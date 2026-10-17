package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Library Member.
 */
public class Member {

    private String id;
    private String name;
    private String email;
    private String phone;
    private LocalDate joinDate;
    private List<String> borrowedBookIds;

    public Member(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.joinDate = LocalDate.now();
        this.borrowedBookIds = new ArrayList<>();
    }

    // â”€â”€ Getters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public String       getId()             { return id; }
    public String       getName()           { return name; }
    public String       getEmail()          { return email; }
    public String       getPhone()          { return phone; }
    public LocalDate    getJoinDate()       { return joinDate; }
    public List<String> getBorrowedBookIds(){ return borrowedBookIds; }

    // â”€â”€ Borrow / Return helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void borrowBook(String bookId)  { borrowedBookIds.add(bookId); }
    public void returnBook(String bookId)  { borrowedBookIds.remove(bookId); }
    public int  totalBorrowed()            { return borrowedBookIds.size(); }

    @Override
    public String toString() {
        return String.format("%-6s | %-20s | %-25s | %-12s | Books: %d",
                id, name, email, phone, borrowedBookIds.size());
    }
}
