package edu.kmaooad.services.interfaces;

import edu.kmaooad.models.UserAccessRule;

public interface UserAccessRuleService {

    UserAccessRule create(Long userId, Long resourceId, Long commandId, boolean allowed);
    void deleteById(Long userId, Long resourceId, Long commandId);

    UserAccessRule getById(Long userId, Long resourceId, Long commandId);

}
