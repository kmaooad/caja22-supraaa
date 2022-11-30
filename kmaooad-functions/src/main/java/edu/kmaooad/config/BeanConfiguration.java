package edu.kmaooad.config;

import edu.kmaooad.commandHandlers.CommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfiguration {

    // todo: add your handler as a parameter of the method and add its instance to the map
    @Bean
    public Map<Long, CommandHandler> getCommandHandlerMap() {
        Map<Long, CommandHandler> map = new HashMap<>() {
            @Override
            public CommandHandler get(Object key) {
                // todo: set OtherCommandHandler instance as a default value instead of null
                return this.getOrDefault(key, null);
            }
        };
        return map;
    }

}
