package ua.com.foxmined.carrestservice.service.appuserservice;

import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.user.UserRepository;
import ua.com.foxmined.carrestservice.model.AppUser;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final UserRepository userRepository;

    public AppUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(AppUser appUser) {
        userRepository.save(appUser);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
