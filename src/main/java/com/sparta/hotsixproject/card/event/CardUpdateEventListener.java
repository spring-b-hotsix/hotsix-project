package com.sparta.hotsixproject.card.event;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.notification.entity.Notification;
import com.sparta.hotsixproject.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.sparta.hotsixproject.notification.NotificationTitle.*;

@Slf4j(topic = "Event Listener")
@Component
@RequiredArgsConstructor
public class CardUpdateEventListener implements ApplicationListener<CardUpdateEvent> {
    private final NotificationService notificationService;

    @Override
    @TransactionalEventListener
    public void onApplicationEvent(CardUpdateEvent event) {
        Card card = event.getCard();
        if (event.getOldName() != event.getNewName()) {
            log.info("Update Card Name");
            Notification notification = new Notification(CARD_NAME_UPDATED, card.getName(), card.getModifiedAt(), event.getUser(), card.getSide().getBoard());
            notificationService.saveNotification(notification);
        }
        else if (event.getOldDescription() != event.getNewDescription()) {
            log.info("Update Card Description");
            Notification notification = new Notification(CARD_DESC_UPDATED, card.getDescription(), card.getModifiedAt(), event.getUser(), card.getSide().getBoard());
            notificationService.saveNotification(notification);
        }
        else {
            log.info("Update Card Color");
            Notification notification = new Notification(CARD_COLOR_UPDATED, card.getColor(), card.getModifiedAt(), event.getUser(), card.getSide().getBoard());
            notificationService.saveNotification(notification);
        }
    }
}
