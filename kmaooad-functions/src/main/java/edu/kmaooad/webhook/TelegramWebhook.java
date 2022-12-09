package edu.kmaooad.webhook;

import edu.kmaooad.commandDispatcher.CommandDispatcher;
import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.processing.CommandParser;
import edu.kmaooad.repositories.BotUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("TelegramWebhook")
public class TelegramWebhook implements Function<BotUpdate, String> {

    @Autowired
    private BotUpdateRepository updateRepository;
    @Autowired
    private CommandParser commandParser;
    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public String apply(BotUpdate botUpdate) {
        try {
            CommandCall commandCall = commandParser.parse(botUpdate);
            commandDispatcher.dispatch(commandCall);
            updateRepository.save(botUpdate);
            return "message_id=" + botUpdate.getMessage().getMessageId();
        } catch (Exception e) {
            eventPublisher.publishEvent(new HandlerEvent("Error occurred: " + e.getMessage(), botUpdate.getMessage().getChat().getId()));
            return "Error occurred";
        }
    }

}
