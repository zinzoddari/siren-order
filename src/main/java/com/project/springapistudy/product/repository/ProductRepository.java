package com.project.springapistudy.product.repository;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
