package ua.lpnu.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.user_service.dto.CreateUserRequest;
import ua.lpnu.user_service.dto.UpdateUserRequest;
import ua.lpnu.user_service.model.User;
import ua.lpnu.user_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}