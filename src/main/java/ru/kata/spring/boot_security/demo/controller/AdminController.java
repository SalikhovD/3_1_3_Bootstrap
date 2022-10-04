package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collections;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("principalUser", service.findByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("userList", service.listUsers());
        model.addAttribute("roles", Role.values());
        return "all-users";
    }

    @PostMapping(value = "/delete-user")
    public String deleteUser(@RequestParam Long id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/updateInfo")
    public String getUser(@RequestParam Long id, Model model) {
        User user = service.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-info";
    }

    @GetMapping("/new-user")
    public String newUser(Principal principal, Model model) {
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        User principalUser = service.findByUsername(principal.getName());
        model.addAttribute("principal", principalUser);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-info";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        service.saveOrUpdate(user);
        return "redirect:/admin";
    }
}