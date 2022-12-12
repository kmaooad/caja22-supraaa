package edu.kmaooad.services.implementations;

import edu.kmaooad.models.BannedUser;
import edu.kmaooad.repositories.BannedUserRepository;
import edu.kmaooad.services.interfaces.BanUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanUserServiceImpl implements BanUserService {

    private final BannedUserRepository bannedUserRepository;

    @Override
    public void addBanForUser(Long userId) {
        bannedUserRepository.save(new BannedUser(userId));
    }

    @Override
    public void deleteBanForUser(Long userId) {
        bannedUserRepository.deleteById(userId);
    }

    @Override
    public boolean isUserBanned(Long userId) {
        return bannedUserRepository.existsById(userId);
    }

}
