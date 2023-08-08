package com.sparta.hotsixproject.checklist.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemResponseDto;
import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistResponseDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistsResponseDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;

public interface ChecklistService {
    /**
     * 체크리스트 만들기
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param requestDto 체크리스트 생성 요청정보
     * @return 체크리스트 생성 결과
     */
    ChecklistResponseDto createChecklist (Long boardId, Long sideId, Long cardId, ChecklistRequestDto requestDto);

    /**
     * 체크리스트 전체 조회
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @return 체크리스트 전체 조회
     */
    ChecklistsResponseDto getChecklists (Long boardId, Long sideId, Long cardId);

    /**
     * 체크리스트 이름 수정
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @param requestDto 체크리스트 수정 요청정보
     * @return 체크리스트 수정 결과
     */
    ChecklistResponseDto updateChecklistName (Long boardId, Long sideId, Long cardId, Long checklistId, ChecklistRequestDto requestDto);

    /**
     * 체크리스트 삭제
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @return 체크리스트 삭제 결과
     */
    void deleteChecklist (Long boardId, Long sideId, Long cardId, Long checklistId);

    /**
     * 체크리스트 아이템 추가
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @param requestDto 아이템 생성 요청정보
     * @return 아이템 추가 결과
     */
    ChecklistItemResponseDto createItem (Long boardId, Long sideId, Long cardId, Long checklistId, ChecklistItemRequestDto requestDto);

    /**
     * 체크리스트 아이템 체크
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @param itemId 아이템 번호
     * @return 아이템 체크 결과
     */
    ChecklistItemResponseDto updateItemChecked (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId);

    /**
     * 체크리스트 아이템 수정
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @param itemId 아이템 번호
     * @param requestDto 아이템 수정 요청정보
     * @return 아이템 수정 결과
     */
    ChecklistItemResponseDto updateItemContent (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId, ChecklistItemRequestDto requestDto);

    /**
     * 체크리스트 아이템 삭제
     * @param boardId 보드 번호
     * @param sideId 사이드 번호
     * @param cardId 카드 번호
     * @param checklistId 체크리스트 번호
     * @param itemId 아이템 번호
     * @return 아이템 삭제 결과
     */
    void deleteItem (Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId);

    /**
     * id로 체크리스트 찾기
     * @param checklistId 체크리스트 번호
     */
    Checklist findChecklist(Long checklistId);

    /**
     * id로 아이템 찾기
     * @param checklistItemId 아이템 번호
     */
    ChecklistItem findChecklistItem(Long checklistItemId);

    /**
     * id로 카드 찾기
     * @param cardId 카드 번호
     */
    Card findCard(Long cardId);
}
