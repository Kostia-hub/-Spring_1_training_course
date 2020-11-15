package lesson.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import lesson.domain.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getById(Long id);
    User auth(String name, String password);

    List<User> getAll();
}
