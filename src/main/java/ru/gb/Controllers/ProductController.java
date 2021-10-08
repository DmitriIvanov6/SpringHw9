package ru.gb.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Domains.Product;
import ru.gb.Errors.ProductErrors;
import ru.gb.Repositories.ProductRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = new ArrayList<>();
        repository.findAll().forEach(products::add);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable int id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable int id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product productNew = repository.save(product);
        return ResponseEntity.created(URI.create("/products/" + productNew.getId())).body(productNew);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrors> handleException(RuntimeException ex) {
        ProductErrors productErrors = new ProductErrors();
        productErrors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        productErrors.setMessage(ex.getMessage());
        return new ResponseEntity<>(productErrors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


