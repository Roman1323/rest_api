package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.UserAlreadyExistsException;
import com.softserve.itacademy.model.User;

import java.util.List;

public interface UserService {
    User create(User user) throws UserAlreadyExistsException;
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();
}
