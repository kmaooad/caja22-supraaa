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

    private ResourceCompositeField realResource;

    public Resource() {}

    public Resource(Long id, Long realId, ResourceType type) {
        this.id = id;
        this.realResource = new ResourceCompositeField(realId,type);
    }

}
