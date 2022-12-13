package edu.kmaooad.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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