package edu.kmaooad.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class DepartmentAccessRule {

    @Id
    private CompositeKey id;
    private boolean allowed;

    public DepartmentAccessRule() {}

    public DepartmentAccessRule(Long departmentId, Long resourceId, Long commandId, boolean allowed) {
        this.id = new CompositeKey(departmentId, resourceId, commandId);
        this.allowed = allowed;
    }

}
