package com.sparta.hotsixproject.checklist.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemResponseDto;
import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.checklist.checklistItem.repository.ChecklistItemRepository;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistResponseDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistsResponseDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import com.sparta.hotsixproject.checklist.repository.ChecklistRepository;
import com.sparta.hotsixproject.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final CardRepository cardRepository;

    // 체크리스트 만들기
    @Override
    @Transactional
    public ChecklistResponseDto createChecklist (Long boardId, Long sideId, Long cardId, ChecklistRequestDto requestDto) {
        Card card = findCard(cardId);
        Checklist checklist = new Checklist(card, requestDto);
        return new ChecklistResponseDto(checklistRepository.save(checklist));
    }

    // 체크리스트 전체 조회
    @Override
    @Transactional(readOnly = true)
    public ChecklistsResponseDto getChecklists (Long boardId, Long sideId, Long cardId) {
        Card card = findCard(cardId);
        List<ChecklistResponseDto> checklists = checklistRepository.findAllByCard(card)
                .stream().map(ChecklistResponseDto::new).toList();
        return new ChecklistsResponseDto(checklists);
    }

    // 체크리스트 이름 수정
    @Override
    @Transactional
    public ChecklistResponseDto updateChecklistName (Long boardId, Long sideId, Long cardId, Long checklistId,
                                              ChecklistRequestDto requestDto) {
        Checklist checklist = findChecklist(checklistId);
        checklist.updateChecklist(requestDto);
        return new ChecklistResponseDto(checklist);
    }

    // 체크리스트 삭제
    @Override
    @Transactional
    public void deleteChecklist (Long boardId, Long sideId, Long cardId, Long checklistId) {
        Checklist checklist = findChecklist(checklistId);
        checklistRepository.delete(checklist);
    }

    // 체크리스트 아이템 추가
    @Override
    @Transactional
    public ChecklistItemResponseDto createItem (Long boardId, Long sideId, Long cardId, Long checklistId,
                                                ChecklistItemRequestDto requestDto) {
        Checklist checklist = findChecklist(checklistId);
        ChecklistItem checklistItem = new ChecklistItem(checklist, requestDto);
        return new ChecklistItemResponseDto(checklistItemRepository.save(checklistItem));
    }

    // 체크리스트 아이템 체크
    @Override
    @Transactional
    public ChecklistItemResponseDto updateItemChecked (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId) {
        ChecklistItem checklistItem = findChecklistItem(itemId);
        checklistItem.updateChecked();
        return new ChecklistItemResponseDto(checklistItem);
    }

    // 체크리스트 아이템 수정
    @Override
    @Transactional
    public ChecklistItemResponseDto updateItemContent (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId,
                                                       ChecklistItemRequestDto requestDto) {
        ChecklistItem checklistItem = findChecklistItem(itemId);
        checklistItem.updateContent(requestDto);
        return new ChecklistItemResponseDto(checklistItem);
    }

    // 체크리스트 아이템 삭제
    @Override
    @Transactional
    public void deleteItem (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId) {
        ChecklistItem checklistItem = findChecklistItem(itemId);
        checklistItemRepository.delete(checklistItem);
    }

    // id에 따른 체크리스트 찾기
    @Override
    public Checklist findChecklist(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow(() ->
                new NotFoundException("선택한 체크리스트는 존재하지 않습니다.")
        );
    }

    // id에 따른 아이템 찾기
    @Override
    public ChecklistItem findChecklistItem(Long checklistItemId) {
        return checklistItemRepository.findById(checklistItemId).orElseThrow(() ->
                new NotFoundException("선택한 체크리스트 아이템은 존재하지 않습니다.")
        );
    }

    // id에 따른 카드 찾기
    @Override
    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new NotFoundException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
