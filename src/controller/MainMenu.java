package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Part;
import model.Product;
import model.inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class initializes the main menu  screen for the inventory management.*/
public class MainMenu implements Initializable {
    public TableView partTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn PartInventoryLevelColumn;
    public TableColumn PartCostColumn;
    public TableView productTable;
    public TableColumn ProductIDColumn;
    public TableColumn ProductNameColumn;
    public TableColumn ProductInventoryLevelColumn;
    public TableColumn ProductCostColumn;
    public Button exitButton;
    public TextField partSearch;
    public TextField productSearch;
    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public Button addProductButton;


    /** Method for populating the tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(inventory.getAllParts());
        productTable.setItems(inventory.getAllProducts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

/** Method for searching parts by ID or Name. */
    public void partSearch(ActionEvent actionEvent) {
        ObservableList<Part> searchPart = FXCollections.observableArrayList();
        String input = partSearch.getText();
        for(Part sp : inventory.getAllParts()){
            if(sp.getName().contains(input) || Integer.toString(sp.getId()).contains(input)) {
                searchPart.add(sp);
                partSearch.setText("");
            }
        }
        partTable.setItems(searchPart);

        if (searchPart.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Dialog");
            error.setContentText("No matching part");
            error.showAndWait();}
        return;
    }

    /** Method for loading the add part form when clicked. */
    public void AddPartCLick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Method for loading the modify part form when clicked.
     * RUNTIME ERROR: Had a hard time figuring out how to send all of the data back over to the modify Part/Product pages and this was the best solution I came up with.
     * The other iterations either sent some data over or none at all.
     * */
    public void ModifyPartCLick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();
            ModifyPart modifypart = loader.getController();
            Part sp = (Part) partTable.getSelectionModel().getSelectedItem();
            modifypart.receivePart(sp);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Must select part to modify");
            alert.showAndWait();
        }
    }

    /** Method for removing the selected part when clicked. */
    public void DeletePartClick(ActionEvent actionEvent) {
        Part SP = (Part)partTable.getSelectionModel().getSelectedItem();

        if (SP == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part must be selected");
            alert.showAndWait();
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE PART");
            alert.setContentText("Are you sure you want to delete selected part?");
            Optional<ButtonType> choice = alert.showAndWait();

            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                inventory.deletePart(SP);
            }
        }
    }

    /** Method for searching products by ID or Name. */
    public void productSearch(ActionEvent actionEvent) {
        ObservableList<Product> searchProduct = FXCollections.observableArrayList();
        String input = productSearch.getText();
        for(Product sp : inventory.getAllProducts()){
            if(sp.getName().contains(input) || Integer.toString(sp.getId()).contains(input)) {
                searchProduct.add(sp);
                productSearch.setText("");
            }
        }
        productTable.setItems(searchProduct);

        if (searchProduct.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Dialog");
            error.setContentText("No matching product");
            error.showAndWait();}
        return;
    }

    /** Method for loading the add product form when clicked. */
    public void AddProductClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Method for loading the modify product form when clicked. */
    public void ModifyProductClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();
            ModifyProduct modifyproduct = loader.getController();
            Product SP = (Product) productTable.getSelectionModel().getSelectedItem();
            modifyproduct.receiveProduct(SP);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Must select product to modify");
            alert.showAndWait();
        }
    }

    /** Method for removing the selected product when clicked. */
    public void DeleteProductClick(ActionEvent actionEvent) {
        Product SP = (Product)productTable.getSelectionModel().getSelectedItem();

        if (SP == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product");
            alert.showAndWait();
            return;
        }
        if (!SP.getAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must first remove associated parts");
            alert.showAndWait();
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE PRODUCT");
            alert.setContentText("Are you sure you want to delete selected product?");
            Optional<ButtonType> choice = alert.showAndWait();

            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                inventory.deleteProduct(SP);
            }
        }
    }
    /** Method for closing the program when finished. */
    public void exitButton(ActionEvent actionEvent) {
        Platform.exit();
    }
}