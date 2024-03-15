package com.microservicios1.customerService.repository;
import com.microservicios1.customerService.entity.Customer;
import com.microservicios1.customerService.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);

}