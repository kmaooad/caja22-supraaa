package edu.kmaooad.services.interfaces;

public interface BanDepartmentService {

    void addBanForDepartment(Long departmentId);
    void deleteBanForDepartment(Long departmentId);
    boolean isDepartmentBanned(Long departmentId);

}
