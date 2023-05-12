package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.dto.UserDto;
import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUsers() {
        List<User> listUsers = userServiceImpl.findAll();
        List<UserDto> listUserDto = new ArrayList<>();
        for (User user: listUsers) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            var avatar = "http://localhost:8080/images/" + user.getAvatarImage();
            userDto.setAvatarImage(avatar);
            listUserDto.add(userDto);
        }
        return listUserDto;
    }
}
