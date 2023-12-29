package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Part;
import model.inHouse;
import model.inventory;
import model.outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class displays the modify part form.*/
public class ModifyPart implements Initializable {

    public TextField partName;
    public TextField partInventoryAdded;
    public TextField partCost;
    public TextField partMax;
    public TextField machineID;
    public TextField partMin;
    public RadioButton inHouseRD;
    public RadioButton outsourcedRD;
    public Label MachineCompanyLabel;
    public TextField partID;

    /** Method for setting the machine ID label. */
    public void InHouse(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Machine ID");
    }

    /** Method for setting the Company Name label. */
    public void Outsourced(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Company Name");
    }

    /** Method for saving all the part data and populating the table. */
    public void SaveModify(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(partID.getText());
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
                if (inHouseRD.isSelected() == true) {
                    int machineId = Integer.parseInt(machineID.getText());
                    inventory.updatePart(id, new inHouse(id, name, price, stock, min, max, machineId));
                }

                if (outsourcedRD.isSelected() == true) {
                    String companyName = machineID.getText();
                    inventory.updatePart(id, new outsourced(id, name, price, stock, min, max, companyName));
                }
            }
            Parent root = FXMLLoader.load(Main.class.getResource("/View/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException numEx) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter characters for names and number values for all other fields");
            alert.showAndWait();
        }
    }

    /** Method for returning to the main menu and neglecting any changed data. */
    public void CancelModify(ActionEvent actionEvent) throws IOException {
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

    /** Method for loading the original part data into the correct text-fields.  */
    public void receivePart(Part part) {

        partID.setText(String.valueOf(part.getId()));
        partName.setText(part.getName());
        partInventoryAdded.setText(String.valueOf(part.getStock()));
        partCost.setText(String.valueOf(part.getPrice()));
        partMin.setText(String.valueOf(part.getMin()));
        partMax.setText(String.valueOf(part.getMax()));

        if (part instanceof inHouse){
            MachineCompanyLabel.setText("Machine ID");
            machineID.setText(String.valueOf(((inHouse)part).getMachineId()));
            inHouseRD.setSelected(true);
        }

        if (part instanceof outsourced){
            MachineCompanyLabel.setText("Company Name");
            machineID.setText(String.valueOf(((outsourced)part).getCompanyName()));
            outsourcedRD.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}