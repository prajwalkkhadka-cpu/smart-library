# ðŸ“š Smart Library Management System

A full-featured **Java CLI application** for managing a library â€” books, members, borrowing, returns, and automatic fine calculation.

![Java](https://img.shields.io/badge/Java-11+-orange?logo=java)
![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue)

---

## âœ¨ Features

| Feature | Description |
|---|---|
| ðŸ“– **Book Management** | Add, remove, search books by title/author/genre |
| ðŸ‘¤ **Member Management** | Register members, search, view borrow history |
| ðŸ”„ **Borrow & Return** | Borrow books with 14-day loan period |
| ðŸ’° **Fine Calculation** | Auto-calculates â‚¹5/day fine for overdue books |
| ðŸ“Š **Dashboard** | Real-time stats â€” total books, borrowed, overdue, fines |
| âš ï¸  **Overdue Report** | Lists all currently overdue books |
| ðŸ’¾ **JSON Persistence** | Data saved to local `data/` folder using Gson |
| ðŸŽ¨ **ASCII Art UI** | Beautiful colored terminal interface |

---

## ðŸš€ Getting Started

### Prerequisites
- Java 11+
- Maven 3.6+

### Build & Run

```bash
# Clone the repo
git clone https://github.com/prajwalkkhadka-cpu/smart-library.git
cd smart-library

# Build the executable JAR
mvn clean package -q

# Run it!
java -jar target/smart-library-1.0.0-jar-with-dependencies.jar
```

---

## ðŸ–¥ï¸ Screenshots

```
  â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—
  â•‘                                                      â•‘
  â•‘      ðŸ“š  SMART LIBRARY MANAGEMENT SYSTEM  ðŸ“š         â•‘
  â•‘          Built with Java â€¢ by prajwalkkhadka-cpu     â•‘
  â•‘                                                      â•‘
  â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

  â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”
  â”‚  ðŸ“š  MAIN MENU  â”‚
  â””â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”˜
  [1] ðŸ“– Book Management
  [2] ðŸ‘¤ Member Management
  [3] ðŸ”„ Borrow / Return
  [4] ðŸ“Š Dashboard
  [5] âš ï¸  Overdue Report
  [0] ðŸšª Exit
```

---

## ðŸ“‚ Project Structure

```
smart-library/
â”œâ”€â”€ pom.xml
â”œâ”€â”€ README.md
â”œâ”€â”€ data/                          â† Auto-created JSON data files
â”‚   â”œâ”€â”€ books.json
â”‚   â”œâ”€â”€ members.json
â”‚   â””â”€â”€ borrow_records.json
â””â”€â”€ src/
    â””â”€â”€ main/
        â””â”€â”€ java/
            â””â”€â”€ com/library/
                â”œâ”€â”€ Main.java                  â† Entry point
                â”œâ”€â”€ model/
                â”‚   â”œâ”€â”€ Book.java
                â”‚   â”œâ”€â”€ Member.java
                â”‚   â””â”€â”€ BorrowRecord.java
                â”œâ”€â”€ service/
                â”‚   â””â”€â”€ LibraryService.java    â† Business logic
                â”œâ”€â”€ storage/
                â”‚   â””â”€â”€ DataStorage.java       â† JSON persistence
                â””â”€â”€ ui/
                    â””â”€â”€ ConsoleUI.java         â† Terminal UI
```

---

## ðŸ“‹ Business Rules

- Members can borrow a **maximum of 3 books** at a time
- Loan period is **14 days**
- Overdue fine is **â‚¹5 per day** after the due date
- Books currently borrowed **cannot be removed** from the system
- Data is auto-saved to JSON after every operation

---

## ðŸ› ï¸ Tech Stack

- **Java 11**
- **Maven** â€” build & dependency management
- **Gson 2.10.1** â€” JSON serialization
- **ANSI escape codes** â€” colored terminal output

---

## ðŸ‘¨â€ðŸ’» Author

**Prajwal Khadka** â€” [@prajwalkkhadka-cpu](https://github.com/prajwalkkhadka-cpu)

---

## ðŸ“„ License

MIT License â€” feel free to use and modify!
