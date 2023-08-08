package com.sparta.hotsixproject.side.service;

import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;

import java.util.List;

public interface SideCustomService{
    /**
     * @return 전체 컬럼(side) 조회 결과
     */
    List<SideResponseDto> getSides();

    /**
     * @param boardId
     * @param requestDto
     * @return
     */
    SideResponseDto createSide(Long boardId, SideRequestDto requestDto);

    /**
     * @param boardId
     * @param sideId
     * @param requestDto
     * @return
     */
    SideResponseDto updateSideName(Long boardId, Long sideId, SideRequestDto requestDto);

    /**
     * @param boardId
     * @param sideId
     * @param requestDto
     * @return
     */
    List<SideResponseDto> moveSide(Long boardId, Long sideId, SideMoveDto requestDto);

    /**
     * @param boardId
     * @param sideId
     */
    void deleteSide(Long boardId, Long sideId);
}
