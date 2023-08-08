package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
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
        List<Side> sideList = sideRepository.findAll();
        return sideList.stream().map(SideResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SideResponseDto updateSideName(Long boardId, Long sideId, SideRequestDto requestDto) {
        Side side = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );
        side.updateSideName(requestDto.getName());
        return new SideResponseDto(side);
    }

    @Override
    @Transactional
    public List<SideResponseDto> moveSide(Long boardId, Long sideId, SideMoveDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        Side selectSide = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );
        List<Side> sideList = sideRepository.findAll();
        int prePos = selectSide.getPosition();
        int newPos = requestDto.getPosition();

        swap(sideList, prePos, newPos);

        return sideList.stream().map(SideResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSide(Long boardId, Long sideId) {

    }

    private void swap(List<Side> sideList, int prePos, int newPos) {
        Side tempSide = new Side();
        Side preSide = sideList.get(prePos);
        Side newSide = sideList.get(newPos);

        tempSide.moveSide(newSide.getBoard(), newSide.getPosition());
        newSide.moveSide(preSide.getBoard(), preSide.getPosition());
        preSide.moveSide(tempSide.getBoard(), tempSide.getPosition());
    }
}
