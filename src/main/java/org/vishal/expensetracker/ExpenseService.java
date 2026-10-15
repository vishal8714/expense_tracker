package org.vishal.expensetracker;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> getAll() {
        return repository.findAll();
    }

    public Optional<Expense> getById(Long id) {
        return repository.findById(id);
    }

    public Expense create(Expense expense) {
        expense.setId(null);
        return repository.save(expense);
    }

    public Optional<Expense> update(Long id, Expense expense) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        expense.setId(id);
        return Optional.of(repository.save(expense));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}