package com.softserve.itacademy.controller;

import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.softserve.itacademy.exception.UserAlreadyExistsException;
import com.softserve.itacademy.json_model.JsonUserRequest;
import com.softserve.itacademy.json_model.JsonUserResponse;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping("/create")
//    public ResponseEntity create(@RequestBody JsonUserRequest user) {
//        return ResponseEntity.ok(user);
//    }

    @PostMapping(value = "",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String create(@Validated @ModelAttribute("user") User user, BindingResult result) {
    public ResponseEntity createUser(@RequestBody @Valid JsonUserRequest userRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(result.getAllErrors().stream().distinct().map(o -> o.toString()).collect(Collectors.toList()));
            }
            User user = JsonUserRequest.toUser(userRequest);
            user.setPassword(user.getPassword());
            user.setRole(roleService.readById(2L));
            User newUser = userService.create(user);
//        return "redirect:/todos/all/users/" + newUser.getId();
            //return ResponseEntity.ok(JsonUserResponse.toJson(user));
            return ResponseEntity.status(HttpStatus.CREATED).body(JsonUserResponse.toJson(user));
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable long id) {
        User user = userService.readById(id);
        return ResponseEntity.ok(JsonUserResponse.toJson(user));
    }

//    @GetMapping("/{id}/update")
//    public String update(@PathVariable long id, Model model) {
//        User user = userService.readById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.getAll());
//        return "update-user";
//    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody JsonUserRequest userRequest, BindingResult result) {
//        if (result.hasErrors()) {
//            user.setRole(oldUser.getRole());
//            model.addAttribute("roles", roleService.getAll());
//            return "update-user";
//        }
        User oldUser = userService.readById(id);
        oldUser.setFirstName(userRequest.getFirst_name());
        oldUser.setLastName(userRequest.getLast_name());
        oldUser.setEmail(userRequest.getEmail());
        oldUser.setPassword(userRequest.getPassword());

        if (oldUser.getRole().getName().equals("USER")) {
            oldUser.setRole(oldUser.getRole());
        } else {
            oldUser.setRole(roleService.readById(userRequest.getRoleId()));
        }
        userService.update(oldUser);
        return ResponseEntity.status(HttpStatus.OK).build();
       // return "redirect:/users/" + id + "/read";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
        //return "redirect:/users/all";
    }

    @GetMapping("")
    public ResponseEntity getAll() {
       return new ResponseEntity<>(JsonUserResponse.toJsonList(userService.getAll()), HttpStatus.OK);
    }
}
