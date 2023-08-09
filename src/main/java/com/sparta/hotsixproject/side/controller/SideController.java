package com.sparta.hotsixproject.side.controller;

import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.service.Impl.SideCustomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SideController {
    private final SideCustomServiceImpl sideCustomServiceImpl;

    @PostMapping("/boards/{boardId}/sides")
    public ResponseEntity<SideResponseDto> createSide(
            @PathVariable("boardId") Long boardId,
            @RequestBody SideRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SideResponseDto result = sideCustomServiceImpl.createSide(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/boards/{boardId}/sides")
    public ResponseEntity<List<SideResponseDto>> getSides(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SideResponseDto> results = sideCustomServiceImpl.getSides(boardId, userDetails.getUser());
        return ResponseEntity.ok().body(results);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/name")
    public ResponseEntity<SideResponseDto> updateSideName(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SideResponseDto result = sideCustomServiceImpl.updateSideName(boardId, sideId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/order")
    public ResponseEntity<List<SideResponseDto>> moveSide(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideMoveDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SideResponseDto> results = sideCustomServiceImpl.moveSide(boardId, sideId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/boards/{boardId}/sides/{sideId}")
    public ResponseEntity<String> deleteSide(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        sideCustomServiceImpl.deleteSide(boardId, sideId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}