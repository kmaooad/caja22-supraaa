package edu.kmaooad.config;

import edu.kmaooad.commandHandlers.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAsync
@AllArgsConstructor
public class BeanConfiguration {


    // todo: add your handler as a parameter of the method and add its instance to the map
    @Bean
    public Map<Long, CommandHandler> getCommandHandlerMap(OtherCommandHandler otherCommandHandler,
                                                          CreateAccessRuleHandler createAccessRuleHandler,
                                                          BanHandler banHandler,
                                                          UnbanHandler unbanHandler) {
        Map<Long, CommandHandler> map = new HashMap<>() {
            @Override
            public CommandHandler get(Object key) {
                return this.getOrDefault(key, otherCommandHandler);
            }
        };
        map.put(0L, createAccessRuleHandler);
        map.put(1L, banHandler);
        map.put(2L, unbanHandler);
        return map;
    }

}
