package com.softserve.itacademy.json_model;

import com.softserve.itacademy.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUserResponse {
    public Long id;

    private String first_name;

    private String last_name;

    private String email;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JsonUserResponse() {
    }

    public JsonUserResponse(Long id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public static JsonUserResponse toJson(User user){
        return new JsonUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getPassword());
    }

    public static List<JsonUserResponse> toJsonList(List<User> users){
        return users.stream()
                .map(u -> JsonUserResponse.toJson(u))
                .collect(Collectors.toList());
    }
}
