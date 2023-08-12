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

    public static final QUser user = new QUser("user");

    public final ListPath<com.sparta.hotsixproject.board.entity.Board, com.sparta.hotsixproject.board.entity.QBoard> boardList = this.<com.sparta.hotsixproject.board.entity.Board, com.sparta.hotsixproject.board.entity.QBoard>createList("boardList", com.sparta.hotsixproject.board.entity.Board.class, com.sparta.hotsixproject.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard> cardList = this.<com.sparta.hotsixproject.card.entity.Card, com.sparta.hotsixproject.card.entity.QCard>createList("cardList", com.sparta.hotsixproject.card.entity.Card.class, com.sparta.hotsixproject.card.entity.QCard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser> cardUserList = this.<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser>createList("cardUserList", com.sparta.hotsixproject.carduser.entity.CardUser.class, com.sparta.hotsixproject.carduser.entity.QCardUser.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath googleId = createString("googleId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Long> kakaoId = createNumber("kakaoId", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<com.sparta.hotsixproject.user.UserRoleEnum> role = createEnum("role", com.sparta.hotsixproject.user.UserRoleEnum.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

