package ua.lpnu.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.lpnu.payment_service.dto.UserResponse;

@FeignClient(name = "user-service", url = "${services.user.url}")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUserById(@PathVariable Long id);
}