package com.sparta.hotsixproject.card.event;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j(topic = "비동기 처리를 위한 Custom EventPublisher")
@Component
@EnableAsync
@RequiredArgsConstructor
public class EventPublisher {
    public final ApplicationEventPublisher eventPublisher;

    @Async
    public void publishCardUpdatedEvent(User user, Card card, String oldName, String newName,
                                        String oldDescription, String newDescription,
                                        String oldColor, String newColor) {
        log.info("이벤트 생성");
        CardUpdateEvent event = new CardUpdateEvent(this, user, card, oldName, newName,
                oldDescription, newDescription, oldColor, newColor);
        eventPublisher.publishEvent(event);
    }
}
