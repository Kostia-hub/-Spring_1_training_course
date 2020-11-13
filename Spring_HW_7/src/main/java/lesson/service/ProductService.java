package lesson.service;

import lesson.domain.Product;
import lesson.repository.ProductDAO;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    //    List <ProductDTO> getAll();

    Product getById(Long id);
    //  ProductDto getById(Long id);

    Product save(Product product);
    //  ProductDto save(Product product);

    Product getMaxPrice(List p);
    //  ProductDto getMaxPrice(List p);

    Product getMinPrice(List p);
    //  ProductDto getMinPrice(List p);

    List<Product> getByPrice(double priceFrom, double priceTo);
    //    List <ProductDTO> getByPrice(double priceFrom, double priceTo);
}