package com.sparta.hotsixproject.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -579810637L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.sparta.hotsixproject.common.entity.QTimeStamped _super = new com.sparta.hotsixproject.common.entity.QTimeStamped(this);

    public final com.sparta.hotsixproject.card.entity.QCard card;

    public final EnumPath<com.sparta.hotsixproject.comment.CommentStatus> commentStatus = createEnum("commentStatus", com.sparta.hotsixproject.comment.CommentStatus.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.sparta.hotsixproject.user.entity.QUser user;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.sparta.hotsixproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.hotsixproject.user.entity.QUser(forProperty("user")) : null;
    }

}

