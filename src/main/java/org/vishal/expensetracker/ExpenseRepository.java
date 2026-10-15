package org.vishal.expensetracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ExpenseRepository {

    private final List<Expense> expenseList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Expense> findAll() {
        return List.copyOf(expenseList);
    }

    public Optional<Expense> findById(Long id) {
        return expenseList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(idCounter.incrementAndGet());
            expenseList.add(expense);
            return expense;
        }
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getId().equals(expense.getId())) {
                expenseList.set(i, expense);
                return expense;
            }
        }
        expenseList.add(expense);
        return expense;
    }

    public void deleteById(Long id) {
        expenseList.removeIf(e -> e.getId().equals(id));
    }
}