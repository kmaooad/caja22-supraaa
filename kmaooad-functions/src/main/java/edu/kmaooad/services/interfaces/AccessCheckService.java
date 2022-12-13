package edu.kmaooad.services.interfaces;

import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.models.ResourceType;

public interface AccessCheckService {

    // preferred signature
    boolean hasAccess(Long userId, Long resourceId, ResourceType resourceType, Long commandId) throws IncorrectResourceParamsException;

    boolean hasAccess(Long userId, Long resourceId, Long commandId) throws IncorrectResourceParamsException;
}
