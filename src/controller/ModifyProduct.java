package controller;

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

/**This class displays the modify product form.*/
public class ModifyProduct implements Initializable {
    public TableView PartTableList;
    public TableView AssociatedTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventoryLevel;
    public TableColumn partCost;
    public TableColumn BPartID;
    public TableColumn BPartName;
    public TableColumn BInventoryLevel;
    public TableColumn BPartCost;
    public TextField productID;
    public TextField productName;
    public TextField productInventoryLevel;
    public TextField productCost;
    public TextField productMax;
    public TextField productMin;
    public TextField searchBar;

    private ObservableList<Part> associatedTable = FXCollections.observableArrayList();

    /** Method for populating the tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartTableList.setItems(inventory.getAllParts());
        AssociatedTable.setItems(associatedTable);

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        BPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        BPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        BInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        BPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Method for loading the original product data into the correct text-fields.  */
    public void receiveProduct(Product SP) {
        productID.setText(String.valueOf(SP.getId()));
        productName.setText(String.valueOf(SP.getName()));
        productInventoryLevel.setText(String.valueOf(SP.getStock()));
        productCost.setText(String.valueOf(SP.getPrice()));
        productMax.setText(String.valueOf(SP.getMax()));
        productMin.setText(String.valueOf(SP.getMin()));
        associatedTable.addAll(SP.getAssociatedParts());

    }

    /** Method for searching parts by ID or Name. */
    public void search(ActionEvent actionEvent) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        String input = searchBar.getText();

        for(Part sp : inventory.getAllParts()){
            if(sp.getName().contains(input) || Integer.toString(sp.getId()).contains(input)) {
                searchParts.add(sp);
            }
        }
        PartTableList.setItems(searchParts);
        searchBar.setText("");

        if (searchParts.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Dialog");
            error.setContentText("No matching part");
            error.showAndWait();}
        return;
    }

    /** Method for adding parts to the associated part table. */
    public void productAddButton(ActionEvent actionEvent) {
        Part Sp = (Part)PartTableList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Sp != null){
            associatedTable.add(Sp);
        }
        else {
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Select part to add");
            alert.showAndWait();
        }
    }

    /** Method for removing parts from the associated part table. */
    public void RemoveAssociatedPart(ActionEvent actionEvent) {
        Part sp = (Part)AssociatedTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(sp == null){
            alert.setContentText("Please select a part to remove");
            alert.showAndWait();
            return;
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("REMOVE PART");
            alert.setContentText("Are you sure you want to remove selected part?");
            Optional<ButtonType> choice = alert.showAndWait();

            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                associatedTable.remove(sp);
            }
        }
    }

    /** Method for saving all the product data and populating the table. */
    public void SaveProduct(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(productID.getText());
            String name = productName.getText();
            int stock = Integer.parseInt(productInventoryLevel.getText());
            double price = Double.parseDouble(productCost.getText());
            int min = Integer.parseInt(productMin.getText());
            int max = Integer.parseInt(productMax.getText());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");

            if (max < min) {
                alert.setContentText("Max must be greater than Min");
                alert.showAndWait();
                return;
            }
            if (stock > max) {
                alert.setContentText("Inventory must be less than Max");
                alert.showAndWait();
                return;
            }
            if (stock < min) {
                alert.setContentText("Inventory must be greater than Min");
                alert.showAndWait();
                return;
            }

            else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                newProduct.getAssociatedParts().clear();
                newProduct.getAssociatedParts().addAll(associatedTable);
                inventory.updateProduct(id, newProduct);
            }

            Parent root = FXMLLoader.load(Main.class.getResource("/View/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter characters for names and number values for all other fields");
            alert.showAndWait();
        }
    }

    /** Method for returning to the main menu and neglecting any changed data. */
    public void Cancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cancel?");
        alert.setContentText("Are you sure you want to cancel? Unsaved progress will be lost.");
        Optional<ButtonType> choice = alert.showAndWait();

        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Main.class.getResource("/View/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}