package com.microservicios1.customerService.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios1.customerService.entity.Customer;
import com.microservicios1.customerService.entity.Region;
import com.microservicios1.customerService.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomersByRegionID(@RequestParam(name = "regionId" , required = false) Long regionId ) {
        List<Customer> customers =  new ArrayList<>();
        if (null ==  regionId) {
            customers = customerService.findCustomerAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            Region Region= new Region();
            Region.setId(regionId);
            customers = customerService.findCustomersByRegion(Region);
            if ( null == customers ) {
                return  ResponseEntity.notFound().build();
            }
        }
        return  ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        Customer customer = customerService.getCustomer(id);
        if (  null == customer) {
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Customer customerDB = customerService.createCustomer(customer);

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        Customer customerFound = customerService.getCustomer(id);
        if ( null == customerFound ) {
            return  ResponseEntity.notFound().build();
        }
        customer.setId(id);
        customerFound=customerService.updateCustomer(customer);
        return  ResponseEntity.ok(customerFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        Customer customer = customerService.getCustomer(id);
        if ( null == customer ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return  ResponseEntity.ok(customer);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}