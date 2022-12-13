//package edu.kmaooad;
//
//import edu.kmaooad.apiCommunication.TelegramWebClient;
//import edu.kmaooad.events.HandlerEvent;
//import edu.kmaooad.events.listeners.HandlerEventListener;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//import static org.mockito.Mockito.*;
//
//public class EventsTest extends BaseTest {
//
//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        @Primary
//        public HandlerEventListener eventListener() {
//            HandlerEventListener listener = mock(HandlerEventListener.class);
//            doNothing().when(listener).onHandlerEvent(any(HandlerEvent.class));
//            return listener;
//        }
//
//    }
//
//    @Test
//    public void publishEvent_withEventObjectProvided_telegramWebClientTriggered() {
//        HandlerEvent event = new HandlerEvent("Hello msg2", 569520498L);
//        applicationEventPublisher.publishEvent(event);
//        verify(eventListener, atLeastOnce()).onHandlerEvent(any());
//    }
//
//}
