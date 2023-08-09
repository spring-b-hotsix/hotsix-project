package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.board.repository.BoardUserRepository;
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
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        checkBoardMember(user, board);

        // position => 1024씩 증가
        int position = (board.getSideList().size() - 1) != 0 ? (board.getSideList().size() - 1) * 1024 : 1024;
        Side side = new Side(requestDto.getName(), position, board);
        sideRepository.save(side);
        return new SideResponseDto(side);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SideResponseDto> getSides(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        checkBoardMember(user, board);
        return sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId).stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SideResponseDto updateSideName(Long boardId, Long sideId, SideRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        checkBoardMember(user, board);
        Side side = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );

        side.updateSideName(requestDto.getName());
        return new SideResponseDto(side);
    }

    @Override
    @Transactional
    public List<SideResponseDto> moveSide(Long boardId, Long sideId, SideMoveDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + boardId)
        );
        checkBoardMember(user, board);
        // 이동시킬 보드
        Board selectBoard = boardRepository.findById(requestDto.getSelectBoardId()).orElseThrow(
                () -> new NullPointerException("Board가 존재하지 않습니다. boardId: " + requestDto.getSelectBoardId())
        );
        checkBoardMember(user, selectBoard);

        // 이동시킬 컬럼(사이드)
        Side currentSide = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );
        // selectPosition = 사이드를 이동시킬 위치
        Side selectSide = selectBoard.getSideList().get(requestDto.getSelectIndex());
        int selectPosition = selectSide.getPosition();

        // 위치 순으로 정렬된 Side List
        List<Side> sortedSideList = sideRepository.findAllByBoardIdOrderBySidePositionAsc(selectBoard.getId());

        // selectSide의 앞 혹은 뒤 position
        int aroundPosition;
        aroundPosition = getAroundPosition(requestDto, sortedSideList);

        // 이동
        move(selectBoard, currentSide, selectPosition, aroundPosition);

        return sideRepository.findAllByBoardIdOrderBySidePositionAsc(boardId).stream()
                .map(SideResponseDto::new)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void deleteSide(Long boardId, Long sideId, User user) {
        Side side = sideRepository.findByBoardIdAndSideId(boardId, sideId).orElseThrow(
                () -> new NullPointerException("Side가 존재하지 않습니다. boardId: " + boardId + " sideId: " + sideId)
        );
        sideRepository.delete(side);
    }

    private void checkBoardMember(User user, Board board) {
        boardUserRepository.findByUserAndBoard(user, board).orElseThrow(() ->
                new IllegalArgumentException("해당 보드 권한을 가진 유저가 아닙니다.")
        );
    }

    private void move(Board selectBoard, Side currentSide, int selectPosition, int aroundPosition) {
        /**
         * (3072 + 4096) / 2 = 3584;
         * 예를 들어서, 2번 - 3번 사이에 들어가고 싶으면
         * (2번 포지션 + 3번 포지션) / 2
         */

        int movePosition = (selectPosition + aroundPosition) / 2;
        currentSide.moveSide(selectBoard, movePosition);
    }

    private int getAroundPosition(SideMoveDto requestDto, List<Side> sortedSideList) {
        int aroundPosition;
        if (requestDto.getSelectIndex() >= sortedSideList.size() - 1) {
            aroundPosition = sortedSideList.get(sortedSideList.size() - 1).getPosition() + 1024;
        } else if (requestDto.getSelectIndex() == 0) {
            int nextPosition = sortedSideList.get(1).getPosition();
            aroundPosition = Math.min(nextPosition - 1024, 0);
        } else {  // prev
            int prevPosition = sortedSideList.get(requestDto.getSelectIndex() - 1).getPosition();
            int nextPosition = sortedSideList.get(requestDto.getSelectIndex() + 1).getPosition();
            aroundPosition = (prevPosition + nextPosition) / 2;
        }
        return aroundPosition;
    }


}
