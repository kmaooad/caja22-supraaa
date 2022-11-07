package edu.kmaooad.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CompositeKey {
    private Long issuerId;
    private Long resourceId;
    private Long commandId;
}