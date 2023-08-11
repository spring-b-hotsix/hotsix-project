package com.sparta.hotsixproject.carduser.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardUser is a Querydsl query type for CardUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardUser extends EntityPathBase<CardUser> {

    private static final long serialVersionUID = -15631421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardUser cardUser = new QCardUser("cardUser");

    public final com.sparta.hotsixproject.card.entity.QCard card;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sparta.hotsixproject.user.entity.QUser user;

    public QCardUser(String variable) {
        this(CardUser.class, forVariable(variable), INITS);
    }

    public QCardUser(Path<? extends CardUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardUser(PathMetadata metadata, PathInits inits) {
        this(CardUser.class, metadata, inits);
    }

    public QCardUser(Class<? extends CardUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.sparta.hotsixproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.hotsixproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

