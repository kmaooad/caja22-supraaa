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

    private Long userId;

    private String[] args;

}
