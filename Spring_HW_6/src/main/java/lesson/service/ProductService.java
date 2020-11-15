package lesson.service;

import lesson.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);


    Product save(Product product);

    Product getMaxPrice(List p);

    Product getMinPrice(List p);

    List<Product> getByPrice(double priceFrom, double priceTo);
}
