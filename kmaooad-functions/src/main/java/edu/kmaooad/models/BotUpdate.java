package edu.kmaooad.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.kmaooad.utils.BotUpdateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("updates")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonDeserialize(using = BotUpdateDeserializer.class)
public class BotUpdate {

    @Id
    private Long updateId;

    private Message message;
}


