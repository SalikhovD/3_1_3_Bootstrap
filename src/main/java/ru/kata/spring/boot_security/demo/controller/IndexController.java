package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class IndexController {

    private final UserService service;

    IndexController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String index() {
        service.preCreateUsers();
        return "index";
    }
}
