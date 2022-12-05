package edu.kmaooad.commandHandlers;

import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.Command;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.CommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OtherCommandHandler implements CommandHandler{

    private final CommandService commandService;
    private final WebClient webClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OtherCommandHandler(CommandService commandService, ApplicationEventPublisher applicationEventPublisher) {
        this.commandService = commandService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.webClient = WebClient.create();
    }

    @Override
    public void handle(CommandCall commandCall) {
        Command command = commandService.getCommandById(commandCall.getCommandId());
        String functionUrl = command.getFunctionUrl();
        Mono<String> response = this.webClient.post()
                .uri(functionUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(r -> {
                    if (r.statusCode().equals(HttpStatus.OK)) {
                        return r.bodyToMono(String.class);
                    } else if (r.statusCode().is4xxClientError()) {
                        return Mono.just("Error response with code " + r.statusCode());
                    } else {
                        return r.createException().flatMap(Mono::error);
                    }
                });
        response.subscribe(value -> {
            System.out.println("Got response: " + value);
            //TODO: emit Event with ("Got response: " + value)
            applicationEventPublisher.publishEvent(new HandlerEvent(value, commandCall.getChatId()));
        });
        System.out.println("Command sent - " + command.getName());
        //TODO: emit Event with ("Command sent - " + command.getName())
        applicationEventPublisher.publishEvent(new HandlerEvent("Command " + command.getName() + " being processed..", commandCall.getChatId()));
    }

}