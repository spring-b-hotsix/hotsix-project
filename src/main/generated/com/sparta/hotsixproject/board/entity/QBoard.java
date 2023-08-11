package com.sparta.hotsixproject.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -72095981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final ListPath<com.sparta.hotsixproject.boarduser.entity.BoardUser, com.sparta.hotsixproject.boarduser.entity.QBoardUser> boardUsers = this.<com.sparta.hotsixproject.boarduser.entity.BoardUser, com.sparta.hotsixproject.boarduser.entity.QBoardUser>createList("boardUsers", com.sparta.hotsixproject.boarduser.entity.BoardUser.class, com.sparta.hotsixproject.boarduser.entity.QBoardUser.class, PathInits.DIRECT2);

    public final SimplePath<java.awt.Color> color = createSimple("color", java.awt.Color.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.sparta.hotsixproject.label.entity.Label, com.sparta.hotsixproject.label.entity.QLabel> labelList = this.<com.sparta.hotsixproject.label.entity.Label, com.sparta.hotsixproject.label.entity.QLabel>createList("labelList", com.sparta.hotsixproject.label.entity.Label.class, com.sparta.hotsixproject.label.entity.QLabel.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<com.sparta.hotsixproject.side.entity.Side, com.sparta.hotsixproject.side.entity.QSide> sideList = this.<com.sparta.hotsixproject.side.entity.Side, com.sparta.hotsixproject.side.entity.QSide>createList("sideList", com.sparta.hotsixproject.side.entity.Side.class, com.sparta.hotsixproject.side.entity.QSide.class, PathInits.DIRECT2);

    public final com.sparta.hotsixproject.user.entity.QUser user;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.hotsixproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

