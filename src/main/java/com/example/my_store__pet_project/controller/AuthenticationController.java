package com.example.my_store__pet_project.controller;

import com.example.my_store__pet_project.dto.UsersDto;
import com.example.my_store__pet_project.enums.UserRole;
import com.example.my_store__pet_project.enums.UserStatus;
import com.example.my_store__pet_project.security.UserDetailsServiceImpl;
import com.example.my_store__pet_project.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {

    private final UsersService usersService;
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping("login")
    public String login(Model model) {
        log.info("Login page");
        model.addAttribute("isBlocked", userDetailsService.isBlocked());
        return "auth/login";
    }

    @GetMapping("403")
    public String error403() {
        log.info("403 page");
        return "auth/403Error";
    }

    @GetMapping("registration")
    public String registration() {
        log.info("registration page");
        return "auth/registration";
    }

    @PostMapping("registration")
    public String signUp(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String passwordRepeat,
                         @RequestParam String email,
                         @RequestParam(required = false) String termsAgree, Model model) {
        log.info("registration post page");
        String msg = null;
        if (username.equals("") || password.equals("") || email.equals("")) {
            msg = "must_filled";
        } else if (usersService.isUserNameExists(username)) {
            msg = "exists";
        } else if (!password.equals(passwordRepeat)) {
            msg = "pass_mismatch";
        } else if (termsAgree == null) {
            msg = "agree";
        }
        if (msg != null) {
            model.addAttribute("message", msg);
            return "auth/registration";
        }
        UsersDto usersDto = new UsersDto();
        usersDto.setDateAdded(LocalDateTime.now());
        usersDto.setName(username);
        usersDto.setPassword(password);
        usersDto.setEmail(email);
        usersDto.setRole(UserRole.USER);
        usersDto.setStatus(UserStatus.UNBLOCKED);

        usersService.createUser(usersDto);
        return "redirect:/auth/login";
    }
}
