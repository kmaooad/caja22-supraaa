package edu.kmaooad.services.interfaces;

public interface BanOrganizationService {

    void addBanForOrganization(Long organizationId);
    void deleteBanForOrganization(Long organizationId);
    boolean isOrganizationBanned(Long organizationId);

}
