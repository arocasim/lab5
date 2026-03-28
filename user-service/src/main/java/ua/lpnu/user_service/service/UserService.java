package ua.lpnu.user_service.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.lpnu.user_service.dto.CreateUserRequest;
import ua.lpnu.user_service.dto.UpdateUserRequest;
import ua.lpnu.user_service.exception.BadRequestException;
import ua.lpnu.user_service.exception.NotFoundException;
import ua.lpnu.user_service.model.User;
import ua.lpnu.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User with this email already exists");
        }

        User user = new User(null, request.getName(), request.getEmail());
        return repository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable(value = "users", key = "#id")
    public User getById(Long id) {
        System.out.println("Getting user from DB: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    @CachePut(value = "users", key = "#id")
    public User update(Long id, UpdateUserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User with this email already exists");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return repository.save(user);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        repository.delete(user);
    }
}