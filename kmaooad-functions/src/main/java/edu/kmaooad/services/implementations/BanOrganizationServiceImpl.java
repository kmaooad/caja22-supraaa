package edu.kmaooad.services.implementations;

import edu.kmaooad.models.BannedOrganization;
import edu.kmaooad.repositories.BannedOrganizationRepository;
import edu.kmaooad.services.interfaces.BanOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanOrganizationServiceImpl implements BanOrganizationService {

    private final BannedOrganizationRepository bannedOrganizationRepository;

    @Override
    public void addBanForOrganization(Long organizationId) {
        bannedOrganizationRepository.save(new BannedOrganization(organizationId));
    }

    @Override
    public void deleteBanForOrganization(Long organizationId) {
        bannedOrganizationRepository.deleteById(organizationId);
    }

    @Override
    public boolean isOrganizationBanned(Long organizationId) {
        return bannedOrganizationRepository.existsById(organizationId);
    }

}
