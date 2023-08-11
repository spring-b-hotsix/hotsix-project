package com.sparta.hotsixproject.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = 1217053837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final com.sparta.hotsixproject.common.entity.QTimeStamped _super = new com.sparta.hotsixproject.common.entity.QTimeStamped(this);

    public final ListPath<com.sparta.hotsixproject.attachment.entity.Attachment, com.sparta.hotsixproject.attachment.entity.QAttachment> attachmentList = this.<com.sparta.hotsixproject.attachment.entity.Attachment, com.sparta.hotsixproject.attachment.entity.QAttachment>createList("attachmentList", com.sparta.hotsixproject.attachment.entity.Attachment.class, com.sparta.hotsixproject.attachment.entity.QAttachment.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.cardlabel.entity.CardLabel, com.sparta.hotsixproject.cardlabel.entity.QCardLabel> cardLabelList = this.<com.sparta.hotsixproject.cardlabel.entity.CardLabel, com.sparta.hotsixproject.cardlabel.entity.QCardLabel>createList("cardLabelList", com.sparta.hotsixproject.cardlabel.entity.CardLabel.class, com.sparta.hotsixproject.cardlabel.entity.QCardLabel.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser> cardUserList = this.<com.sparta.hotsixproject.carduser.entity.CardUser, com.sparta.hotsixproject.carduser.entity.QCardUser>createList("cardUserList", com.sparta.hotsixproject.carduser.entity.CardUser.class, com.sparta.hotsixproject.carduser.entity.QCardUser.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.hotsixproject.checklist.entity.Checklist, com.sparta.hotsixproject.checklist.entity.QChecklist> checklistList = this.<com.sparta.hotsixproject.checklist.entity.Checklist, com.sparta.hotsixproject.checklist.entity.QChecklist>createList("checklistList", com.sparta.hotsixproject.checklist.entity.Checklist.class, com.sparta.hotsixproject.checklist.entity.QChecklist.class, PathInits.DIRECT2);

    public final StringPath color = createString("color");

    public final ListPath<com.sparta.hotsixproject.comment.entity.Comment, com.sparta.hotsixproject.comment.entity.QComment> commentList = this.<com.sparta.hotsixproject.comment.entity.Comment, com.sparta.hotsixproject.comment.entity.QComment>createList("commentList", com.sparta.hotsixproject.comment.entity.Comment.class, com.sparta.hotsixproject.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> due = createDateTime("due", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> position = createNumber("position", Integer.class);

    public final com.sparta.hotsixproject.side.entity.QSide side;

    public final com.sparta.hotsixproject.user.entity.QUser user;

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.side = inits.isInitialized("side") ? new com.sparta.hotsixproject.side.entity.QSide(forProperty("side"), inits.get("side")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.hotsixproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

