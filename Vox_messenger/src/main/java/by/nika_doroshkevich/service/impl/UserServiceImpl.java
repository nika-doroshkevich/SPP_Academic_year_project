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

        User user = userRepository.findByUsername(username);
        /*if (users.size() > 1) {
            throw new IllegalStateException("More than one username with current username!");
        }

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);*/
        return user;
    }
}
