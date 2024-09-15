package org.example.product_management_system.controller;

import org.example.product_management_system.model.User;
import org.example.product_management_system.service.UserService;
import org.example.product_management_system.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;
    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(){
        logger.info("Getting all users");
        List<User> users = userService.getUsers();
        return  ResponseHandler.response("Users retrieved successfully", HttpStatus.OK, users);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") String userId) {
        try {
            Optional<User> user = userService.getUser(userId);
            return ResponseHandler.response("User retrieved successfully", HttpStatus.OK, user);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseHandler.response("User added successfully", HttpStatus.CREATED, user);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") String userId, @RequestParam(required = false) String name, @RequestParam(required = false) String password){
        try {
            userService.updateUser(userId, name, password);
            return ResponseHandler.response("User updated successfully", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId){
        try {
            userService.deleteUser(userId);
            return ResponseHandler.response("User deleted successfully", HttpStatus.OK, null);
        } catch (IllegalAccessException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
