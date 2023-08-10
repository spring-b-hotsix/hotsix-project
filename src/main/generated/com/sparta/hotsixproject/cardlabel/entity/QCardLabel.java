package com.sparta.hotsixproject.cardlabel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardLabel is a Querydsl query type for CardLabel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardLabel extends EntityPathBase<CardLabel> {

    private static final long serialVersionUID = -1851216845L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardLabel cardLabel = new QCardLabel("cardLabel");

    public final com.sparta.hotsixproject.card.entity.QCard card;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sparta.hotsixproject.label.entity.QLabel label;

    public QCardLabel(String variable) {
        this(CardLabel.class, forVariable(variable), INITS);
    }

    public QCardLabel(Path<? extends CardLabel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardLabel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardLabel(PathMetadata metadata, PathInits inits) {
        this(CardLabel.class, metadata, inits);
    }

    public QCardLabel(Class<? extends CardLabel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.sparta.hotsixproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
        this.label = inits.isInitialized("label") ? new com.sparta.hotsixproject.label.entity.QLabel(forProperty("label"), inits.get("label")) : null;
    }

}

