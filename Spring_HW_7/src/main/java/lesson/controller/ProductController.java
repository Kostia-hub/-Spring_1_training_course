package lesson.controller;

import lesson.repository.ProductDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lesson.domain.Product;
import lesson.service.ProductService;
import lesson.exception.EntityNotFoundException;
import lesson.dto.EntityNotFoundResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private ProductDAO productDAO;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/products
    @GetMapping
    public List<Product> getAll(){
        return productDAO.findAll();
    }

    // http://localhost:8080/products/1 - GET
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        checkById(id);
        return productDAO.findById(id).orElse(null);
    }

    // http://localhost:8080/products/max - GET
    @GetMapping("/max")
    public Product getMaxPrice(){
        return productService.getMaxPrice(productService.getAll());
    }

    // http://localhost:8080/products/min - GET
    @GetMapping("/min")
    public Product getMinPrice(){
        return productService.getMinPrice(productService.getAll());
    }
    // http://localhost:8080/products/editProduct - GET
    @GetMapping("/editProduct")
    public String editProduct(Model model, @RequestParam("id") Long id) {
        model.addAttribute(productService.getById(id));
        return "updProducts";
    }

    @PutMapping(value = "/productsUpd")
    public Product updateProduct(@RequestParam("id") Long id, @RequestParam("prod") String name, @RequestParam("price") Double price) {
        return productService.save(new Product(id, name, price));
    }

    // http://localhost:8080/1/price - GET
    @GetMapping("/{id}/price")
    @ResponseBody
    public String apiPrice(@PathVariable Long id){
        Product byId = productService.getById(id);
        return String.valueOf(byId == null ? null : byId.getPrice());
    }

    // http://localhost:8080/new - GET

    @GetMapping("/new")
    public String getFormNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    // http://localhost:8080/new - POST
    @PostMapping("/new")
    public Product addNewProduct(Product product){
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        checkById(id);
        productDAO.deleteById(id); //Так правильно? или нужно было создать метод в продукт-сервисе?
    }

    // http://localhost:8080/filter?price_from=35.4&priceTo=3
    @GetMapping("/filter")
    public String filterByPrice(Model model,
                                @RequestParam(name = "min") double priceFrom,
                                @RequestParam(required = false) Double priceTo){
        List<Product> products =
                productService.getByPrice(priceFrom, priceTo == null ? Double.MAX_VALUE : priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/filter {title:"asd"}
    @PostMapping("/filter")
    @ResponseBody
    public String filterByTitle(@RequestParam String title){
        return productService.getAll().stream()
                .filter(product-> product.getTitle().contains(title))
                .map(product -> String.valueOf(product.getId()))
                .collect(Collectors.joining(","));
    }

    private void checkById(@PathVariable Long id) {
        if(!productDAO.existsById(id)){
            throw new EntityNotFoundException("Product", id, "Product not found");
        }
    }

    @ExceptionHandler //Ошибки будут обрабатываться в этом хендлере
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex){
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}