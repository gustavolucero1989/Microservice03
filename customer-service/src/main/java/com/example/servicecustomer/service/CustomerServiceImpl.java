package com.example.servicecustomer.service;

import com.example.servicecustomer.entity.Customer;
import com.example.servicecustomer.entity.Region;
import com.example.servicecustomer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB != null){
            return customerDB;
        }
        customer.setState("CREATED");
        customerDB = customerRepository.save(customer);
        return customerDB;
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        customerDB.setState("UPDATE");
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer customerDB = getCustomer(id);
        if(customerDB==null){
            return null;
        }
        customerDB.setState("DELETED");
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
