package edu.kmaooad.webhook;

import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.repositories.BotUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("TelegramWebhook")
public class TelegramWebhook implements Function<BotUpdate, String> {

    @Autowired
    private BotUpdateRepository updateRepository;

    @Override
    public String apply(BotUpdate botUpdate) {
        // todo: parse botUpdate to a CommandCall
        // todo: call dispatcher with the CommandCall
        updateRepository.save(botUpdate);
        return "message_id=" + botUpdate.getMessage().getMessageId();
    }
}
