package com.upiiz.expenses.repository;

import com.upiiz.expenses.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}