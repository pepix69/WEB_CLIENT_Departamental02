package com.upiiz.expenses.controller;

import com.upiiz.expenses.entities.Expense;
import com.upiiz.expenses.responses.CustomResponse;
import com.upiiz.expenses.services.ExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://wcbdf-adl-examen-2.onrender.vercel.app"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1/expenses")
@Tag(
        name = "Expenses"
)
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<List<Expense>>> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        Link allExpensesLink = linkTo(ExpenseController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            expenses = expenseService.getAllExpenses();
            if (!expenses.isEmpty()) {
                CustomResponse<List<Expense>> response = new CustomResponse<>(1, "Gastos encontrados", expenses, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Gastos no encontrados", expenses, links));
            }
        } catch (Exception e) {
            CustomResponse<List<Expense>> response = new CustomResponse<>(500, "Error interno de servidor", expenses, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<Expense>> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = null;
        CustomResponse<Expense> response = null;
        Link allExpensesLink = linkTo(ExpenseController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            expense = expenseService.getExpenseById(id);
            if (expense.isPresent()) {
                response = new CustomResponse<>(1, "Gasto encontrado", expense.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Gasto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<CustomResponse<Expense>> crearExpense(@RequestBody Expense expense) {
        Link allExpensesLink = linkTo(ExpenseController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            Expense expense1 = expenseService.createExpense(expense);
            if (expense1 != null) {
                CustomResponse<Expense> response = new CustomResponse<>(1, "Gasto creado", expense1, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Gasto no encontrado", expense1, links));
            }
        } catch (Exception e) {
            CustomResponse<Expense> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<CustomResponse<Expense>> updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
        Link allExpensesLink = linkTo(ExpenseController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);
        try {
            expense.setExpenseId(id);
            if (!expenseService.getExpenseById(id).equals("")) {
                Expense expenseEntity = expenseService.updateExpense(expense);
                CustomResponse<Expense> response = new CustomResponse<>(1, "Gasto actualizado", expenseEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<Expense> response = new CustomResponse<>(0, "Gasto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<Expense> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<CustomResponse<Expense>> deleteExpenseById(@PathVariable Long id) {
        Optional<Expense> expenseEntity = null;
        CustomResponse<Expense> response = null;
        Link allExpensesLink = linkTo(ExpenseController.class).withSelfRel();
        List<Link> links = List.of(allExpensesLink);

        try {
            expenseEntity = expenseService.getExpenseById(id);
            if (expenseEntity.isPresent()) {
                expenseService.deleteExpense(id);
                response = new CustomResponse<>(1, "Gasto eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Gasto no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}