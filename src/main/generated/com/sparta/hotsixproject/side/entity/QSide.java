package com.sparta.hotsixproject.side.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSide is a Querydsl query type for Side
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSide extends EntityPathBase<Side> {

    private static final long serialVersionUID = -991078757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSide side = new QSide("side");

    public final com.sparta.hotsixproject.board.entity.QBoard board;

    public final ListPath<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard> cardList = this.<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard>createList("cardList", com.sparta.hotsixproject.card.entity.Card.class, com.sparta.hotsixproject.card.entity.QCard.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> position = createNumber("position", Integer.class);

    public QSide(String variable) {
        this(Side.class, forVariable(variable), INITS);
    }

    public QSide(Path<? extends Side> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSide(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSide(PathMetadata metadata, PathInits inits) {
        this(Side.class, metadata, inits);
    }

    public QSide(Class<? extends Side> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.sparta.hotsixproject.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

