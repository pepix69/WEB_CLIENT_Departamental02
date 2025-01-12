package com.upiiz.expenses.services;

import com.upiiz.expenses.entities.Product;
import com.upiiz.expenses.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllExpenses() {
        return productRepository.findAll();
    }

    public Optional<Product> getExpenseById(Long id) {
        return productRepository.findById(id);
    }

    public Product createExpense(Product product) {
        return productRepository.save(product);
    }

    public Product updateExpense(Product product) {
        return productRepository.save(product);
    }

    public void deleteExpense(Long id) {
        productRepository.deleteById(id);
    }
}