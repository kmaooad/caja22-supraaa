package edu.kmaooad.config;

import edu.kmaooad.commandHandlers.CommandHandler;
import edu.kmaooad.commandHandlers.OtherCommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class BeanConfiguration {

    private OtherCommandHandler otherCommandHandler;

    // todo: add your handler as a parameter of the method and add its instance to the map
    @Bean
    public Map<Long, CommandHandler> getCommandHandlerMap() {
        Map<Long, CommandHandler> map = new HashMap<>() {
            @Override
            public CommandHandler get(Object key) {
                return this.getOrDefault(key, otherCommandHandler);
            }
        };
        return map;
    }

}
