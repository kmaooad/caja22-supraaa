package edu.kmaooad.services.interfaces;

public interface BanUserService {

    void addBanForUser(Long userId);
    void deleteBanForUser(Long userId);
    boolean isUserBanned(Long userId);

}
