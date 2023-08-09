package com.sparta.hotsixproject.label.service;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.cardlabel.repository.CardLabelRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.repository.LabelRepository;
import com.sparta.hotsixproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final CardLabelRepository cardLabelRepository;

    // 라벨 생성
    @Transactional
    @LabelCheckPageAndUser
    public ResponseEntity<ApiResponseDto> createLabel(Long boardId, LabelRequestDto requestDto, User user) {
        Board board = findBoard(boardId);
        Label label = new Label(board, requestDto.getTitle(), requestDto.getColor());
        labelRepository.save(label);

        ApiResponseDto apiResponseDto = new ApiResponseDto("라벨 생성 완료", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 해당 보드에 대한 라벨 전체 조회
    @Transactional(readOnly = true)
    @LabelCheckPageAndUser
    public List<LabelResponseDto> getLabels(Long boardId, User user) {
        return labelRepository.findAllByBoard_Id(boardId).stream().map(LabelResponseDto::new).toList();
    }

    // 라벨 수정
    @Transactional
    @LabelCheckPageAndUser
    public ResponseEntity<LabelResponseDto> updateLabel(Long boardId, Long labelId, LabelRequestDto requestDto, User user) {
        Label label = findLabel(labelId);
        label.update(requestDto.getTitle(), requestDto.getColor());
        LabelResponseDto responseDto = new LabelResponseDto(label);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 라벨 삭제
    @Transactional
    @LabelCheckPageAndUser
    public ResponseEntity<ApiResponseDto> deleteLabel(Long boardId, Long labelId, User user) {
        Label label = findLabel(labelId);
        labelRepository.delete(label);
        ApiResponseDto apiResponseDto = new ApiResponseDto("라벨 삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 카드에 라벨 추가
    @Transactional
    @LabelCheckPageAndUser
    public ResponseEntity<ApiResponseDto> CreateCardLabel(Long boardId, Long sideId, Long cardId, Long labelId, User user) {
        Card card = findCard(cardId);
        Label label = findLabel(labelId);
        if (cardLabelRepository.findByCard_idAndLabel_id(cardId, labelId).orElse(null)!=null) {
            throw new IllegalArgumentException("해당 카드 라벨은 이미 존재합니다.");
        }
        CardLabel cardLabel = new CardLabel(card, label);
        cardLabelRepository.save(cardLabel);
        ApiResponseDto apiResponseDto = new ApiResponseDto("카드 내 라벨 추가 완료", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 카드에 추가된 라벨 전체 조회
    @Transactional(readOnly = true)
    @LabelCheckPageAndUser
    public List<LabelResponseDto> getCardLabels(Long boardId, Long sideId, Long cardId, User user) {
        return cardLabelRepository.findAllByCard_Id(cardId).stream().map(LabelResponseDto::new).toList();
    }

    // 카드에 라벨 삭제
    @Transactional
    @LabelCheckPageAndUser
    public ResponseEntity<ApiResponseDto> deleteCardLabel(Long boardId, Long sideId, Long cardId, Long labelId, User user) {
        CardLabel cardLabel = findCardLabel(cardId, labelId);
        cardLabelRepository.delete(cardLabel);
        ApiResponseDto apiResponseDto = new ApiResponseDto("카드 내 라벨 삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // id를 통해 보드 찾기
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 보드 입니다.")
        );
    }

    // id를 통해 카드 찾기
    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 카드 입니다.")
        );
    }

    // id를 통해 라벨 찾기
    public Label findLabel(Long id) {
        return labelRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 라벨 입니다.")
        );
    }

    // id를 통해 카드라벨 찾기
    public CardLabel findCardLabel(Long cardId, Long labelId) {
        return cardLabelRepository.findByCard_idAndLabel_id(cardId, labelId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 카드라벨 입니다.")
        );
    }
}
