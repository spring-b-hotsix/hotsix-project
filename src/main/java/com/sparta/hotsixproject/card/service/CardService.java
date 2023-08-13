package com.sparta.hotsixproject.card.service;

import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.card.dto.CardResponseDto;
import com.sparta.hotsixproject.card.dto.DueRequestDto;
import com.sparta.hotsixproject.card.dto.MoveRequestDto;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.event.CardUpdateEvent;
import com.sparta.hotsixproject.card.event.EventPublisher;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.carduser.entity.CardUser;
import com.sparta.hotsixproject.carduser.repository.CardUserRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.exception.UnauthorizedException;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final SideRepository sideRepository;
    private final BoardUserRepository boardUserRepository;
    private final CardUserRepository cardUserRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    // 카드 생성
    @Transactional
    public ResponseEntity<ApiResponseDto> createCard(Long sideId, String name, User user) {
        Side side = sideRepository.findById(sideId).get();
        Card card = new Card(name, side.getCardList().size() + 1, user, side);
        side.addCard(card); // 연관관계 리스트 추가
        cardRepository.save(card);

        CardUser cardUser = new CardUser(card, user);
        cardUserRepository.save(cardUser);

        ApiResponseDto apiResponseDto = new ApiResponseDto("카드 생성 완료", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 카드 상세 조회
    @Transactional(readOnly = true)
    public ResponseEntity<CardResponseDto> getCard(Long boardId, Long sideId, Long cardId) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // side 안의 카드 전체 조회
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCards(Long boardId, Long sideId) {
//        return cardRepository.findBySide_Board_IdAndSide_IdOrderByPositionDescIdAsc(boardId, sideId).stream().map(CardResponseDto::new).toList();
//        return cardRepository.findBySide_Board_IdAndSide_Id(boardId, sideId).stream().map(CardResponseDto::new).toList();
        return cardRepository.findBySide_Board_IdAndSide_IdOrderByPositionAscIdAsc(boardId, sideId).stream().map(CardResponseDto::new).toList();
    }

    // 카드 이름 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateName(Long boardId, Long sideId, Long cardId, String name, User editor) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        String oldName = card.getName();
        card.updateName(name);

        // 비동기적으로 이벤트 발생
        eventPublisher.publishCardUpdatedEvent(editor, card, oldName, name,
                card.getDescription(), card.getDescription(),
                card.getColor(), card.getColor());

        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 설명 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateDesc(Long boardId, Long sideId, Long cardId, String description, User editor) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        String oldDescription = card.getDescription();
        card.updateDesc(description);

        // 비동기적으로 이벤트 발생, dto 내려 준 이후에 listener로 메소드 시작
        eventPublisher.publishCardUpdatedEvent(editor, card, card.getName(), card.getName(),
                oldDescription, description,
                card.getColor(), card.getColor());
        
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 색상 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateColor(Long boardId, Long sideId, Long cardId, String color, User editor) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        String oldColor = card.getColor();
        card.updateColor(color);

        // 비동기적으로 이벤트 발생
        eventPublisher.publishCardUpdatedEvent(editor, card, card.getName(), card.getName(),
                card.getDescription(), card.getDescription(),
                card.getColor(), color);

        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 마감 기한 설정 및 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateDue(Long boardId, Long sideId, Long cardId, DueRequestDto requestDto) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        card.updateDue(requestDto.getDue());
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 마감 기한 삭제
    @Transactional
    public ResponseEntity<CardResponseDto> updateDueRemoval(Long boardId, Long sideId, Long cardId) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        card.deleteDue();
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 작업자 추가
    @Transactional
    public ResponseEntity<CardResponseDto> addWorker(Long boardId, Long sideId, Long cardId, String email) {
        if (boardUserRepository.findByUser_EmailAndBoard_Id(email, boardId).isEmpty()) {
            throw new IllegalArgumentException("보드 멤버가 아닙니다.");
        }
        if (cardUserRepository.findByCard_IdAndUser_Email(cardId, email).isPresent()) {
            throw new IllegalArgumentException("이미 카드 작업자 입니다.");
        }

        Card card = cardRepository.findById(cardId).orElse(null);
        CardUser cardUser = new CardUser(card, userRepository.findByEmail(email).orElse(null));
        card.addCardUser(cardUser);
        cardUserRepository.save(cardUser);

        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> deleteWorker(Long boardId, Long sideId, Long cardId, String email) {
        if (boardUserRepository.findByUser_EmailAndBoard_Id(email, boardId).isEmpty()) {
            throw new IllegalArgumentException("보드 멤버가 아닙니다.");
        }
        Card card = cardRepository.findById(cardId).orElse(null);
        CardUser cardUser = cardUserRepository.findByCard_IdAndUser_Email(cardId, card.getUser().getEmail()).get();
        card.removeCardUser(cardUser); // 삭제 완료
        cardUserRepository.delete(cardUser);

        ApiResponseDto apiResponseDto = new ApiResponseDto("카드 내 삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    @Transactional
    // 카드 이동
    public ResponseEntity<CardResponseDto> moveCard(Long boardId, Long sideId, Long cardId, MoveRequestDto requestDto) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        Side side = sideRepository.findByName(requestDto.getSideName()).get();
        for (Card ca : side.getCardList()) {
            if (ca.getPosition() >= requestDto.getPosition() && ca.getPosition() < card.getPosition()) { // 아래에서 위로
                ca.moveDown();
            } else if (ca.getPosition() <= requestDto.getPosition() && ca.getPosition() > card.getPosition()) { // 위에서 아래로
                ca.moveUp();
            }
        }
        card.moveCard(side, requestDto.getPosition());
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    @Transactional
    // 카드 삭제
    public ResponseEntity<ApiResponseDto> deleteCard(Long boardId, Long sideId, Long cardId, User user) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        if (!card.getUser().equals(user)) {
            throw new UnauthorizedException("삭제 권한 없음");
        }
        CardUser cardUser = cardUserRepository.findByCard_IdAndUser_Email(cardId, card.getUser().getEmail()).get();
        card.removeCardUser(cardUser);

        Side side = sideRepository.findById(sideId).get();
        side.removeCard(card); // 연관관계 리스트 삭제
        cardRepository.delete(card);

        ApiResponseDto apiResponseDto = new ApiResponseDto("삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
