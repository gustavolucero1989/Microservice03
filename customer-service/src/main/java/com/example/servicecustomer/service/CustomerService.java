package com.example.servicecustomer.service;

import com.example.servicecustomer.entity.Customer;
import com.example.servicecustomer.entity.Region;

import java.util.List;

public interface CustomerService {
    public List<Customer> findCustomerAll();
    public Customer createCustomer(Customer customer);
    public List<Customer> findCustomerByRegion(Region region);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Long id);
    public Customer getCustomer(Long id);

}
