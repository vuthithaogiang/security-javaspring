package com.giang.security.demo;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @GetMapping
    public String get() {

        return "GET :: user";

    }

    @PostMapping
    public String  post() {
        return "POST :: user";
    }

}
