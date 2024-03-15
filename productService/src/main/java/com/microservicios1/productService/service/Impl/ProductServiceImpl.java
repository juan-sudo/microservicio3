package com.microservicios1.productService.service.Impl;

import com.microservicios1.productService.entity.Category;
import com.microservicios1.productService.entity.Product;
import com.microservicios1.productService.repository.ProductRepository;
import com.microservicios1.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        Product product1 = productRepository.findByName(product.getName());
        if (product1 != null) {
            return product1;
        }
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product product1 = getProduct(product.getId());
        if(product1 == null){
            return null;
        }
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setCategory(product.getCategory());
        product1.setStock(product.getStock());
        product1.setPrice(product.getPrice());
        return productRepository.save(product1);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product1 = getProduct(id);
        if(product1 == null){
            return null;
        }
        product1.setStatus("DELETED");
        return productRepository.save(product1);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product product1 = getProduct(id);
        if(product1 == null){
            return null;
        }
        Double stock = product1.getStock()+quantity;
        product1.setStock(stock);
        return productRepository.save(product1);
    }

    @Override
    public Product getProductByName(Product product) {
        return productRepository.findByName(product.getName());
    }
}
