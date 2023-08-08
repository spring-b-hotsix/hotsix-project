package com.sparta.hotsixproject.label.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.cardlabel.repository.CardLabelRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final CardRepository cardRepository;
    private final CardLabelRepository cardLabelRepository;
    private final LabelRepository labelRepository;
    
    // 라벨 생성
    @Transactional
    public ResponseEntity<ApiResponseDto> createLabel(Long boardId, Long sideId, Long cardId, LabelRequestDto requestDto) {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        Label label = new Label(requestDto.getTitle(), requestDto.getColor());
        CardLabel cardLabel = new CardLabel(card, label);
        cardLabelRepository.save(cardLabel);

        ApiResponseDto apiResponseDto = new ApiResponseDto("라벨 생성 완료", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }
    
    // 라벨 전체 조회
    public List<LabelResponseDto> getLabels(Long boardId, Long sideId, Long cardId) {
        return labelRepository.findByCardLabelList_Card_Id(cardId).stream().map(LabelResponseDto::new).toList();
    }
}
