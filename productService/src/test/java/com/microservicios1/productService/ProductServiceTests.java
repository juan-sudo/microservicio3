package com.microservicios1.productService;

import com.microservicios1.productService.entity.Category;
import com.microservicios1.productService.entity.Product;
import com.microservicios1.productService.repository.ProductRepository;
import com.microservicios1.productService.service.Impl.ProductServiceImpl;
import com.microservicios1.productService.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup(){
        Product product = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
    }

    @Test
    void whenValidGetId_ThenReturnProduct(){

        //when
        Product product = productService.getProduct(1L);

        //then
        Assertions.assertThat(product.getName()).isEqualTo("computer");
    }

    @Test
    void whenValidUpdateStock_ThenReturnNewStock(){
        //when
        Product product = productService.updateStock(1L, Double.parseDouble("8"));

        //then
        Assertions.assertThat(product.getStock()).isEqualTo(13);
    }
}
