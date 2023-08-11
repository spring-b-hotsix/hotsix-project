package com.sparta.hotsixproject.side.controller;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.service.Impl.SideCustomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "컬럼(side) 관련 API", description = "컬럼(side) 관련 API 입니다.")
public class SideController {
    private final SideCustomServiceImpl sideCustomServiceImpl;

    @PostMapping("/boards/{boardId}/sides")
    @Operation(summary = "컬럼(side) 생성", description = "@PathVariable을 통해 boardId를 받아와, 해당 위치에 컬럼(side)을 생성합니다. Dto를 통해 name(이름) 값을 받아와 side를 생성할 때 해당 name을 저장합니다.")
    public ResponseEntity<ApiResponseDto> createSide(
            @Parameter(name = "boardId", description = "컬럼(side)을 생성할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(description = "컬럼(side)을 생성 및 수정할 때 필요한 정보") @RequestBody SideRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SideResponseDto result = sideCustomServiceImpl.createSide(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto(result.getName()+"를 추가했습니다.",201));
    }

    @GetMapping("/boards/{boardId}/sides")
    @Operation(summary = "해당 보드의 컬럼(side) 전체 조회", description = "@PathVariable을 통해 boardId를 받아와, 해당 위치의 컬럼(side)을 전체 조회합니다.")
    public ResponseEntity<List<SideResponseDto>> getSides(
            @Parameter(name = "boardId", description = "선택한 컬럼(side)이 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SideResponseDto> results = sideCustomServiceImpl.getSides(boardId, userDetails.getUser());
        return ResponseEntity.ok().body(results);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/name")
    @Operation(summary = "컬럼(side) 이름 수정", description = "@PathVariable을 통해 boardId와 sideId를 받아와, 해당 위치에 있는 컬럼(side)의 이름을 수정합니다. Dto를 통해 name(이름) 정보를 가져옵니다.")
    public ResponseEntity<SideResponseDto> updateSideName(
            @Parameter(name = "boardId", description = "선택한 컬럼(side)이 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "이름을 수정할 컬럼(side)의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @RequestBody SideRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SideResponseDto result = sideCustomServiceImpl.updateSideName(boardId, sideId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/order")
    @Operation(summary = "컬럼(side) 이동", description = "@PathVariable을 통해 boardId와 sideId를 받아와, 해당 위치에 있는 컬럼(side)을 이동시킵니다. Dto를 통해 position(위치) 정보를 가져옵니다.")
    public ResponseEntity<List<SideResponseDto>> moveSide(
            @Parameter(name = "boardId", description = "선택한 컬럼(side)이 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "이동시킬 컬럼(side)의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(description = "컬럼(side)을 이동 시킬 정보 (position)") @RequestBody SideMoveDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SideResponseDto> results = sideCustomServiceImpl.moveSide(boardId, sideId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/boards/{boardId}/sides/{sideId}")
    @Operation(summary = "컬럼(side) 삭제", description = "@PathVariable을 통해 boardId와 sideId를 받아와, 해당 위치에 있는 컬럼(side)을 삭제합니다.")
    public ResponseEntity<String> deleteSide(
            @Parameter(name = "boardId", description = "선택한 컬럼(side)이 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "삭제할 컬럼(side)의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        sideCustomServiceImpl.deleteSide(boardId, sideId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}