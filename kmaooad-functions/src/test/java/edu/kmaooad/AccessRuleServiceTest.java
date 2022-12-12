package edu.kmaooad;

import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.AccessRuleCompositeKey;
import edu.kmaooad.models.IssuerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AccessRuleServiceTest extends BaseTest {

    @AfterEach
    public void afterEach() {
        accessRuleRepository.deleteAll();
    }

    @Test
    public void givenNewAccessRule_whenUpsert_shouldBeCreated() {
        long issuerId = 100L;
        IssuerType issuerType = IssuerType.USER;
        long resourceId = 200L;
        long commandId = 300L;
        boolean allowed = true;
        Assertions.assertFalse(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        accessRuleService.upsert(issuerId, issuerType, resourceId, commandId, allowed);
        Optional<AccessRule> accessRuleOptional = accessRuleService.getById(issuerId, issuerType, resourceId, commandId);
        Assertions.assertTrue(accessRuleOptional.isPresent());
        AccessRuleCompositeKey key = accessRuleOptional.get().getId();
        Assertions.assertEquals(key, new AccessRuleCompositeKey(issuerId, issuerType, resourceId, commandId));
    }

    @Test
    public void givenExistingAccessRule_whenUpsert_shouldBeUpdated() {
        long issuerId = 100L;
        IssuerType issuerType = IssuerType.USER;
        long resourceId = 200L;
        long commandId = 300L;
        boolean allowed = true;
        boolean newAllowed = false;
        Assertions.assertFalse(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        accessRuleService.upsert(issuerId, issuerType, resourceId, commandId, allowed);
        Assertions.assertTrue(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        accessRuleService.upsert(issuerId, issuerType, resourceId, commandId, newAllowed);
        Assertions.assertTrue(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        Assertions.assertEquals(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).get().isAllowed(), newAllowed);
    }

    @Test
    public void givenExistingAccessRule_whenDelete_shouldBeDeleted() {
        long issuerId = 100L;
        IssuerType issuerType = IssuerType.USER;
        long resourceId = 200L;
        long commandId = 300L;
        boolean allowed = true;
        accessRuleService.upsert(issuerId, issuerType, resourceId, commandId, allowed);
        Assertions.assertTrue(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        accessRuleService.deleteById(issuerId, issuerType, resourceId, commandId);
        Assertions.assertFalse(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
    }

    @Test
    public void givenExistingAccessRule_whenExistsById_shouldBeTrue() {
        long issuerId = 100L;
        IssuerType issuerType = IssuerType.USER;
        long resourceId = 200L;
        long commandId = 300L;
        boolean allowed = true;
        accessRuleService.upsert(issuerId, issuerType, resourceId, commandId, allowed);
        Assertions.assertTrue(accessRuleService.getById(issuerId, issuerType, resourceId, commandId).isPresent());
        Assertions.assertTrue(accessRuleService.existsById(issuerId, issuerType, resourceId, commandId));
    }

}
