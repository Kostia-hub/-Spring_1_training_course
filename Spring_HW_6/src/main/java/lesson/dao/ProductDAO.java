package lesson.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import lesson.domain.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(Double startPrice, Double endPrice);

}
