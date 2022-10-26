package edu.kmaooad.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private Long messageId;

    private Long date;

    private String text;

    private From from;

    private Chat chat;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class From {

        private Long id;

        private boolean isBot;

        private String firstName;

        private String username;

        private String languageCode;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Chat {

        private Long id;

        private String firstName;

        private String username;

        private String type;
    }

}

