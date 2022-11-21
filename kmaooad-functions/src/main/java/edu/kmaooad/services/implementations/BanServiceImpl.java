package edu.kmaooad.services.implementations;

import edu.kmaooad.models.BannedDepartment;
import edu.kmaooad.models.BannedOrganization;
import edu.kmaooad.models.BannedUser;
import edu.kmaooad.repositories.BannedDepartmentRepository;
import edu.kmaooad.repositories.BannedOrganizationRepository;
import edu.kmaooad.repositories.BannedUserRepository;
import edu.kmaooad.services.interfaces.BanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanServiceImpl implements BanService {

    private final BannedUserRepository bannedUserRepository;
    private final BannedDepartmentRepository bannedDepartmentRepository;
    private final BannedOrganizationRepository bannedOrganizationRepository;

    @Override
    public void addBanForUser(Long userId) {
        bannedUserRepository.save(new BannedUser(userId));
    }

    @Override
    public void addBanForDepartment(Long departmentId) {
        bannedDepartmentRepository.save(new BannedDepartment(departmentId));
    }

    @Override
    public void addBanForOrganization(Long organizationId) {
        bannedOrganizationRepository.save(new BannedOrganization(organizationId));
    }

    @Override
    public void deleteBanForUser(Long userId) {
        bannedUserRepository.deleteById(userId);
    }

    @Override
    public void deleteBanForDepartment(Long departmentId) {
        bannedDepartmentRepository.deleteById(departmentId);
    }

    @Override
    public void deleteBanForOrganization(Long organizationId) {
        bannedOrganizationRepository.deleteById(organizationId);
    }

    @Override
    public boolean isUserBanned(Long userId) {
        return bannedUserRepository.existsById(userId);
    }

    @Override
    public boolean isDepartmentBanned(Long departmentId) {
        return bannedDepartmentRepository.existsById(departmentId);
    }

    @Override
    public boolean isOrganizationBanned(Long organizationId) {
        return bannedOrganizationRepository.existsById(organizationId);
    }

}
