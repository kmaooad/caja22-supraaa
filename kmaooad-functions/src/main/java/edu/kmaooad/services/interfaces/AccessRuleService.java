package edu.kmaooad.services.interfaces;

import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.IssuerType;
import org.springframework.stereotype.Service;

@Service
public interface AccessRuleService {

    AccessRule upsert(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId, boolean allowed);
    void deleteById(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId);
    AccessRule getById(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId);

}
