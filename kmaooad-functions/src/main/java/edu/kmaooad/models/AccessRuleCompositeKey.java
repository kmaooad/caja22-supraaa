package edu.kmaooad.models;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccessRuleCompositeKey {
    private Long issuerId;
    private IssuerType issuerType;
    private Long resourceId;
    private Long commandId;
}