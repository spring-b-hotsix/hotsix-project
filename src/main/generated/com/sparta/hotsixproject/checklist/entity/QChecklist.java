package com.sparta.hotsixproject.checklist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChecklist is a Querydsl query type for Checklist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChecklist extends EntityPathBase<Checklist> {

    private static final long serialVersionUID = -41199469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChecklist checklist = new QChecklist("checklist");

    public final com.sparta.hotsixproject.card.entity.QCard card;

    public final ListPath<com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem, com.sparta.hotsixproject.checklist.checklistItem.entity.QChecklistItem> checklistItems = this.<com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem, com.sparta.hotsixproject.checklist.checklistItem.entity.QChecklistItem>createList("checklistItems", com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem.class, com.sparta.hotsixproject.checklist.checklistItem.entity.QChecklistItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QChecklist(String variable) {
        this(Checklist.class, forVariable(variable), INITS);
    }

    public QChecklist(Path<? extends Checklist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChecklist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChecklist(PathMetadata metadata, PathInits inits) {
        this(Checklist.class, metadata, inits);
    }

    public QChecklist(Class<? extends Checklist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.sparta.hotsixproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
    }

}

