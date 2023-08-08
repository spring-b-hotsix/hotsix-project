package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideNameDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.side.service.SideCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SideCustomServiceImpl implements SideCustomService {
    private final BoardRepository boardRepository;
    private final SideRepository sideRepository;

    @Override
    @Transactional
    public SideResponseDto createSide(Long boardId, SideRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        Side side = new Side(requestDto.getName(), board.getSideList().size(), board);
        sideRepository.save(side);
        return new SideResponseDto(side);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SideResponseDto> getSides() {
        List<Side> sides = sideRepository.findAll();
        return sides.stream().map(SideResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SideResponseDto updateSideName(Long boardId, Long sideId, SideNameDto requestDto) {
        Side side = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + sideId)
        );
        side.updateSideName(requestDto.getName());
        return new SideResponseDto(side);
    }

    @Override
    @Transactional
    public SideResponseDto moveSide(Long boardId, Long sideId, SideMoveDto requestDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteSide(Long boardId, Long sideId) {

    }
}
