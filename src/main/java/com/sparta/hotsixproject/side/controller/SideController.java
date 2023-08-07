package com.sparta.hotsixproject.side.controller;


import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideNameDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.service.Impl.SideCustomServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SideController {
    private final SideCustomServiceImpl sideCustomServiceImpl;

    @GetMapping("/boards/{boardId}/sides")
    public ResponseEntity<List<SideResponseDto>> getSides(@PathVariable("boardId") Long boardId) {
        List<SideResponseDto> results = sideCustomServiceImpl.getSides();
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/boards/{boardId}/sides")
    public ResponseEntity<SideResponseDto> createSide(
            @PathVariable("boardId") Long boardId,
            @RequestBody SideRequestDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.createSide(boardId, requestDto);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}")
    public ResponseEntity<SideResponseDto> updateSideName(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideNameDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.updateSideName(boardId, sideId, requestDto);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/sides/{sideId}")
    public ResponseEntity<SideResponseDto> updateSideOrder(
            @PathVariable("boardId") Long boardId,
            @PathVariable("sideId") Long sideId,
            @RequestBody SideMoveDto requestDto
    ) {
        SideResponseDto result = sideCustomServiceImpl.updateSideOrder(boardId, sideId, requestDto);
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
