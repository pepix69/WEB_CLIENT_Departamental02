package com.example.examen_02.services;

import com.example.examen_02.entities.Products;
import com.example.examen_02.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    ProductsRepository productsRepository;

    public List<Products> obtenerTodos() {
        return productsRepository.findAll();
    }

    public Optional<Products> obtenerProductsPorId(Long id) {
        return productsRepository.findById(id);
    }

    public Products guardarProducts(Products products) {
        return productsRepository.save(products);
    }

    public void deleteProducts(Long id) {
        productsRepository.deleteById(id);
    }

    public Optional<Products> actualizarProducts(Long id, Products product) {
        return productsRepository.findById(id).map(productoExistente -> {
            productoExistente.setProduct_name(product.getProduct_name());
            productoExistente.setProduct_description(product.getProduct_description());
            productoExistente.setPrice(product.getPrice());
            productoExistente.setStock_quantity(product.getStock_quantity());
            productoExistente.setCreated_at(product.getCreated_at());

            return productsRepository.save(productoExistente);
        });
    }
}
