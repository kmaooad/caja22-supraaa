package edu.kmaooad.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class AccessRule {

    @Id
    private AccessRuleCompositeKey id;
    private boolean allowed;

    public AccessRule() {}

    public AccessRule(Long departmentId, IssuerType issuerType, Long resourceId, Long commandId, boolean allowed) {
        this.id = new AccessRuleCompositeKey(departmentId, issuerType, resourceId, commandId);
        this.allowed = allowed;
    }


}
