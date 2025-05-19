package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LibraryController {

    @FXML
    private TableColumn<?, ?> authorColumn;

    @FXML
    private TextField authorField;

    @FXML
    private CheckBox availableCheckBox;

    @FXML
    private TableColumn<?, ?> availableColumn;

    @FXML
    private HBox buttonBox;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, ?> idColumn;

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
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TextField titleField;

    @FXML
    private Button updateButton;

    @FXML
    void deleteBook(ActionEvent event) {

    }

    @FXML
    void insertBook(ActionEvent event) {

    }

    @FXML
    void readBook(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {

    }

    @FXML
    void searchBooks(ActionEvent event) {

    }

    @FXML
    void sortBooks(ActionEvent event) {

    }

    @FXML
    void updateBook(ActionEvent event) {

    }

}
