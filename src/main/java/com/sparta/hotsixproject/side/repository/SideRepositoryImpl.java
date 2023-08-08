package com.sparta.hotsixproject.side.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hotsixproject.board.entity.QBoard;
import com.sparta.hotsixproject.side.entity.QSide;
import com.sparta.hotsixproject.side.entity.Side;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SideRepositoryImpl implements SideRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Side> findByBoardIdAndSideId(Long boardId, Long sideId) {
        QBoard board = QBoard.board;
        QSide side = QSide.side;

        return jpaQueryFactory.select(side)
                .from(side)
                .innerJoin(board)
                .on(side.id.eq(sideId))
                .where(board.id.eq(boardId))
                .stream().findFirst();
    }
}
