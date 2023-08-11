package com.sparta.hotsixproject.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 153707907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<com.sparta.hotsixproject.board.entity.Board, com.sparta.hotsixproject.board.entity.QBoard> boardList = this.<com.sparta.hotsixproject.board.entity.Board, com.sparta.hotsixproject.board.entity.QBoard>createList("boardList", com.sparta.hotsixproject.board.entity.Board.class, com.sparta.hotsixproject.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard> cardList = this.<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard>createList("cardList", com.sparta.hotsixproject.card.entity.Card.class, com.sparta.hotsixproject.card.entity.QCard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser> cardUserList = this.<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser>createList("cardUserList", com.sparta.hotsixproject.carduser.entity.CardUser.class, com.sparta.hotsixproject.carduser.entity.QCardUser.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath googleId = createString("googleId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> kakaoId = createNumber("kakaoId", Long.class);

    public final StringPath nickname = createString("nickname");

    public final com.sparta.hotsixproject.notification.entity.QNotification notification;

    public final StringPath password = createString("password");

    public final EnumPath<com.sparta.hotsixproject.user.UserRoleEnum> role = createEnum("role", com.sparta.hotsixproject.user.UserRoleEnum.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notification = inits.isInitialized("notification") ? new com.sparta.hotsixproject.notification.entity.QNotification(forProperty("notification"), inits.get("notification")) : null;
    }

}

