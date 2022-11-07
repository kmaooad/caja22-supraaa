package edu.kmaooad.services.implementations;

import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.OrganizationAccessRule;
import edu.kmaooad.repositories.OrganizationAccessRuleRepository;
import edu.kmaooad.services.interfaces.OrganizationAccessRuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationAccessRuleServiceImpl implements OrganizationAccessRuleService {

    private OrganizationAccessRuleRepository organizationAccessRuleRepository;

    /**
     * saves the rule if id is not present, otherwise updates the rule
     * */
    public OrganizationAccessRule create(Long userId, Long resourceId, Long commandId, boolean allowed) {
        return organizationAccessRuleRepository.save(new OrganizationAccessRule(userId, resourceId, commandId, allowed));
    }

    public void deleteById(Long userId, Long resourceId, Long commandId) {
        organizationAccessRuleRepository.deleteById(new CompositeKey(userId, resourceId, commandId));
    }

    public OrganizationAccessRule getById(Long userId, Long resourceId, Long commandId) {
        return organizationAccessRuleRepository.findById(new CompositeKey(userId, resourceId, commandId)).orElseThrow(() -> new NotFoundException(OrganizationAccessRule.class.getSimpleName()));
    }

}
