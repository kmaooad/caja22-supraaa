package edu.kmaooad.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class UserAccessRule {

    @Id
    private CompositeKey id;
    private boolean allowed;

    public UserAccessRule() {}

    public UserAccessRule(Long userId, Long resourceId, Long commandId, boolean allowed) {
        this.id = new CompositeKey(userId, resourceId, commandId);
        this.allowed = allowed;
    }

}
