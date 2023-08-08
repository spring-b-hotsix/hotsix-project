package com.sparta.hotsixproject.side.repository;

import com.sparta.hotsixproject.side.entity.Side;

import java.util.List;
import java.util.Optional;

public interface SideRepositoryCustom {
    Optional<Side> findByName(String name);
    Optional<Side> findByBoardIdAndSideId(Long boardId, Long sideId);
    Optional<Side> findByBoardIdAndSidePosition(Long boardId, int sidePosition);
    Optional<Side> findByPrevSidePosition(int prevPosition);
    List<Side> findAllOrderByPositionAsc();
    List<Side> findAllByBoardIdOrderBySidePositionAsc(Long boardId);
}
