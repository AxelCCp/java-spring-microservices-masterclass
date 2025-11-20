package com.spring.ecom_app.controller;

import com.spring.ecom_app.model.dto.UserRequest;
import com.spring.ecom_app.model.dto.UserResponse;
import com.spring.ecom_app.model.entity.User;
import com.spring.ecom_app.model.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id) {
        /*Optional<User> user_op = this.userService.getUser(id);
        if(user_op.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singleton("User not found"));
        } else {
            return ResponseEntity.ok(user_op.get());
        }*/
        return this.userService.getUser(id).map(u -> ResponseEntity.ok().body(u)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserRequest userRequest) {
        this.userService.create(userRequest);
        return ResponseEntity.ok("User added ok!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody UserRequest userRequest) {
        Boolean update = this.userService.update(id, userRequest);
        if(update) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated ok");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User no found by id");
        }
    }
}
