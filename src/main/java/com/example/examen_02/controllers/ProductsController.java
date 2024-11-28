package com.example.examen_02.controllers;

import com.example.examen_02.entities.Products;
import com.example.examen_02.services.ProductsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/examen2")
@Tag(name = "Products")
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @GetMapping("/obtener")
    public ResponseEntity<List<Products>> getClientes(){
        return ResponseEntity.ok(productsService.obtenerTodos());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Optional<Products>> getClienteById(@PathVariable Long id){
        return ResponseEntity.ok(productsService.obtenerProductsPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Products> guardar(@RequestBody Products cliente){
        return ResponseEntity.ok(productsService.guardarProducts(cliente));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Optional<Products>> updateCliente(@PathVariable Long id, @RequestBody Products cliente){
        return ResponseEntity.ok(productsService.actualizarProducts(id, cliente));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        productsService.deleteProducts(id);
        return ResponseEntity.noContent().build();
    }

}