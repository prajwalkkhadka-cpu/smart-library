package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Tracks a single borrow transaction between a Member and a Book.
 */
public class BorrowRecord {

    private static final int    LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY     = 5.0; // â‚¹5 per overdue day

    private String    id;
    private String    bookId;
    private String    memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;   // null until returned

    public BorrowRecord(String id, String bookId, String memberId) {
        this.id         = id;
        this.bookId     = bookId;
        this.memberId   = memberId;
        this.borrowDate = LocalDate.now();
        this.dueDate    = LocalDate.now().plusDays(LOAN_PERIOD_DAYS);
    }

    // â”€â”€ Getters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public String    getId()         { return id; }
    public String    getBookId()     { return bookId; }
    public String    getMemberId()   { return memberId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate()    { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean   isReturned()    { return returnDate != null; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    /**
     * Calculates fine based on how many days past due date.
     * Returns 0 if returned/due on time.
     */
    public double calculateFine() {
        LocalDate checkDate  = (returnDate != null) ? returnDate : LocalDate.now();
        long      overdueDays = ChronoUnit.DAYS.between(dueDate, checkDate);
        return overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0.0;
    }

    @Override
    public String toString() {
        String status = isReturned()
                ? "Returned on " + returnDate
                : (LocalDate.now().isAfter(dueDate) ? "âš ï¸  OVERDUE" : "Active");
        return String.format("%-6s | Book: %-6s | Member: %-6s | Due: %s | Fine: â‚¹%.1f | %s",
                id, bookId, memberId, dueDate, calculateFine(), status);
    }
}
