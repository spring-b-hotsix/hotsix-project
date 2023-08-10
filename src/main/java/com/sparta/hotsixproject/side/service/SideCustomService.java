package com.sparta.hotsixproject.side.service;

import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.user.entity.User;

import java.util.List;

public interface SideCustomService{
    /**
     * @return 전체 컬럼(side) 조회 결과
     */
    List<SideResponseDto> getSides(Long boardId, User user);

    /**
     * @param boardId
     * @param requestDto
     * @param user
     * @return
     */
    SideResponseDto createSide(Long boardId, SideRequestDto requestDto, User user);

    /**
     * @param boardId
     * @param sideId
     * @param requestDto
     * @param user
     * @return
     */
    SideResponseDto updateSideName(Long boardId, Long sideId, SideRequestDto requestDto, User user);

    /**
     * @param boardId
     * @param sideId
     * @param requestDto
     * @param user
     * @return
     */
    List<SideResponseDto> moveSide(Long boardId, Long sideId, SideMoveDto requestDto, User user);

    /**
     * @param boardId
     * @param sideId
     * @param user
     */
    void deleteSide(Long boardId, Long sideId, User user);
}
