package ua.lpnu.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.user_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}