package edu.kmaooad.services.implementations;

import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.AccessRuleCompositeKey;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.repositories.AccessRuleRepository;
import edu.kmaooad.services.interfaces.AccessRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessRuleServiceImpl implements AccessRuleService {

    private final AccessRuleRepository accessRuleRepository;

    @Override
    public AccessRule upsert(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId, boolean allowed) {
        return accessRuleRepository.save(new AccessRule(issuerId, issuerType, resourceId, commandId, allowed));
    }

    @Override
    public void deleteById(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId) {
        accessRuleRepository.deleteById(new AccessRuleCompositeKey(issuerId, issuerType, resourceId, commandId));
    }

    @Override
    public Optional<AccessRule> getById(Long issuerId, IssuerType issuerType, Long resourceId, Long commandId) {
        return accessRuleRepository.findById(new AccessRuleCompositeKey(issuerId, issuerType, resourceId, commandId));
    }
}
