package lesson02.service;

import org.springframework.stereotype.Service;
import lesson02.domain.Product;
import lesson02.repository.ProductRepoImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service  //сервис общается с репозиторием
public class ProductServiceImpl {

    private ProductRepoImpl productRepo;

    public ProductServiceImpl(ProductRepoImpl productRepo) {
        this.productRepo = productRepo;
    }

    public Product getById(Long id){
        return productRepo.getById(id);
    }

    public List<Product> getAll(){
        List<Product> products = productRepo.getAll();
        products.sort(Comparator.comparingLong(Product::getId));
        return products;
    }

    public List<Product> getByPrice(Double start, Double end){
        return productRepo.getAll().stream()
                .filter(product-> product.getPrice() >= start && product.getPrice() <= end)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    public Product save(Product product){
        return productRepo.save(product);
    }

    public void removeById(Long id){
        productRepo.remove(id);
    }
}
