package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class LibraryController {

    @FXML
    private TextField authorField;

    @FXML
    private CheckBox availableCheckBox;

    @FXML
    private HBox buttonBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private Button readButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button sortButton;

    @FXML
    private TextField titleField;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Book, Number> idColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Boolean> availableColumn;

    @FXML
    private TableView<Book> tableView;

    @FXML
    public void initialize() {
        // Setting column values for table view
        idColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        availableColumn.setCellValueFactory(cellData -> cellData.getValue().availableProperty());

        // Loading initial data
        refreshTable();
    }

    // Reads the selected row (before update operation)
    @FXML
    void readBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        titleField.setText(selectedBook.getTitle());
        authorField.setText(selectedBook.getAuthor());
        availableCheckBox.setSelected(selectedBook.isAvailable());
    }

    // Inserting a new book
    @FXML
    void insertBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        boolean available = availableCheckBox.isSelected();

        if (title.isEmpty() || author.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Title and Author cannot be empty.");
            return;
        }

        String query = "INSERT INTO Books (title, author, available) VALUES (?, ?, ?)";
        try (Connection connection = Database.connect();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setBoolean(3, available);
            pstmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Book inserted successfully.");
            clearInputFields();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to insert the book.");
        }
    }

    // Updating the selected book
    @FXML
    void updateBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No book selected for update.");
            return;
        }

        String newTitle = titleField.getText();
        String newAuthor = authorField.getText();
        boolean newAvailability = availableCheckBox.isSelected();

        if (newTitle.isEmpty() || newAuthor.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Title and Author cannot be empty.");
            return;
        }

        String query = "UPDATE Books SET title = ?, author = ?, available = ? WHERE book_id = ?";

        try (Connection connection = Database.connect();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, newTitle);
            pstmt.setString(2, newAuthor);
            pstmt.setBoolean(3, newAvailability);
            pstmt.setInt(4, selectedBook.getBookId());

            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully.");
            clearInputFields();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update the book.");
        }

    }

    // Deletes the selected book
    @FXML
    void deleteBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No book selected for deletion.");
            return;
        }

        String query = "DELETE FROM Books WHERE book_id=?";

        try (Connection connection = Database.connect();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, selectedBook.getBookId());

            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully.");
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete the book.");
        }
    }

    // Refreshing the table view with the latest data
    @FXML
    void refreshTable(ActionEvent event) {
        refreshTable();
    }

    // Searching books by title
    @FXML
    void searchBooks(ActionEvent event) {
        String searchText = searchField.getText().toLowerCase();

        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a title to search.");
            return;
        }

        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        for (Book book : loadAllBooks()) {
            // Checks if the book title contains the search text, if so, adds
            // the book to the list of filtered books
            if (book.getTitle().toLowerCase().contains(searchText)) {
                filteredBooks.add(book);
            }
        }

        tableView.setItems(filteredBooks);

        // if no books match the search text, shows an alert
        if (filteredBooks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No books found matching the search term.");
        }
    }

    // Sorting all the books by title
    @FXML
    void sortBooks(ActionEvent event) {
        ObservableList<Book> books = tableView.getItems();

        // FXCollections.sort() with a case-insensitive Comparator to sort
        // the list by title
        FXCollections.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
    }

    // Loading all books from database
    private ObservableList<Book> loadAllBooks() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        String query = "SELECT * FROM Books";

        try (Connection connection = Database.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("available");

                bookList.add(new Book(bookId, title, author, available));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }

    // Refreshing the table view with the latest data
    private void refreshTable() {
        tableView.setItems(loadAllBooks());
    }

    // Displays alert messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clears input fields after insert or update
    private void clearInputFields() {
        titleField.clear();
        authorField.clear();
        availableCheckBox.setSelected(false);
        searchField.clear();
    }

}
