package edu.kmaooad.commandHandlers;

import edu.kmaooad.apiCommunication.OtherMicroservicesWebClient;
import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.Command;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.CommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OtherCommandHandler implements CommandHandler{

    private final CommandService commandService;
    private final OtherMicroservicesWebClient otherMicroservicesWebClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OtherCommandHandler(CommandService commandService, OtherMicroservicesWebClient otherMicroservicesWebClient, ApplicationEventPublisher applicationEventPublisher) {
        this.commandService = commandService;
        this.otherMicroservicesWebClient = otherMicroservicesWebClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void handle(CommandCall commandCall) {
        Command command = commandService.getCommandById(commandCall.getCommandId());
        String functionUrl = command.getFunctionUrl();
        String fullCommand = command.getName() + " " + String.join(" ", commandCall.getArgs());
        Mono<String> response = this.otherMicroservicesWebClient.sendPostRequest(functionUrl, fullCommand);
        response.subscribe(value -> {
            System.out.println("Got response: " + value);
            applicationEventPublisher.publishEvent(new HandlerEvent(value, commandCall.getChatId()));
        });
        System.out.println("Command sent - " + command.getName());
        applicationEventPublisher.publishEvent(new HandlerEvent("Command " + command.getName() + " being processed..", commandCall.getChatId()));
    }

}