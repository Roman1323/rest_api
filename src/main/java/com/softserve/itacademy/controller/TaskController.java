package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/api/todos")
public class TaskController {
    private final TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;

    public TaskController(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and (@toDoController.canRead(#todoId))")
//    @GetMapping("/create/todos/{todo_id}")
//    public String create(@PathVariable("todo_id") long todoId, Model model) {
//        model.addAttribute("task", new TaskDto());
//        model.addAttribute("todo", todoService.readById(todoId));
//        model.addAttribute("priorities", Priority.values());
//        return "create-task";
//    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and @toDoController.canRead(#todoId)")
    @PostMapping("/{todo_id}/tasks")
    public ResponseEntity create(@PathVariable("todo_id") long todoId,
                                 @Validated @RequestBody TaskDto taskDto, BindingResult result) {
        if (result.hasErrors()) {
//            model.addAttribute("todo", todoService.readById(todoId));
//            model.addAttribute("priorities", Priority.values());
//            return "create-task";
           return new ResponseEntity<>(taskDto, HttpStatus.NOT_FOUND);
           // return ResponseEntity.status(HttpStatus.NOT_FOUND).location(URI.create("api/todos/{todo_id}/tasks")).body(taskDto);
        }
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(taskDto.getTodoId()),
                stateService.getByName("New")
        );
        taskService.create(task);
        return ResponseEntity.ok(TaskTransformer.convertToDto(task));
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and @toDoController.canRead(#todoId)")
//    @GetMapping("/{todo_id}/tasks/{task_id}")
//    public String update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId, Model model) {
//        TaskDto taskDto = TaskTransformer.convertToDto(taskService.readById(taskId));
//        model.addAttribute("task", taskDto);
//        model.addAttribute("priorities", Priority.values());
//        model.addAttribute("states", stateService.getAll());
//        return "update-task";
//    }
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and (@toDoController.canRead(#todoId))")
    @PutMapping("/{todo_id}/tasks/{task_id}")
    public ResponseEntity<TaskDto> update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId,
                                          @Validated @RequestBody TaskDto taskDto, BindingResult result) {
        if (result.hasErrors()) {
//            model.addAttribute("priorities", Priority.values());
//            model.addAttribute("states", stateService.getAll());
//            return "update-task";
        return new ResponseEntity<>(taskDto, HttpStatus.NOT_FOUND);
        }
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(todoId),
                stateService.readById(taskDto.getStateId())
        );
        task.setId(taskId);
        taskService.update(task);
        return ResponseEntity.ok(taskDto);
    }

//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and @toDoController.canRead(#todoId)")
    @DeleteMapping("/{todo_id}/tasks/{task_id}")
    public ResponseEntity delete(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
