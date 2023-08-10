package com.sparta.hotsixproject.checklist.checklistItem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChecklistItem is a Querydsl query type for ChecklistItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChecklistItem extends EntityPathBase<ChecklistItem> {

    private static final long serialVersionUID = -260440773L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChecklistItem checklistItem = new QChecklistItem("checklistItem");

    public final BooleanPath checked = createBoolean("checked");

    public final com.sparta.hotsixproject.checklist.entity.QChecklist checklist;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChecklistItem(String variable) {
        this(ChecklistItem.class, forVariable(variable), INITS);
    }

    public QChecklistItem(Path<? extends ChecklistItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChecklistItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChecklistItem(PathMetadata metadata, PathInits inits) {
        this(ChecklistItem.class, metadata, inits);
    }

    public QChecklistItem(Class<? extends ChecklistItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.checklist = inits.isInitialized("checklist") ? new com.sparta.hotsixproject.checklist.entity.QChecklist(forProperty("checklist"), inits.get("checklist")) : null;
    }

}

