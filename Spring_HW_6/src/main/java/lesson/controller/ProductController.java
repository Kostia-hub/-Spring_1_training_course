package lesson.controller;

import lesson.dao.ProductDAO;
import lesson.domain.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lesson.domain.Product;
import lesson.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import static lesson.domain.Role.*;

@Controller
@RequestMapping("/")
public class ProductController {

    private final ProductService productService;
    private ProductDAO productDAO;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/ - GET
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("products", productService.getAll());
        return "list";
    }

    @RequestMapping(value = "/login")
    public String loginPage(){
        return "login";
    }

//    @RequestMapping(value = {"","/"})
//    public String index(){
//        return "index";
//    }

    // http://localhost:8080/1 - GET
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(Model model,@PathVariable("id") Long id){
        Product byId = productService.getById(id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "product";
    }

    // http://localhost:8080/max - GET
    @RequestMapping(value = "/max", method = RequestMethod.GET)
    public String getMaxPrice(Model model){
        model.addAttribute(productService.getMaxPrice(productService.getAll()));
        return "maxPriceProduct";
    }

    // http://localhost:8080/min - GET
    @RequestMapping(value = "/min", method = RequestMethod.GET)
    public String getMinPrice(Model model){
        model.addAttribute(productService.getMinPrice(productService.getAll()));
        return "minPriceProduct";
    }

    //@Secured({"Role.ADMIN"}) так не получилось
    @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
    public String editProduct(Model model, @RequestParam("id") Long id) {
        model.addAttribute(productService.getById(id));
        return "updProducts";
    }

    @RequestMapping(value = "/productsUpd", method = RequestMethod.POST)
    public String updateProduct(@RequestParam("id") Long id, @RequestParam("prod") String name, @RequestParam("price") Double price) {
        productService.save(new Product(id, name, price));
        return "redirect:/";
    }

    // http://localhost:8080/app/products/1/price - GET
    @RequestMapping(value = "/{id}/price", method = RequestMethod.GET)
    @ResponseBody
    public String apiPrice(@PathVariable Long id){
        Product byId = productService.getById(id);
        return String.valueOf(byId == null ? null : byId.getPrice());
    }

    // http://localhost:8080/app/products/new - GET

    @GetMapping("/new")
    public String getFormNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    // http://localhost:8080/app/products/new - POST
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewProduct(Product product){
        Product savedProduct = productService.save(product);
        System.out.println(savedProduct);
        return "redirect:/" + savedProduct.getId();
    }

    // http://localhost:8080/priceFilter
    @RequestMapping("/priceFilter")
    public String productsByPrice(Model model,
                                  @RequestParam(name = "price_from") double priceFrom,
                                  @RequestParam(name = "price_to") double priceTo){
        List<Product> products = productService.getByPrice(priceFrom, priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/app/filter?price_from=35.4&priceTo=3
    @GetMapping("/filter")
    public String filterByPrice(Model model,
                                @RequestParam(name = "min") double priceFrom,
                                @RequestParam(required = false) Double priceTo){
        List<Product> products =
                productService.getByPrice(priceFrom, priceTo == null ? Double.MAX_VALUE : priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/app/filter {title:"asd"}
    @PostMapping("/filter")
    @ResponseBody
    public String filterByTitle(@RequestParam String title){
        return productService.getAll().stream()
                .filter(product-> product.getTitle().contains(title))
                .map(product -> String.valueOf(product.getId()))
                .collect(Collectors.joining(","));
    }
}