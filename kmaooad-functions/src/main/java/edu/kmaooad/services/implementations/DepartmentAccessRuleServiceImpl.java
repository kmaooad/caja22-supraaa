package edu.kmaooad.services.implementations;

import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.DepartmentAccessRule;
import edu.kmaooad.repositories.DepartmentAccessRuleRepository;
import edu.kmaooad.services.interfaces.DepartmentAccessRuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentAccessRuleServiceImpl implements DepartmentAccessRuleService {

    private DepartmentAccessRuleRepository departmentAccessRuleRepository;

    /**
     * saves the rule if id is not present, otherwise updates the rule
     * */
    public DepartmentAccessRule create(Long userId, Long resourceId, Long commandId, boolean allowed) {
        return departmentAccessRuleRepository.save(new DepartmentAccessRule(userId, resourceId, commandId, allowed));
    }

    public void deleteById(Long userId, Long resourceId, Long commandId) {
        departmentAccessRuleRepository.deleteById(new CompositeKey(userId, resourceId, commandId));
    }

    public DepartmentAccessRule getById(Long userId, Long resourceId, Long commandId) {
        return departmentAccessRuleRepository.findById(new CompositeKey(userId, resourceId, commandId)).orElseThrow(() -> new NotFoundException(DepartmentAccessRule.class.getSimpleName()));
    }

}
