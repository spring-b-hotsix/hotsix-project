package com.sparta.hotsixproject.card.service;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.card.dto.CardRequestDto;
import com.sparta.hotsixproject.card.dto.CardResponseDto;
import com.sparta.hotsixproject.card.dto.MoveRequestDto;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.user.entity.User;
import lombok.RequiredArgsConstructor;
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

    // 카드 생성
    @Transactional
    public ResponseEntity<ApiResponseDto> createCard(Long sideId, String name, User user) {
        Side side = sideRepository.findById(sideId).get();
        Card card = new Card(name, side.getCardList().size() + 1, user, side);
        cardRepository.save(card);

        // 추가 작업 필요

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
    public ResponseEntity<CardResponseDto> updateName(Long boardId, Long sideId, Long cardId, String name) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        card.updateName(name);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 설명 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateDesc(Long boardId, Long sideId, Long cardId, String description) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        card.updateDesc(description);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 색상 수정
    @Transactional
    public ResponseEntity<CardResponseDto> updateColor(Long boardId, Long sideId, Long cardId, String color) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        card.updateColor(color);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseEntity<>(cardResponseDto, HttpStatus.OK);
    }

    // 카드 작업자 추가
//    @Transactional
//    public ResponseEntity<CardResponseDto> addWorker(Long boardId, Long sideId, Long cardId) {
//    }

    @Transactional
    // 카드 이동
    public ResponseEntity<CardResponseDto> moveCard(Long boardId, Long sideId, Long cardId, MoveRequestDto requestDto) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        Side side = sideRepository.findByName(requestDto.getSideName());
        for (Card ca : side.getCardList()) {
            if (ca.getPosition() >= requestDto.getPosition() && ca.getPosition() < card.getPosition()) { // 아래에서 위로
                ca.moveDown();
            }
            else if (ca.getPosition() <= requestDto.getPosition() && ca.getPosition() > card.getPosition()) { // 위에서 아래로
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
            ApiResponseDto apiResponseDto = new ApiResponseDto("삭제 권한 없음", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNAUTHORIZED);
        }
        cardRepository.delete(card);
        ApiResponseDto apiResponseDto = new ApiResponseDto("삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}