package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> userList = service.listUsers();
        model.addAttribute("userList", userList);
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

    @PostMapping("/new-user")
    public String newUser(Model model) {
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-info";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam Map<String, String> form) {
        Set<Role> roleSet = new HashSet<>();

        Collection<String> allRolesAsString = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        for (String field : form.keySet()) {
            if (allRolesAsString.contains(field)) {
                Role role = Role.valueOf(field);
                roleSet.add(role);
            }
        }
        user.setRoles(roleSet);
        service.saveOrUpdate(user);
        return "redirect:/admin";
    }
}