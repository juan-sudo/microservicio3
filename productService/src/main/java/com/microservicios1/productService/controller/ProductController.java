package com.microservicios1.productService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios1.productService.entity.Category;
import com.microservicios1.productService.entity.Product;
import com.microservicios1.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> productList = new ArrayList<>();
        if(categoryId == null){
            productList = productService.listAllProduct();
            if(productList.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else {
            productList = productService.findByCategory(Category.builder().id(categoryId).build());
            if(productList.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if(product==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product product1 = productService.updateProduct(product);
        if(product1==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product productDeleted = productService.deleteProduct(id);
        if(productDeleted==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDeleted);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name="quantity", required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> listErrors = result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errors = new HashMap<>();
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errors;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(listErrors)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = objectMapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
