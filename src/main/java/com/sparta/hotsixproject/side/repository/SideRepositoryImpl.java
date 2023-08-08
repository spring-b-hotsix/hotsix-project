package com.sparta.hotsixproject.side.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hotsixproject.board.entity.QBoard;
import com.sparta.hotsixproject.side.entity.QSide;
import com.sparta.hotsixproject.side.entity.Side;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SideRepositoryImpl implements SideRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Side> findByBoardIdAndSideId(Long boardId, Long sideId) {
        QBoard board = QBoard.board;
        QSide side = QSide.side;
        return jpaQueryFactory.selectFrom(side)
                .innerJoin(board)
                .on(side.id.eq(sideId))
                .where(board.id.eq(boardId))
                .stream().findFirst();
    }

    @Override
    public Optional<Side> findByBoardIdAndSidePosition(Long boardId, int sidePosition) {
        QBoard board = QBoard.board;
        QSide side = QSide.side;
        return jpaQueryFactory.selectFrom(side)
                .where(board.id.eq(boardId).and(side.position.eq(sidePosition)))
                .stream().findFirst();
    }

    @Override
    public List<Side> findAllOrderByPositionAsc() {
        QSide side = QSide.side;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, side.position);

        return jpaQueryFactory.selectFrom(side)
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public List<Side> findAllByBoardIdOrderBySidePositionAsc(Long boardId) {
        QBoard board = QBoard.board;
        QSide side = QSide.side;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, side.position);

        return jpaQueryFactory.selectFrom(side)
                .where(board.id.eq(boardId))
                .orderBy(orderSpecifier)
                .fetch();
    }
}
