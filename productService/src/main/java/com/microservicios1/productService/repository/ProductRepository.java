package com.microservicios1.productService.repository;

import com.microservicios1.productService.entity.Category;
import com.microservicios1.productService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);
    public Product findByName(String name);
}
