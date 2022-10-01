package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String personalPage(Principal principal, Model model) {
        User user = service.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "personal-page";
    }

}
