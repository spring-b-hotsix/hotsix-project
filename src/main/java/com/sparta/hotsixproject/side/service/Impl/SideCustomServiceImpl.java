package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideNameDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.service.SideCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SideCustomServiceImpl implements SideCustomService {
    @Override
    public List<SideResponseDto> getSides() {
        return null;
    }

    @Override
    public SideResponseDto createSide(Long boardId, SideRequestDto requestDto) {
        return null;
    }

    @Override
    public SideResponseDto updateSideName(Long boardId, Long sideId, SideNameDto requestDto) {
        return null;
    }

    @Override
    public SideResponseDto updateSideOrder(Long boardId, Long sideId, SideMoveDto requestDto) {
        return null;
    }

    @Override
    public void deleteSide(Long boardId, Long sideId) {

    }
}
