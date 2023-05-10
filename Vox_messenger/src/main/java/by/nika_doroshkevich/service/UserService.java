package by.nika_doroshkevich.service;

import by.nika_doroshkevich.model.User;

import java.util.List;

public interface UserService {

    User loadOrSave(User user);

    List<User> findAll();

    User getUserByUsername(String username);
}
