package com.microservicios1.shoppingService.Client;

import com.microservicios1.shoppingService.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name = "customerService", fallback = CustomerHystrixFallbackImplements.class)
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
}