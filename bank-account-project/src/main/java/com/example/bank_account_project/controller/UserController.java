package com.example.bank_account_project.controller;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.service.UserService;
import com.example.bank_account_project.utils.validable.Validable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService usersService;

    @PostMapping("/search-user")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("search") String search) {
        List<UserDto> users = usersService.searchUsers(search);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Validable<UserDto>> createUser(@RequestBody UserDto usersDto) {
        Validable<UserDto> createdUser = usersService.createUser(usersDto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return usersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Validable<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto usersDto) {
        Validable<UserDto> updatedUser = usersService.updateUser(id, usersDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
