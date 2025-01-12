package com.upiiz.expenses.repository;

import com.upiiz.expenses.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}