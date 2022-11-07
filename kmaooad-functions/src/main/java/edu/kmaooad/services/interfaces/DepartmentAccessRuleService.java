package edu.kmaooad.services.interfaces;

import edu.kmaooad.models.DepartmentAccessRule;

public interface DepartmentAccessRuleService {

    DepartmentAccessRule create(Long departmentId, Long resourceId, Long commandId, boolean allowed);
    void deleteById(Long departmentId, Long resourceId, Long commandId);
    DepartmentAccessRule getById(Long departmentId, Long resourceId, Long commandId);

}
