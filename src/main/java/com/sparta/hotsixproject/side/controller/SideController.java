package com.sparta.hotsixproject.side.controller;


import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideNameDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.service.Impl.SideCustomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SideController {
    private final SideCustomServiceImpl sideCustomServiceImpl;

    @PostMapping("/boards/{boardId}/sides")
    public ResponseEntity<SideResponseDto> createSide(
            @PathVariable("boardId") Long boardId,
            @RequestBody SideRequestDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.createSide(boardId, requestDto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/boards/{boardId}/sides")
    public ResponseEntity<List<SideResponseDto>> getSides(@PathVariable("boardId") Long boardId) {
        List<SideResponseDto> results = sideCustomServiceImpl.getSides();
        return ResponseEntity.ok().body(results);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/name")
    public ResponseEntity<SideResponseDto> updateSideName(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideNameDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.updateSideName(boardId, sideId, requestDto);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}/move")
    public ResponseEntity<SideResponseDto> moveSide(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideMoveDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.moveSide(boardId, sideId, requestDto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/boards/{boardId}/sides/{sideId}")
    public ResponseEntity<String> deleteSide(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId
    ) {
        sideCustomServiceImpl.deleteSide(boardId, sideId);
        return ResponseEntity.noContent().build();
    }
}
