package edu.kmaooad.services.interfaces;

public interface AccessCheckService {

    boolean hasAccess(Long userId, Long resourceId, Long commandId);
}
