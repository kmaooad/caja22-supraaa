package edu.kmaooad.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class OrganizationAccessRule {

    @Id
    private CompositeKey id;
    private boolean allowed;

    public OrganizationAccessRule() {}

    public OrganizationAccessRule(Long organizationId, Long resourceId, Long commandId, boolean allowed) {
        this.id = new CompositeKey(organizationId, resourceId, commandId);
        this.allowed = allowed;
    }

}
