package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.json_model.JsonToDoRequest;
import com.softserve.itacademy.json_model.JsonToDoResponse;
import com.softserve.itacademy.json_model.JsonUserRequest;
import com.softserve.itacademy.json_model.JsonUserResponse;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class ToDoController {

    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    public ToDoController(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }

//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.user.id == #ownerId")
//    @GetMapping("/create/users/{owner_id}")
//    public String create(@PathVariable("owner_id") long ownerId, Model model) {
//        model.addAttribute("todo", new ToDo());
//        model.addAttribute("ownerId", ownerId);
//        return "create-todo";
//    }

//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.user.id == #ownerId")
    @PostMapping(value = "/{user_id}/todos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@PathVariable("user_id") long user_id, @RequestBody  @Valid JsonToDoRequest toDoRequest,BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println(toDoRequest);
        ToDo todo = JsonToDoRequest.toModel(toDoRequest);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(userService.readById(user_id));
        todo = todoService.create(todo);
        return ResponseEntity.accepted().body(JsonToDoResponse.toJson(todo));
    }

//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') ")
    @GetMapping("/{user_id}/todos/{todo_id}/tasks")
    public ResponseEntity read(@PathVariable("user_id") long user_id, @PathVariable("todo_id") long todo_id) {
        ToDo todo = todoService.readById(todo_id);
        List<Task> tasks = taskService.getByTodoId(todo_id);
//        List<User> users = userService.getAll().stream()
//                .filter(user -> user.getId() != todo.getOwner().getId()).collect(Collectors.toList());
//        model.addAttribute("todo", todo);
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("users", users);
//        model.addAttribute("owner_id", todo.getOwner().getId());
        List<TaskDto> taskDtos = tasks.stream()
                .map(t-> TaskTransformer.convertToDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

//   @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.user.id == #ownerId")
//    @GetMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId, Model model) {
//        ToDo todo = todoService.readById(todoId);
//        model.addAttribute("todo", todo);
//        return "update-todo";
//    }
//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.user.id == #ownerId")

    @PutMapping("/{user_id}/todos/{todo_id}")
    public ResponseEntity update(@PathVariable("user_id") long user_id,@PathVariable("todo_id") long todo_id,
                         @RequestBody JsonToDoRequest toDoRequest) {
        ToDo oldTodo = todoService.readById(todo_id);
        oldTodo.setOwner(oldTodo.getOwner());
        oldTodo.setCollaborators(oldTodo.getCollaborators());
        oldTodo.setTitle(toDoRequest.getTitle());
        todoService.update(oldTodo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.user.id == #ownerId")
    @DeleteMapping("/{user_id}/todos/{todo_id}")
    public ResponseEntity delete(@PathVariable long todo_id, @PathVariable long user_id) {
        todoService.delete(todo_id);
        return ResponseEntity.status(HttpStatus.OK).build();
//        return "redirect:/todos/all/users/" + ownerId;
    }
//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{user_id}/todos")
    public ResponseEntity getAll(@PathVariable("user_id") long userId) {
        List<ToDo> todos = todoService.getByUserId(userId);
        return ResponseEntity.ok(todos.stream()
                .map(t-> JsonToDoResponse.toJson(t))
                .collect(Collectors.toList()));
    }

//    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/{user_id}/todos/{todo_id}/collaborators")
    public ResponseEntity addCollaborator(@PathVariable long user_id, @PathVariable long todo_id) {
        ToDo todo = todoService.readById(todo_id);
        List<User> collaborators = todo.getCollaborators();
        User collaborator = userService.readById(user_id);
        if(collaborators.add(collaborator)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        };
        todo.setCollaborators(collaborators);
        todoService.update(todo);
        //return ResponseEntity.ok(JsonUserResponse.toJson(collaborator));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//@PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/{user_id}/todos/{todo_id}/collaborators")
    public ResponseEntity removeCollaborator(@PathVariable long user_id, @PathVariable long todo_id) {
        ToDo todo = todoService.readById(todo_id);
        List<User> collaborators = todo.getCollaborators();
        collaborators.remove(userService.readById(user_id));
        todo.setCollaborators(collaborators);
        todoService.update(todo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


//    public boolean canRead(long id){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ToDo todo = todoService.readById(id);
//        return todo.getCollaborators()
//                .stream()
//                .anyMatch(u->u.getEmail()
//                        .equals(authentication.getName()));
//    }
}
