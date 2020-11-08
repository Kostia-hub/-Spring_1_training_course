package lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lesson.domain.Client;

public interface ClientDAO extends JpaRepository<Client, Long> {
}
