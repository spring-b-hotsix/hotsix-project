package com.sparta.hotsixproject.card.event;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CardUpdateEvent extends ApplicationEvent {
    private final User user;
    private final Card card;
    private final String oldName;
    private final String newName;
    private final String oldDescription;
    private final String newDescription;
    private final String oldColor;
    private final String newColor;

    public CardUpdateEvent(Object source, User user, Card card, String oldName, String newName,
                            String oldDescription, String newDescription,
                            String oldColor, String newColor) {
        super(source);
        this.user = user;
        this.card = card;
        this.oldName = oldName;
        this.newName = newName;
        this.oldDescription = oldDescription;
        this.newDescription = newDescription;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }
}
