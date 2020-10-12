package com.example.serviceproduct.repository;

import com.example.serviceproduct.entity.Category;
import com.example.serviceproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);
}
