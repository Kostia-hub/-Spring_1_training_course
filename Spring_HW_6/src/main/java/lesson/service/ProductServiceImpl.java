package lesson.service;

import org.springframework.stereotype.Service;
import lesson.domain.Product;
import lesson.repository.ProductDAO;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
        init();
    }

    private void init(){

        productDAO.saveAll(Arrays.asList(
                new Product(null, "Cheese", 450.75),
                new Product(null, "Milk", 50.75),
                new Product(null, "Chocolate", 123.75),
                new Product(null, "Eggs", 75.0),
                new Product(null, "Bread", 45.0)
        ));

    }

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public Product getMaxPrice(List p) {
        List<Product> prodList = productDAO.findAll();
            prodList.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return prodList.get(0);
    }

    @Override
    public Product getMinPrice(List p) {
        List<Product> prodList = productDAO.findAll();
        prodList.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return prodList.get(prodList.size()-1);
    }

    @Override
    public List<Product> getByPrice(double priceFrom, double priceTo) {
        return productDAO.findAllByPriceBetween(priceFrom, priceTo);
    }
}
