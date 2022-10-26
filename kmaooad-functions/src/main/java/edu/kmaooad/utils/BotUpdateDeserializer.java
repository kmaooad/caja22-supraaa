package edu.kmaooad.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Message;

import java.io.IOException;

public class BotUpdateDeserializer extends StdDeserializer<BotUpdate> {

    public BotUpdateDeserializer() {
        this(null);
    }

    public BotUpdateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BotUpdate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode treeNode = jp.getCodec().readTree(jp);
        BotUpdate botUpdate = new BotUpdate();
        botUpdate.setUpdateId(treeNode.get("update_id").longValue());
        Message message = new Message();
        message.setMessageId(treeNode.get("message").get("message_id").longValue());
        message.setDate(treeNode.get("message").get("date").longValue());
        message.setText(treeNode.get("message").get("text").textValue());
        Message.From from = new Message.From();
        from.setId(treeNode.get("message").get("from").get("id").longValue());
        from.setBot(treeNode.get("message").get("from").get("is_bot").booleanValue());
        from.setUsername(treeNode.get("message").get("from").get("username").textValue());
        from.setFirstName(treeNode.get("message").get("from").get("first_name").textValue());
        from.setLanguageCode(treeNode.get("message").get("from").get("language_code").textValue());
        Message.Chat chat = new Message.Chat();
        chat.setId(treeNode.get("message").get("chat").get("id").longValue());
        chat.setUsername(treeNode.get("message").get("chat").get("username").textValue());
        chat.setFirstName(treeNode.get("message").get("chat").get("first_name").textValue());
        chat.setType(treeNode.get("message").get("chat").get("type").textValue());
        message.setChat(chat);
        message.setFrom(from);
        botUpdate.setMessage(message);
        return botUpdate;
    }
}
