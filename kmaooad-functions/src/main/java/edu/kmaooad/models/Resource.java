package edu.kmaooad.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Resource {

    @Id
    private Long id;

    private ResourceCompositeField resource;

    public Resource() {}

    public Resource(Long id, Long realId, ResourceType type) {
        this.id = id;
        this.resource = new ResourceCompositeField(realId,type);
    }

}
