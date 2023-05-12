package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Objects;

@Controller
public class UploadController {

    private final UserServiceImpl userService;
    private final Path root = Paths.get("./uploads");

    public UploadController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file, Principal principal) throws IOException {
        StringBuilder fileNames = new StringBuilder();

        var fileName = file.getOriginalFilename();
        var fileNameArray = Objects.requireNonNull(fileName).split("\\.");
        var extension = fileNameArray[fileNameArray.length - 1];

        User user = userService.getUserByUsername(principal.getName());
        var currentUserId = user.getId();
        var fileNameForSave = currentUserId + "." + extension;
        userService.saveAvatarImage(principal.getName(), fileNameForSave);

        fileNames.append(file.getOriginalFilename());
        Files.copy(file.getInputStream(), this.root.resolve(fileNameForSave), StandardCopyOption.REPLACE_EXISTING);
        model.addAttribute("msg", "Uploaded images: " + fileNames);
        var avatarName = userService.getAvatarByUsername(principal.getName());
        var avatar = "http://localhost:8080/images/" + avatarName;
        model.addAttribute("avatar", avatar);
        model.addAttribute("currentUserId", user.getId());
        return "messenger-page";
    }
}
