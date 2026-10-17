package com.library.ui;

import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Member;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI for Smart Library Management System.
 * Renders ASCII art menus and handles all user interaction.
 */
public class ConsoleUI {

    // â”€â”€ ANSI Color Codes â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private static final String RESET   = "\u001B[0m";
    private static final String RED     = "\u001B[31m";
    private static final String GREEN   = "\u001B[32m";
    private static final String YELLOW  = "\u001B[33m";
    private static final String BLUE    = "\u001B[34m";
    private static final String CYAN    = "\u001B[36m";
    private static final String BOLD    = "\u001B[1m";
    private static final String MAGENTA = "\u001B[35m";

    private final LibraryService service;
    private final Scanner        scanner;

    public ConsoleUI(LibraryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  ENTRY POINT
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    public void start() {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> bookMenu();
                case "2" -> memberMenu();
                case "3" -> borrowReturnMenu();
                case "4" -> printDashboard();
                case "5" -> printOverdueReport();
                case "0" -> { running = false; goodbye(); }
                default  -> warn("Invalid option. Please try again.");
            }
        }
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  MAIN MENU
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void printMainMenu() {
        System.out.println();
        box("ðŸ“š  MAIN MENU", CYAN);
        System.out.println(CYAN + "  [1]" + RESET + " ðŸ“– Book Management");
        System.out.println(CYAN + "  [2]" + RESET + " ðŸ‘¤ Member Management");
        System.out.println(CYAN + "  [3]" + RESET + " ðŸ”„ Borrow / Return");
        System.out.println(CYAN + "  [4]" + RESET + " ðŸ“Š Dashboard");
        System.out.println(CYAN + "  [5]" + RESET + " âš ï¸  Overdue Report");
        System.out.println(RED  + "  [0]" + RESET + " ðŸšª Exit");
        prompt();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  BOOK MENU
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void bookMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            box("ðŸ“–  BOOK MANAGEMENT", BLUE);
            System.out.println(BLUE + "  [1]" + RESET + " List all books");
            System.out.println(BLUE + "  [2]" + RESET + " Search books");
            System.out.println(BLUE + "  [3]" + RESET + " Add new book");
            System.out.println(BLUE + "  [4]" + RESET + " Remove book");
            System.out.println(YELLOW + "  [0]" + RESET + " â† Back");
            prompt();
            switch (scanner.nextLine().trim()) {
                case "1" -> listAllBooks();
                case "2" -> searchBooks();
                case "3" -> addBook();
                case "4" -> removeBook();
                case "0" -> back = true;
                default  -> warn("Invalid option.");
            }
        }
    }

    private void listAllBooks() {
        List<Book> all = service.getAllBooks();
        if (all.isEmpty()) { warn("No books in library."); return; }
        header("ALL BOOKS (" + all.size() + ")");
        bookHeader();
        all.forEach(b -> System.out.println("  " + b));
        divider();
    }

    private void searchBooks() {
        System.out.print(YELLOW + "  ðŸ” Search (title/author/genre): " + RESET);
        String kw      = scanner.nextLine().trim();
        List<Book> res = service.searchBooks(kw);
        if (res.isEmpty()) { warn("No books matched '" + kw + "'."); return; }
        header("SEARCH RESULTS (" + res.size() + ")");
        bookHeader();
        res.forEach(b -> System.out.println("  " + b));
        divider();
    }

    private void addBook() {
        header("ADD NEW BOOK");
        System.out.print("  Title  : "); String title  = scanner.nextLine().trim();
        System.out.print("  Author : "); String author = scanner.nextLine().trim();
        System.out.print("  Genre  : "); String genre  = scanner.nextLine().trim();
        System.out.print("  Year   : ");
        int year = 2024;
        try { year = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception ignored) {}
        Book book = service.addBook(title, author, genre, year);
        success("âœ… Book added: " + book.getId() + " - " + book.getTitle());
    }

    private void removeBook() {
        System.out.print(YELLOW + "  Book ID to remove: " + RESET);
        String id = scanner.nextLine().trim();
        if (service.removeBook(id)) success("âœ… Book " + id + " removed.");
        else warn("âŒ Cannot remove. Book not found or currently borrowed.");
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  MEMBER MENU
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void memberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            box("ðŸ‘¤  MEMBER MANAGEMENT", MAGENTA);
            System.out.println(MAGENTA + "  [1]" + RESET + " List all members");
            System.out.println(MAGENTA + "  [2]" + RESET + " Search members");
            System.out.println(MAGENTA + "  [3]" + RESET + " Register new member");
            System.out.println(MAGENTA + "  [4]" + RESET + " View member history");
            System.out.println(YELLOW  + "  [0]" + RESET + " â† Back");
            prompt();
            switch (scanner.nextLine().trim()) {
                case "1" -> listAllMembers();
                case "2" -> searchMembers();
                case "3" -> addMember();
                case "4" -> memberHistory();
                case "0" -> back = true;
                default  -> warn("Invalid option.");
            }
        }
    }

    private void listAllMembers() {
        List<Member> all = service.getAllMembers();
        if (all.isEmpty()) { warn("No members registered."); return; }
        header("ALL MEMBERS (" + all.size() + ")");
        memberHeader();
        all.forEach(m -> System.out.println("  " + m));
        divider();
    }

    private void searchMembers() {
        System.out.print(YELLOW + "  ðŸ” Search (name/email): " + RESET);
        String kw        = scanner.nextLine().trim();
        List<Member> res = service.searchMembers(kw);
        if (res.isEmpty()) { warn("No members matched."); return; }
        header("RESULTS (" + res.size() + ")");
        memberHeader();
        res.forEach(m -> System.out.println("  " + m));
        divider();
    }

    private void addMember() {
        header("REGISTER NEW MEMBER");
        System.out.print("  Name  : "); String name  = scanner.nextLine().trim();
        System.out.print("  Email : "); String email = scanner.nextLine().trim();
        System.out.print("  Phone : "); String phone = scanner.nextLine().trim();
        Member member = service.addMember(name, email, phone);
        success("âœ… Member registered: " + member.getId() + " - " + member.getName());
    }

    private void memberHistory() {
        System.out.print(YELLOW + "  Member ID: " + RESET);
        String id = scanner.nextLine().trim();
        List<BorrowRecord> history = service.getMemberHistory(id);
        if (history.isEmpty()) { warn("No borrow history found for " + id); return; }
        header("BORROW HISTORY â€” " + id);
        history.forEach(r -> System.out.println("  " + r));
        divider();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  BORROW / RETURN
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void borrowReturnMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            box("ðŸ”„  BORROW / RETURN", GREEN);
            System.out.println(GREEN  + "  [1]" + RESET + " Borrow a book");
            System.out.println(GREEN  + "  [2]" + RESET + " Return a book");
            System.out.println(GREEN  + "  [3]" + RESET + " View all borrow records");
            System.out.println(YELLOW + "  [0]" + RESET + " â† Back");
            prompt();
            switch (scanner.nextLine().trim()) {
                case "1" -> borrowBook();
                case "2" -> returnBook();
                case "3" -> listAllRecords();
                case "0" -> back = true;
                default  -> warn("Invalid option.");
            }
        }
    }

    private void borrowBook() {
        System.out.print(YELLOW + "  Book ID   : " + RESET); String bookId   = scanner.nextLine().trim();
        System.out.print(YELLOW + "  Member ID : " + RESET); String memberId = scanner.nextLine().trim();
        try {
            BorrowRecord record = service.borrowBook(bookId, memberId);
            success("âœ… Book borrowed! Due date: " + record.getDueDate());
            System.out.println(GREEN + "  Record: " + RESET + record);
        } catch (Exception e) {
            warn("âŒ " + e.getMessage());
        }
    }

    private void returnBook() {
        System.out.print(YELLOW + "  Book ID   : " + RESET); String bookId   = scanner.nextLine().trim();
        System.out.print(YELLOW + "  Member ID : " + RESET); String memberId = scanner.nextLine().trim();
        try {
            BorrowRecord record = service.returnBook(bookId, memberId);
            double fine = record.calculateFine();
            if (fine > 0) {
                warn("âš ï¸  Book returned. Overdue fine: â‚¹" + fine);
            } else {
                success("âœ… Book returned on time. No fine.");
            }
        } catch (Exception e) {
            warn("âŒ " + e.getMessage());
        }
    }

    private void listAllRecords() {
        List<BorrowRecord> all = service.getAllRecords();
        if (all.isEmpty()) { warn("No borrow records yet."); return; }
        header("ALL BORROW RECORDS (" + all.size() + ")");
        all.forEach(r -> System.out.println("  " + r));
        divider();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  DASHBOARD
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void printDashboard() {
        System.out.println();
        box("ðŸ“Š  LIBRARY DASHBOARD", CYAN);
        stat("ðŸ“š Total Books",        service.totalBooks());
        stat("ðŸ‘¤ Total Members",      service.totalMembers());
        stat("ðŸ“– Books Borrowed",     service.totalBorrowed());
        stat("âœ… Books Available",    service.totalBooks() - service.totalBorrowed());
        stat("âš ï¸  Overdue Books",     service.totalOverdue());
        System.out.printf("  %s%-22s%s â‚¹%.2f%n", YELLOW, "ðŸ’° Total Fines Collected", RESET, service.totalFinesCollected());
        divider();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  OVERDUE REPORT
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void printOverdueReport() {
        List<BorrowRecord> overdue = service.getOverdueRecords();
        if (overdue.isEmpty()) { success("ðŸŽ‰ No overdue books! All good."); return; }
        header("âš ï¸  OVERDUE REPORT (" + overdue.size() + " books)");
        overdue.forEach(r -> System.out.println(RED + "  " + r + RESET));
        divider();
    }

    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”
    //  UI HELPERS
    // â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”

    private void printBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("  â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("  â•‘                                                      â•‘");
        System.out.println("  â•‘      ðŸ“š  SMART LIBRARY MANAGEMENT SYSTEM  ðŸ“š         â•‘");
        System.out.println("  â•‘          Built with Java â€¢ by prajwalkkhadka-cpu     â•‘");
        System.out.println("  â•‘                                                      â•‘");
        System.out.println("  â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
        System.out.println(RESET);
    }

    private void goodbye() {
        System.out.println(GREEN + BOLD);
        System.out.println("  ðŸ‘‹ Thank you for using Smart Library System. Goodbye!");
        System.out.println(RESET);
    }

    private void box(String title, String color) {
        String line = "â”€".repeat(title.length() + 4);
        System.out.println(color + BOLD + "  â”Œ" + line + "â”");
        System.out.println("  â”‚  " + title + "  â”‚");
        System.out.println("  â””" + line + "â”˜" + RESET);
    }

    private void header(String title) {
        System.out.println();
        System.out.println(BOLD + BLUE + "  â”€â”€ " + title + " " + "â”€".repeat(Math.max(0, 50 - title.length())) + RESET);
    }

    private void divider() {
        System.out.println(BLUE + "  " + "â”€".repeat(54) + RESET);
    }

    private void bookHeader() {
        System.out.println(BOLD + "  ID     | Title                               | Author               | Genre           | Year | Status" + RESET);
        divider();
    }

    private void memberHeader() {
        System.out.println(BOLD + "  ID     | Name                 | Email                     | Phone        | Books" + RESET);
        divider();
    }

    private void stat(String label, long value) {
        System.out.printf("  %s%-22s%s %d%n", YELLOW, label, RESET, value);
    }

    private void prompt() {
        System.out.print(BOLD + GREEN + "\n  âž¤ Your choice: " + RESET);
    }

    private void success(String msg) {
        System.out.println(GREEN + "\n  " + msg + RESET);
    }

    private void warn(String msg) {
        System.out.println(YELLOW + "\n  " + msg + RESET);
    }
}
