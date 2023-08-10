package com.sparta.hotsixproject.boarduser.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardUser is a Querydsl query type for BoardUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardUser extends EntityPathBase<BoardUser> {

    private static final long serialVersionUID = -1846393837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardUser boardUser = new QBoardUser("boardUser");

    public final com.sparta.hotsixproject.board.entity.QBoard board;

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final com.sparta.hotsixproject.user.entity.QUser user;

    public QBoardUser(String variable) {
        this(BoardUser.class, forVariable(variable), INITS);
    }

    public QBoardUser(Path<? extends BoardUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardUser(PathMetadata metadata, PathInits inits) {
        this(BoardUser.class, metadata, inits);
    }

    public QBoardUser(Class<? extends BoardUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.sparta.hotsixproject.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.hotsixproject.user.entity.QUser(forProperty("user")) : null;
    }

}

