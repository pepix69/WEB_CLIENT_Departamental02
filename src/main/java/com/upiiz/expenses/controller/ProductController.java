package com.upiiz.expenses.controller;

import com.upiiz.expenses.entities.Product;
import com.upiiz.expenses.responses.CustomResponse;
import com.upiiz.expenses.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://wcbdf-adl-examen-2.onrender.vercel.app"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1/products")
@Tag(
        name = "Products"
)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<List<Product>>> getExpenses() {
        List<Product> products = new ArrayList<>();
        Link allExpensesLink = linkTo(ProductController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            products = productService.getAllExpenses();
            if (!products.isEmpty()) {
                CustomResponse<List<Product>> response = new CustomResponse<>(1, "Productos encontrados", products, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Productos no encontrados", products, links));
            }
        } catch (Exception e) {
            CustomResponse<List<Product>> response = new CustomResponse<>(500, "Error interno de servidor", products, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<Product>> getExpenseById(@PathVariable Long id) {
        Optional<Product> expense = null;
        CustomResponse<Product> response = null;
        Link allExpensesLink = linkTo(ProductController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            expense = productService.getExpenseById(id);
            if (expense.isPresent()) {
                response = new CustomResponse<>(1, "Producto encontrado", expense.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Producto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<CustomResponse<Product>> crearExpense(@RequestBody Product product) {
        Link allExpensesLink = linkTo(ProductController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            Product product1 = productService.createExpense(product);
            if (product1 != null) {
                CustomResponse<Product> response = new CustomResponse<>(1, "Producto creado", product1, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Producto no encontrado", product1, links));
            }
        } catch (Exception e) {
            CustomResponse<Product> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<CustomResponse<Product>> updateExpense(@RequestBody Product product, @PathVariable Long id) {
        Link allExpensesLink = linkTo(ProductController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            product.setId(id);
            if (!productService.getExpenseById(id).equals("")) {
                Product productEntity = productService.updateExpense(product);
                CustomResponse<Product> response = new CustomResponse<>(1, "Producto actualizado", productEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<Product> response = new CustomResponse<>(0, "Producto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<Product> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<CustomResponse<Product>> deleteExpenseById(@PathVariable Long id) {
        Optional<Product> expenseEntity = null;
        CustomResponse<Product> response = null;
        Link allExpensesLink = linkTo(ProductController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);

        try {
            expenseEntity = productService.getExpenseById(id);
            if (expenseEntity.isPresent()) {
                productService.deleteExpense(id);
                response = new CustomResponse<>(1, "Producto eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Producto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}