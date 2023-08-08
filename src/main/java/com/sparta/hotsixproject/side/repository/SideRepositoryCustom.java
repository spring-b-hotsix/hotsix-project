package com.sparta.hotsixproject.side.repository;

import com.sparta.hotsixproject.side.entity.Side;

import java.util.Optional;

public interface SideRepositoryCustom {
    Optional<Side> findByBoardIdAndSideId(Long boardId, Long sideId);
}
