package com.project.springapistudy.product.repository;

import com.project.springapistudy.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
