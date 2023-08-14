package com.sparta.hotsixproject.card.controller;

import com.sparta.hotsixproject.card.dto.*;
import com.sparta.hotsixproject.card.service.CardService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Tag(name = "카드 관련 API", description = "카드 관련 API 입니다.")
public class CardController {
    public final CardService cardService;
    
    // 임시 주석6
    @PostMapping("/{boardId}/sides/{sideId}/cards")
    @Operation(summary = "카드 생성", description = "@PathVariable을 통해 boardId와 sideId를 받아와, 해당 위치에 카드를 생성합니다. Dto를 통해 name(이름) 값을 받아와 카드를 생성할 때 해당 name을 저장합니다.")
    public ResponseEntity<ApiResponseDto> createCard(
            @Parameter(name = "boardId", description = "카드를 생성할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "카드를 생성할 side(컬럼)의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "requestDto", description = "카드 생성시 필요한 정보 (name)") @RequestBody CardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.createCard(sideId, requestDto.getName(), userDetails.getUser());
    }

    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}")
    @Operation(summary = "카드 상세 조회", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드 1개의 상세 정보를 조회합니다.")
    public String getCard(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side(컬럼)의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "상세 정보를 조회할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            Model model
    ) {
        ResponseEntity<CardResponseDto> card = cardService.getCard(boardId, sideId, cardId);
        model.addAttribute("card", card);
        return "card";
    }

    @GetMapping("/{boardId}/sides/{sideId}/cards")
    @Operation(summary = "카드 전체 조회", description = "@PathVariable을 통해 boardId, sideId를 받아와, 해당 정보와 일치하는 모든 카드의 정보를 조회합니다.")
    public List<CardResponseDto> getCards(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId
    ) {
        return cardService.getCards(boardId, sideId);
    }

    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/worker")
    @Operation(summary = "카드 작업자 추가", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드에 작업자를 추가합니다.")
    public ResponseEntity<CardResponseDto> addWorker(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "작업자를 추가할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "초대할 작업자의 정보 (email)") @RequestBody InviteCardRequestDto requestDto
    ) {
        return cardService.addWorker(boardId, sideId, cardId, requestDto.getEmail());
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/name")
    @Operation(summary = "카드 이름 수정", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드에 Dto로부터 받아온 name(이름) 정보를 수정합니다.")
    public ResponseEntity<CardResponseDto> updateName(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "이름을 수정할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "카드 이름 수정 정보") @RequestBody NameRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.updateName(boardId, sideId, cardId, requestDto.getName(), userDetails.getUser());
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/desc")
    @Operation(summary = "카드 설명 수정", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드에 Dto로부터 받아온 description(설명) 정보를 수정합니다.")
    public ResponseEntity<CardResponseDto> updateDesc(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "설명을 수정할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "카드 설명 수정 정보") @RequestBody DescRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.updateDesc(boardId, sideId, cardId, requestDto.getDescription(), userDetails.getUser());
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/color")
    @Operation(summary = "카드 색상 수정", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드에 Dto로부터 받아온 color(색상) 정보를 수정합니다.")
    public ResponseEntity<CardResponseDto> updateColor(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "색상을 수정할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "카드 색상 수정 정보") @RequestBody ColorRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.updateColor(boardId, sideId, cardId, requestDto.getColor(), userDetails.getUser());
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/due")
    @Operation(summary = "카드 마감 기한 설정 및 수정", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드에 Dto로부터 받아온 due(마감기한) 정보를 수정합니다.")
    public ResponseEntity<CardResponseDto> updateDue(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "마감 기한을 설정 및 수정할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "카드 마감 기한 설정 및 수정에 대한 정보 (LocalDateTime)") @RequestBody DueRequestDto requestDto
    ) {
        return cardService.updateDue(boardId, sideId, cardId, requestDto);
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/due-removal")
    @Operation(summary = "카드 마감 기한 삭제", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드의 due(마감기한) 정보를 삭제합니다.")
    public ResponseEntity<CardResponseDto> updateDue(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "마감 기한을 삭제시킬 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId
    ) {
        return cardService.updateDueRemoval(boardId, sideId, cardId);
    }

    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/position")
    @Operation(summary = "카드 이동", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드의 position(위치)를 Dto로부터 받아온 position 정보를 사용하여 수정합니다.")
    public ResponseEntity<CardResponseDto> moveCard(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "이동시킬 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "requestDto", description = "카드를 이동시킬 정보 (position)") @RequestBody MoveRequestDto requestDto
    ) {
        return cardService.moveCard(boardId, sideId, cardId, requestDto);
    }

    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}")
    @Operation(summary = "카드 삭제", description = "@PathVariable을 통해 boardId, sideId, cardId를 받아와, 해당 정보와 일치하는 카드를 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteCard(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "삭제시킬 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.deleteCard(boardId, sideId, cardId, userDetails.getUser());
    }
}