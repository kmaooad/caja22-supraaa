package edu.kmaooad.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HandlerEvent {

    private String message;

    private Long chatId;

}
