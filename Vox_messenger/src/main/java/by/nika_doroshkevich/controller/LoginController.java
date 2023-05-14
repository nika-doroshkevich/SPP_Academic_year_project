package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.security.AppAuthException;
import by.nika_doroshkevich.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, ModelMap model) {
        if (error != null) {
            AppAuthException authException = AppAuthException.valueOfCode(error);
            model.addAttribute("error", authException.getAppException());
        }

        model.addAttribute("user", new User());
        return new ModelAndView("login", model);
    }

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
