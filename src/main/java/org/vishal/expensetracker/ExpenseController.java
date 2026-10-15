package org.vishal.expensetracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final List<Expense> expenseList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        for (Expense e : expenseList) {
            if (e.getId().equals(id)) {
                return ResponseEntity.ok(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense exp) {
        exp.setId(idCounter.incrementAndGet());
        expenseList.add(exp);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + exp.getId()))
                .body(exp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense exp) {
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getId().equals(id)) {
                exp.setId(id);
                expenseList.set(i, exp);
                return ResponseEntity.ok(exp);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        boolean removed = expenseList.removeIf(e -> e.getId().equals(id));
        return removed ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}