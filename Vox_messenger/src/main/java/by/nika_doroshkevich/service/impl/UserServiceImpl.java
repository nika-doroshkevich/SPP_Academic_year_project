package by.nika_doroshkevich.service.impl;

import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.repository.UserRepository;
import by.nika_doroshkevich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User loadOrSave(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            return foundUser;
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void saveAvatarImage(String username, String avatarImage) {
        User user = userRepository.findByUsername(username);
        user.setAvatarImage(avatarImage);
        userRepository.save(user);
    }

    public String getAvatarByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAvatarImage();
    }
}
