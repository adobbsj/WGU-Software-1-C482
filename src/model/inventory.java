package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** the inventory class for holding all parts and products. */
public class inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static ObservableList<Part> getAllParts() { return allParts; }

    /** Method for adding parts. */
    public static void addPart(Part addPart) {
        allParts.add(addPart);
    }

    /** Method for removing parts. */
    public static void deletePart(Part part){
        allParts.remove(part);
    }

    /** Method for modifying parts. */
    public static void updatePart(int id, Part part) {

        int index = -1;

        for (Part p : getAllParts()) {
            index++;

            if (p.getId() == id) {
                allParts.set(index, part);
                return;
            }
        }
    }


    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Method for adding products. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** Method for removing products. */
    public static void deleteProduct(Product product){
        allProducts.remove(product);
    }

    /** Method for modifying products. */
    public static void updateProduct(int id, Product product) {

        int index = -1;
        for (Product p : getAllProducts()) {
            index++;
            if (p.getId() == id) {
                allProducts.set(index, product);
                return;
            }
        }
    }

}