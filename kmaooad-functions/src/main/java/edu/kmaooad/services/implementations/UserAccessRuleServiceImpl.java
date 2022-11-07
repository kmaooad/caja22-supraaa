package edu.kmaooad.services.implementations;

import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.UserAccessRule;
import edu.kmaooad.repositories.UserAccessRuleRepository;
import edu.kmaooad.services.interfaces.UserAccessRuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAccessRuleServiceImpl implements UserAccessRuleService {

    private UserAccessRuleRepository userAccessRuleRepository;

    /**
     * saves the rule if id is not present, otherwise updates the rule
     * */
    public UserAccessRule create(Long userId, Long resourceId, Long commandId, boolean allowed) {
        return userAccessRuleRepository.save(new UserAccessRule(userId, resourceId, commandId, allowed));
    }

    public void deleteById(Long userId, Long resourceId, Long commandId) {
        userAccessRuleRepository.deleteById(new CompositeKey(userId, resourceId, commandId));
    }

    public UserAccessRule getById(Long userId, Long resourceId, Long commandId) {
        return userAccessRuleRepository.findById(new CompositeKey(userId, resourceId, commandId)).orElseThrow(() -> new NotFoundException(UserAccessRule.class.getSimpleName()));
    }

}
