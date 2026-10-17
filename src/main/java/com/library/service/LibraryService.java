package com.library.service;

import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Member;
import com.library.storage.DataStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Core business logic for the Library Management System.
 * Handles all CRUD operations and borrow/return workflows.
 */
public class LibraryService {

    private final DataStorage storage;

    private List<Book>         books;
    private List<Member>       members;
    private List<BorrowRecord> records;

    public LibraryService() {
        this.storage = new DataStorage();
        this.books   = storage.loadBooks();
        this.members = storage.loadMembers();
        this.records = storage.loadRecords();

        // Seed demo data if collections are empty
        if (books.isEmpty()) seedDemoData();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  BOOK OPERATIONS
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    public Book addBook(String title, String author, String genre, int year) {
        String id   = "B" + String.format("%04d", books.size() + 1);
        Book   book = new Book(id, title, author, genre, year);
        books.add(book);
        storage.saveBooks(books);
        return book;
    }

    public boolean removeBook(String bookId) {
        Optional<Book> book = findBookById(bookId);
        if (book.isEmpty()) return false;
        if (!book.get().isAvailable()) return false; // can't remove borrowed book
        books.remove(book.get());
        storage.saveBooks(books);
        return true;
    }

    public List<Book> getAllBooks()            { return new ArrayList<>(books); }

    public List<Book> getAvailableBooks() {
        return books.stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    public List<Book> searchBooks(String keyword) {
        String kw = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(kw)
                          || b.getAuthor().toLowerCase().contains(kw)
                          || b.getGenre().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookById(String id) {
        return books.stream().filter(b -> b.getId().equalsIgnoreCase(id)).findFirst();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  MEMBER OPERATIONS
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    public Member addMember(String name, String email, String phone) {
        String id     = "M" + String.format("%04d", members.size() + 1);
        Member member = new Member(id, name, email, phone);
        members.add(member);
        storage.saveMembers(members);
        return member;
    }

    public List<Member> getAllMembers()       { return new ArrayList<>(members); }

    public Optional<Member> findMemberById(String id) {
        return members.stream().filter(m -> m.getId().equalsIgnoreCase(id)).findFirst();
    }

    public List<Member> searchMembers(String keyword) {
        String kw = keyword.toLowerCase();
        return members.stream()
                .filter(m -> m.getName().toLowerCase().contains(kw)
                          || m.getEmail().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  BORROW / RETURN OPERATIONS
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    public BorrowRecord borrowBook(String bookId, String memberId) {
        Book   book   = findBookById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
        Member member = findMemberById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        if (!book.isAvailable())          throw new IllegalStateException("Book is already borrowed.");
        if (member.totalBorrowed() >= 3)  throw new IllegalStateException("Member has reached the 3-book borrowing limit.");

        String       recId  = "R" + String.format("%04d", records.size() + 1);
        BorrowRecord record = new BorrowRecord(recId, bookId, memberId);

        book.setAvailable(false);
        member.borrowBook(bookId);
        records.add(record);

        storage.saveBooks(books);
        storage.saveMembers(members);
        storage.saveRecords(records);

        return record;
    }

    public BorrowRecord returnBook(String bookId, String memberId) {
        BorrowRecord record = records.stream()
                .filter(r -> r.getBookId().equalsIgnoreCase(bookId)
                          && r.getMemberId().equalsIgnoreCase(memberId)
                          && !r.isReturned())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active borrow record found."));

        Book   book   = findBookById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));
        Member member = findMemberById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found."));

        record.setReturnDate(LocalDate.now());
        book.setAvailable(true);
        member.returnBook(bookId);

        storage.saveBooks(books);
        storage.saveMembers(members);
        storage.saveRecords(records);

        return record;
    }

    public List<BorrowRecord> getAllRecords()      { return new ArrayList<>(records); }

    public List<BorrowRecord> getOverdueRecords() {
        return records.stream()
                .filter(r -> !r.isReturned() && LocalDate.now().isAfter(r.getDueDate()))
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getMemberHistory(String memberId) {
        return records.stream()
                .filter(r -> r.getMemberId().equalsIgnoreCase(memberId))
                .collect(Collectors.toList());
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  STATS
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    public long totalBooks()     { return books.size(); }
    public long totalMembers()   { return members.size(); }
    public long totalBorrowed()  { return books.stream().filter(b -> !b.isAvailable()).count(); }
    public long totalOverdue()   { return getOverdueRecords().size(); }

    public double totalFinesCollected() {
        return records.stream()
                .filter(BorrowRecord::isReturned)
                .mapToDouble(BorrowRecord::calculateFine)
                .sum();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  DEMO SEED DATA
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void seedDemoData() {
        addBook("Clean Code",                  "Robert C. Martin", "Programming",  2008);
        addBook("The Pragmatic Programmer",    "David Thomas",     "Programming",  2019);
        addBook("Design Patterns",             "Gang of Four",     "Software Eng", 1994);
        addBook("Effective Java",              "Joshua Bloch",     "Programming",  2018);
        addBook("The Hobbit",                  "J.R.R. Tolkien",   "Fantasy",      1937);
        addBook("1984",                        "George Orwell",    "Dystopian",    1949);
        addBook("Atomic Habits",               "James Clear",      "Self-Help",    2018);
        addBook("Thinking, Fast and Slow",     "Daniel Kahneman",  "Psychology",   2011);
        addBook("The Alchemist",               "Paulo Coelho",     "Fiction",      1988);
        addBook("Introduction to Algorithms",  "Cormen et al.",    "CS",           2009);

        addMember("Alice Johnson",  "alice@example.com",  "9876543210");
        addMember("Bob Smith",      "bob@example.com",    "9123456789");
        addMember("Charlie Brown",  "charlie@example.com","9988776655");
    }
}
