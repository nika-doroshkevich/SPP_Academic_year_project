package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

//    @PostMapping(value = {"/login"})
//    public String login(@ModelAttribute("user") User user, Model model) {
//        User currentUser = userService.loadOrSave(user);
//        model.addAttribute("currentUser", currentUser);
//
//        List<User> users = userService.findAll();
//        model.addAttribute("users", users);
//        return "messenger-page";
//    }

    @GetMapping("/")
    public String greeting(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("currentUserId", user.getId());
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        var avatarName = userService.getAvatarByUsername(principal.getName());
        var avatar = "http://localhost:8080/images/" + avatarName;
        model.addAttribute("avatar", avatar);
        return "messenger-page";
    }
}
