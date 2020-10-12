package com.example.servicecustomer.repository;

import com.example.servicecustomer.entity.Customer;
import com.example.servicecustomer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findByNumberID(String numberID);
    public Customer findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}
