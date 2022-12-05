package edu.kmaooad.events.listeners;

import edu.kmaooad.apiCommunication.TelegramWebClient;
import edu.kmaooad.events.HandlerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandlerEventListener {

    private final TelegramWebClient tgClient;

    @Async
    @EventListener
    public void onHandlerEvent(HandlerEvent e) {
        tgClient.sendMessage(e.getMessage(), e.getChatId());
    }

}
