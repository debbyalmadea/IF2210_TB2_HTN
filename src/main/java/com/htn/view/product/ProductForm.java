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

public class ProductForm implements View {
    @Getter
    private final StackPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Product Form");
    private VBox content;
    @Getter
    private final Tab parent;
    private final Stage primaryStage;
    private Item itemToChange;

    public ProductForm(Tab parent, Stage primaryStage, Item itemToChange) {
        view = new StackPane();
        this.parent = parent;
        this.primaryStage = primaryStage;
        this.itemToChange = itemToChange;
        init();
        view.getStylesheets().add("customer.css");
        view.setAlignment(Pos.CENTER);
        view.getChildren().add(content);
    }

    public void init() {
        content = new VBox();
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
        TextField fileField = new TextField();
        VBox productPhoto = FieldBuilder.builder().field(fileField).label("Product Image").required(true).build();
        // productPhoto.getChildren().addAll(openButton, fileField);

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
                fileField.setText(selectedFile.getAbsolutePath());
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

    }

    public void save(Item newItem, Item itemToChange) {
        if (itemToChange == null) {
            ProductController.addNewProduct(newItem);
        } else {
            ProductController.editProduct(itemToChange, newItem.getName(), newItem.getDescription(),
                    newItem.getSellingPrice(), newItem.getPurchasingPrice(), newItem.getCategory(), newItem.getImage());
        }

        parent.setContent(ViewFactory.get("product", parent).getView());
    }

}
