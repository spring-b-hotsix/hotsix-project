package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.side.service.SideCustomService;
import com.sparta.hotsixproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SideCustomServiceImpl implements SideCustomService {
    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final SideRepository sideRepository;

    @Override
    @Transactional
    public SideResponseDto createSide(Long boardId, SideRequestDto requestDto, User user) {
        Board board = findBoard(boardId);
        checkBoardMember(user, board);

        // position => 1024씩 증가
        List<Side> sortedSideList = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board.getId());
        int position = (sortedSideList.size() != 0) ? sortedSideList.get((sortedSideList.size() - 1)).getPosition() + 1024 : 1024;
        Side side = new Side(requestDto.getName(), position, board);
        sideRepository.save(side);
        return new SideResponseDto(side);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SideResponseDto> getSides(Long boardId, User user) {
        Board board = findBoard(boardId);
        checkBoardMember(user, board);
        return sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId).stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SideResponseDto updateSideName(Long boardId, Long sideId, SideRequestDto requestDto, User user) {
        Board board = findBoard(boardId);
        checkBoardMember(user, board);
        Side side = findSide(boardId, sideId);

        side.updateSideName(requestDto.getName());
        return new SideResponseDto(side);
    }

    @Override
    @Transactional
    public List<SideResponseDto> moveSide(Long boardId, Long sideId, SideMoveDto requestDto, User user) {
        Board board = findBoard(boardId);
        checkBoardMember(user, board);

        // 이동시킬 컬럼(사이드)
        Side currentSide = findSide(boardId, sideId);
        List<Side> sortedSideList = sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId);
        Side selectSide = sortedSideList.get(requestDto.getSelectIndex());

        // 이동
        swap(selectSide, currentSide);

        return sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId).stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSide(Long boardId, Long sideId, User user) {
        Side side = findSide(boardId, sideId);
        sideRepository.delete(side);
    }

    private Side findSide(Long boardId, Long sideId) {
        return sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
    }

    private void checkBoardMember(User user, Board board) {
        boardUserRepository.findByUserAndBoard(user, board).orElseThrow(() ->
                new IllegalArgumentException("해당 보드 권한을 가진 유저가 아닙니다.")
        );
    }

    private void swap(Side side1, Side side2) {
        int temp = side1.getPosition();
        side1.setPosition(side2.getPosition());
        side2.setPosition(temp);
    }
}
