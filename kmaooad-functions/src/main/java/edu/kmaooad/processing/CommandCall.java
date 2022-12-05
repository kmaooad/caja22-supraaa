package edu.kmaooad.processing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandCall {

    private Long commandId;

    private Long chatId;

    // todo: add chat_id field

    // todo: to check access rule for this command inside dispatcher resourceId and resourceType are required,
    //  so maybe those can also be included in the CommandCall?

    private Long userId;

    private String[] args;

}
