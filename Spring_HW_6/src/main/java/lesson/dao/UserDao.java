package lesson.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import lesson.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}