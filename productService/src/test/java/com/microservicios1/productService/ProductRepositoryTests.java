package com.microservicios1.productService;

import com.microservicios1.productService.entity.Category;
import com.microservicios1.productService.entity.Product;
import com.microservicios1.productService.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testEncontrarProductoPorCategoria(){
        //given
        Product product = Product.builder()
                .name("Cellphone")
                .category(Category.builder().id(1L).build())
                .description("iphone14")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1500.90"))
                .status("CREATED")
                .createAt(new Date())
                .build();
        productRepository.save(product);

        //when
        List<Product> productList = productRepository.findByCategory(product.getCategory());

        //then
        Assertions.assertThat(productList.size()).isEqualTo(3);
    }

}
