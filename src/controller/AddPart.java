package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.inHouse;
import model.inventory;
import model.outsourced;
import java.io.IOException;
import java.util.Optional;

/**This class displays the add part form.*/
public class AddPart {
    private static int ID = 1;
    public RadioButton inHouseID;
    public RadioButton outsourcedID;
    public Label MachineCompanyLabel;
    public TextField partID;
    public TextField partName;
    public TextField partInventoryAdded;
    public TextField partCost;
    public TextField partMax;
    public TextField machine_ID;
    public TextField partMin;

    /** Method for setting the machine ID label. */
    public void onInHouse(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Machine ID");
    }

    /** Method for setting the Company Name label. */
    public void onOutsourced(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Company Name");
    }

    /** Method for saving all the part data and populating the table. */
    public void savePart(ActionEvent actionEvent) {
        try {
            int id = ID++;
            String name = partName.getText();
            int stock = Integer.parseInt(partInventoryAdded.getText());
            double price = Double.parseDouble(partCost.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());

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
                if (inHouseID.isSelected() == true) {
                    int machineId = Integer.parseInt(machine_ID.getText());
                    inHouse newInHouse = new inHouse(id, name, price, stock, min, max, machineId);
                    newInHouse.setMachineId(machineId);
                    inventory.addPart(newInHouse);
                }

                if (outsourcedID.isSelected() == true) {
                    String companyName = machine_ID.getText();
                    inventory.addPart(new outsourced(id, name, price, stock, min, max, companyName));
                }
            }
            Parent root = FXMLLoader.load(Main.class.getResource("/View/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NumberFormatException numEx) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Please enter characters for names and number values for all other fields");
            error.showAndWait();
        }
    }

    /** Method for returning to the main menu and neglecting any changed data. */
    public void CancelSave(ActionEvent actionEvent) throws IOException {
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