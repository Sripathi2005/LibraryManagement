# 📚 Library Management System

A console-based Java application to manage a library's book inventory with persistent file storage.

---

## 📋 Features

- **Add Book** — Add new books with auto-incremented ID, title, and author name
- **Issue Book** — Issue an available book to a student; records the student name and issue date
- **Return Book** — Return a previously issued book back to the library
- **Update Book** — Edit the title or author of an existing book
- **View All Books** — Display all books with their current status, issued-to name, and summary counts
- **File Handling** — All data is saved to `books.dat` and reloaded on next run (persistent storage)

---

## 🧠 Concepts Used

| Concept | Usage |
|--------|-------|
| OOP (Classes & Objects) | `Book` inner class with fields and serialization |
| File Handling | `ObjectInputStream` / `ObjectOutputStream` to save and load data |
| Serialization | `Book` implements `Serializable` for binary file storage |
| ArrayList | Dynamic list to store all books in memory |
| Stream API | Filtering available/issued books for summary |

---

## 📁 File Structure

```
2_LibraryManagement/
├── src/
│   └── LibraryManagement.java   ← Main source file
├── out/                         ← Compiled .class files (auto-generated)
├── books.dat                    ← Auto-created data file (after first run)
├── run.sh                       ← Run script for Linux/Mac
└── run.bat                      ← Run script for Windows
```

---

## ▶️ How to Run

### Linux / Mac
```bash
cd 2_LibraryManagement
bash run.sh
```

### Windows
```
cd 2_LibraryManagement
run.bat
```

### Manual (Any OS)
```bash
javac -d out src/LibraryManagement.java
java -cp out LibraryManagement
```

---

## 🖥️ Sample Output

```
╔══════════════════════════════════════╗
║   📚  LIBRARY MANAGEMENT SYSTEM  📚  ║
╚══════════════════════════════════════╝

┌──────────────────────────────┐
│          MAIN MENU           │
├──────────────────────────────┤
│  1. Add Book                 │
│  2. Issue Book               │
│  3. Return Book              │
│  4. Update Book              │
│  5. View All Books           │
│  6. Exit                     │
└──────────────────────────────┘

  Enter your choice: 2
  Enter Book ID to issue: 1
  Enter Student Name : Priya

  ✅ Book Issued Successfully!
  ┌────────────────────────────────┐
  │  Book    : Clean Code         │
  │  Issued To: Priya             │
  │  Date    : 21-04-2026         │
  └────────────────────────────────┘
```

---

## ⚙️ Requirements

- Java JDK 17 or above
- Terminal / Command Prompt

---

## 📌 Notes

- `books.dat` is created automatically in the project folder after the first run
- Data **persists between sessions** — books added today will still be there next time
- Book IDs are auto-incremented and never repeat
- If `books.dat` is deleted, the library starts fresh
