package com.htn.view.product;

import com.htn.controller.ProductController;
import com.htn.data.item.Item;
import com.htn.view.View;
import com.htn.view.FieldBuilder;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductForm implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Product Form");
    private VBox content;
    @Getter
    private final Tab parent;
    private final Stage primaryStage;
    private Item itemToChange;


    public ProductForm(Tab parent, Stage primaryStage, Item itemToChange) {
        view = new ScrollPane();
        this.parent = parent;
        this.primaryStage = primaryStage;
        this.itemToChange = itemToChange;
        init();
        view.getStylesheets().add("customer.css");
        view.setContent(content);
        view.setFitToWidth(true);
//        view.setAlignment(Pos.CENTER);
//        view.getChildren().add(content);
    }

    public void init() {
        content = new VBox();
        content.setMaxHeight(view.getViewportBounds().getHeight());
        content.setMaxWidth(view.getViewportBounds().getWidth());
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        content.getStyleClass().add("form");
        content.setPadding(new Insets(32, 40, 32, 40));

        TextField nameField = new TextField();
        TextField stockField = new TextField();
        TextField sellingPriceField = new TextField();
        TextField purchasingPriceField = new TextField();
        TextField categoryField = new TextField();

        VBox productName = FieldBuilder.builder().field(nameField).label("Product name").required(true).build();
        VBox stock = FieldBuilder.builder().field(stockField).label("Stock").required(true).build();
        VBox sellingPrice = FieldBuilder.builder().field(sellingPriceField).label("Selling price").required(true)
                .build();
        VBox purchasingPrice = FieldBuilder.builder().field(purchasingPriceField).label("Purchasing price")
                .required(true).build();
        VBox category = FieldBuilder.builder().field(categoryField).label("Category").required(true).build();

        Button openButton = new Button("Add image");

        VBox productPhoto = new VBox();
        TextField fileField = new TextField("/sample_product.png");
//        VBox productPhoto = FieldBuilder.builder().field(fileField).label("Product Image").required(true).build();
        productPhoto.getChildren().addAll(openButton);

        openButton.setOnAction(e -> {
            FileChooser photoChooser = new FileChooser();
            File initialDirectory = new File(System.getProperty("user.home"));
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg",
                    "*.png");
            photoChooser.setTitle("Upload image");
            photoChooser.setInitialDirectory(initialDirectory);
            photoChooser.getExtensionFilters().add(imageFilter);
            File selectedFile = photoChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                String fileName = moveFile(selectedFile.getAbsoluteFile().toString());
                fileField.setText("/"+fileName);
                System.out.println(fileName);
            }
        });
        TextArea descriptionField = new TextArea();
        VBox description = FieldBuilder.builder().field(descriptionField).label("Description").required(true).build();
        Button save = new Button("Save");
        save.setPrefWidth(Double.MAX_VALUE);
        save.setOnAction(e -> {
            String id = Long.toString(System.currentTimeMillis()); /// USING NOW TIME
            String nameString = nameField.getText();
            int stockInt = Integer.parseInt(stockField.getText());
            double sellingPriceDouble = Double.parseDouble(sellingPriceField.getText());
            double purchasingPriceDouble = Double.parseDouble(purchasingPriceField.getText());
            String imageString = fileField.getText();
            String descriptionString = descriptionField.getText();
            String categoryString = categoryField.getText();
            Item newItem = new Item(id, nameString, descriptionString, imageString, sellingPriceDouble,
                    purchasingPriceDouble, stockInt, categoryString);
            this.save(newItem, itemToChange);
        });
        content.getChildren().addAll(new Label("New Product"), productName, stock, sellingPrice, purchasingPrice,
                category, description, openButton, productPhoto, save);

        if (itemToChange != null) {
            nameField.setText(itemToChange.getName());
            stockField.setText(String.valueOf(itemToChange.getStock()));
            sellingPriceField.setText(String.valueOf(itemToChange.getSellingPrice()));
            purchasingPriceField.setText(String.valueOf(itemToChange.getPurchasingPrice()));
            fileField.setText(itemToChange.getImage());
            descriptionField.setText(itemToChange.getDescription());
            categoryField.setText(itemToChange.getCategory());
        }
    }

    String moveFile(String sourceFilePath) {
        // Define the destination folder
        String destinationFolder = "src/main/resources/";

        // Get the file name from the source file path
        String fileName = new File(sourceFilePath).getName();

        // Create a Path object for the source file
        Path sourcePath = Paths.get(sourceFilePath);

        // Create a Path object for the destination folder
        Path destinationPath = Paths.get(destinationFolder + fileName);

        try {
            // Move the file to the destination folder
            Files.copy(sourcePath, destinationPath);
            System.out.println("File moved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void save(Item newItem, Item itemToChange) {
        if (itemToChange == null) {
            ProductController.addNewProduct(newItem);
        } else {
            ProductController.editProduct(itemToChange, newItem.getName(), newItem.getDescription(),
                    newItem.getSellingPrice(), newItem.getPurchasingPrice(), newItem.getCategory(), newItem.getImage(), newItem.getStock());
        }

        parent.setContent(ViewFactory.get("product", parent).getView());
    }

}
