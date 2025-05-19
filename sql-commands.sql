CREATE DATABASE LibraryDB;
USE LibraryDB;
CREATE TABLE IF NOT EXISTS Books (
    book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    available TINYINT(1)
);
CREATE TABLE IF NOT EXISTS Borrowers(
    borrower_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS Loans(
    loan_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    borrower_id INT,
    loan_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (borrower_id) REFERENCES Borrowers(borrower_id)
);
-- populating tables with random data
-- table Books
INSERT INTO Books (title, author, available)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 1),
    ('1984', 'George Orwell', 1),
    ('To Kill a Mockingbird', 'Harper Lee', 1),
    ('Moby-Dick', 'Herman Melville', 1),
    ('Pride and Prejudice', 'Jane Austen', 1),
    ('The Catcher in the Rye', 'J.D. Salinger', 1),
    ('Brave New World', 'Aldous Huxley', 1),
    ('The Hobbit', 'J.R.R. Tolkien', 1),
    ('Jane Eyre', 'Charlotte Brontë', 1),
    ('Animal Farm', 'George Orwell', 1);
-- table Borrowers
INSERT INTO Borrowers (name, email)
VALUES ('Alice Johnson', 'alice@example.com'),
    ('Bob Smith', 'bob@example.com'),
    ('Charlie Davis', 'charlie@example.com'),
    ('Diana Moore', 'diana@example.com'),
    ('Ethan Brown', 'ethan@example.com'),
    ('Fiona Lewis', 'fiona@example.com'),
    ('George Hall', 'george@example.com'),
    ('Hannah Adams', 'hannah@example.com'),
    ('Ivan Scott', 'ivan@example.com'),
    ('Julia Clark', 'julia@example.com');
-- table Loans
INSERT INTO Loans (book_id, borrower_id, loan_date, return_date)
VALUES (1, 2, '2024-12-01', '2024-12-10'),
    (3, 1, '2024-12-05', '2024-12-15'),
    (5, 4, '2024-12-10', '2024-12-20'),
    (2, 3, '2024-12-11', NULL),
    (7, 6, '2024-12-13', NULL),
    (4, 5, '2024-12-15', '2024-12-25'),
    (9, 7, '2024-12-17', NULL),
    (6, 8, '2024-12-20', NULL),
    (8, 9, '2024-12-22', '2024-12-30'),
    (10, 10, '2024-12-25', NULL);
-- Marking borrowwd books as unavailable
UPDATE Books
SET available = 0
WHERE book_id IN (2, 7, 9, 6, 10);