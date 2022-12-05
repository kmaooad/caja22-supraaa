package edu.kmaooad.services.interfaces;

import edu.kmaooad.models.ResourceType;

public interface AccessCheckService {

    boolean hasAccess(Long userId, Long resourceId, Long commandId);

    // preferred signature
    boolean hasAccess(Long userId, Long resourceId, ResourceType resourceType, Long commandId);
}
