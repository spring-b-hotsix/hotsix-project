package com.sparta.hotsixproject.label.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabel is a Querydsl query type for Label
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabel extends EntityPathBase<Label> {

    private static final long serialVersionUID = -1593905709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabel label = new QLabel("label");

    public final NumberPath<Integer> blue = createNumber("blue", Integer.class);

    public final com.sparta.hotsixproject.board.entity.QBoard board;

    public final ListPath<com.sparta.hotsixproject.cardlabel.entity.CardLabel, com.sparta.hotsixproject.cardlabel.entity.QCardLabel> cardLabelList = this.<com.sparta.hotsixproject.cardlabel.entity.CardLabel, com.sparta.hotsixproject.cardlabel.entity.QCardLabel>createList("cardLabelList", com.sparta.hotsixproject.cardlabel.entity.CardLabel.class, com.sparta.hotsixproject.cardlabel.entity.QCardLabel.class, PathInits.DIRECT2);

    public final NumberPath<Integer> green = createNumber("green", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> red = createNumber("red", Integer.class);

    public final StringPath title = createString("title");

    public QLabel(String variable) {
        this(Label.class, forVariable(variable), INITS);
    }

    public QLabel(Path<? extends Label> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabel(PathMetadata metadata, PathInits inits) {
        this(Label.class, metadata, inits);
    }

    public QLabel(Class<? extends Label> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.sparta.hotsixproject.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

