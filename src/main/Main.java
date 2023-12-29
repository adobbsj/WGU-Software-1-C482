package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Part;
import model.inventory;

/** Main class for the inventory management system
 *  FUTURE ENHANCEMENT: If there were to be an update to this program I would have it add a cart feature to add Parts with the total price and add Products with the option to add the associated parts in the total price.
 * Javadoc can be found in the main folder.
 */
public class Main extends Application {

    /** Method for loading the main screen of the application. */
    @Override
    public void start(Stage stage) throws Exception {
        addParts();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root, 1122, 460));
        stage.show();
    }

    /** Method for adding placeholder data into the application. */
    private void addParts () {
        inventory.addPart(new Part(1,"hard drive",22.99,15, 1, 100));
        inventory.addPart(new Part(2,"graphics card",359.99,2, 1, 10));
        inventory.addPart(new Part(3,"cpu",110.59,6, 1, 10));
        inventory.addPart(new Part(4,"ram",35,52, 1, 100));
        inventory.addPart(new Part(5,"fan",15,500, 1, 10000));
    }

    /** Method for entering the program code section. */
    public static void main(String[] args) {
        launch(args);
    }
}
