package edu.kmaooad.services.implementations;

import edu.kmaooad.models.BannedDepartment;
import edu.kmaooad.repositories.BannedDepartmentRepository;
import edu.kmaooad.services.interfaces.BanDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanDepartmentServiceImpl implements BanDepartmentService {

    private final BannedDepartmentRepository bannedDepartmentRepository;

    @Override
    public void addBanForDepartment(Long departmentId) {
        bannedDepartmentRepository.save(new BannedDepartment(departmentId));
    }

    @Override
    public void deleteBanForDepartment(Long departmentId) {
        bannedDepartmentRepository.deleteById(departmentId);
    }

    @Override
    public boolean isDepartmentBanned(Long departmentId) {
        return bannedDepartmentRepository.existsById(departmentId);
    }

}
