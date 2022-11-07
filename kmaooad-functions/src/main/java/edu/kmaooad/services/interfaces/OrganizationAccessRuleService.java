package edu.kmaooad.services.interfaces;


import edu.kmaooad.models.OrganizationAccessRule;

public interface OrganizationAccessRuleService {

    OrganizationAccessRule create(Long organizationId, Long resourceId, Long commandId, boolean allowed);
    void deleteById(Long organizationId, Long resourceId, Long commandId);
    OrganizationAccessRule getById(Long organizationId, Long resourceId, Long commandId);

}
