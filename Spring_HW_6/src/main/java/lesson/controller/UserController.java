package lesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lesson.config.UserInfo;
import lesson.domain.User;
import lesson.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserInfo userInfo) {
        this.userService = userService;
    }

    @GetMapping
    public String getList(Model model,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String password) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable Long id) {
        User byId = userService.getById(id);
        model.addAttribute("user", byId == null ? new User() : byId);
        return "user";
    }

}
