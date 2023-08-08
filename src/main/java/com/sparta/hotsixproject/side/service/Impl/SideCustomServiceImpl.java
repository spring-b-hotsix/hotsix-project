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
import java.util.Objects;
import java.util.Optional;
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
        return sideRepository.findAllOrderByPositionAsc().stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
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
        Board requestBoard = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + requestDto.getBoardId())
        );
        // 이동할 사이드
        Side currentSide = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );

        // swap
        swap(requestBoard, currentSide, requestDto.getPosition());

        return sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId).stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSide(Long boardId, Long sideId) {

    }

    private void swap(Board board, Side side, int selectPoistion) {
        /**
         * 1. 같은 보드로 옮기는 경우 = swap (두 컬럼의 자리를 바꿈)
         * 2. 다른 보드로 옮기는 경우 = move (기존 컬럼은 position값 + 1)
         */
        Optional<Side> preSide = sideRepository.findByBoardIdAndSidePosition(board.getId(), selectPoistion);
        Side currentSide = sideRepository.findByBoardIdAndSideId(board.getId(), side.getId()).get();

        if (preSide.isPresent()) {
            if (!Objects.equals(board, currentSide.getBoard())) {
                // 1. 같은 보드로 옮기는 경우 = swap
                Side tempSide = new Side();
                tempSide.moveSide(board, currentSide.getPosition());
                currentSide.moveSide(board, preSide.get().getPosition());
                preSide.get().moveSide(board, tempSide.getPosition());
            } else {
                // 2. 다른 보드로 옮기는 경우 = move (기존 컬럼은 position값 + 1)
                currentSide.moveSide(board, preSide.get().getPosition());
                preSide.get().moveSide(board, preSide.get().getPosition() + 1);
            }
        } else {
            currentSide.moveSide(board, selectPoistion);
        }

    }
}
