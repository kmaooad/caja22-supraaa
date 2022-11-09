package edu.kmaooad.services.interfaces;

public interface BanService {

    void addBanForUser(Long userId);
    void addBanForDepartment(Long departmentId);
    void addBanForOrganization(Long organizationId);

    void deleteBanForUser(Long userId);
    void deleteBanForDepartment(Long departmentId);
    void deleteBanForOrganization(Long organizationId);

    boolean isUserBanned(Long userId);
    boolean isDepartmentBanned(Long departmentId);
    boolean isOrganizationBanned(Long organizationId);

}
