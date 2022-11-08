package com.softserve.itacademy.controller;


import com.softserve.itacademy.json_model.JsonUserResponse;
import com.softserve.itacademy.service.UserService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

//    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = {"/", "home" })
    public ResponseEntity home() {
//        return "home";
        return new ResponseEntity<>(JsonUserResponse.toJsonList(userService.getAll()), HttpStatus.OK);
    }
}
