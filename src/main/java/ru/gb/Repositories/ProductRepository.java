package ru.gb.Repositories;

import org.springframework.data.repository.CrudRepository;
import ru.gb.Domains.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
