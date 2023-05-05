package com.htn.view.product;

import com.htn.view.View;
import com.htn.view.FieldBuilder;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.lang.reflect.Field;

public class ProductForm implements View {
    @Getter
    private final StackPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Product Form");
    private VBox content;
    @Getter
    private final Tab parent;
    private final Stage primaryStage;

    public ProductForm(Tab parent, Stage primaryStage){
        view = new StackPane();
        this.parent = parent;
        this.primaryStage = primaryStage;
        init();
        view.getStylesheets().add("customer.css");
        view.setAlignment(Pos.CENTER);
        view.getChildren().add(content);
    }

    public void init(){
        content = new VBox();
        content.setSpacing(10);
        content.getStyleClass().add("form");
        content.setPadding(new Insets(32, 40, 32, 40));


        VBox productName = FieldBuilder.builder().field(new TextField()).label("Product name").required(true).build();
        VBox stock = FieldBuilder.builder().field(new TextField()).label("Stock").required(true).build();
        VBox sellingPrice = FieldBuilder.builder().field(new TextField()).label("Selling price").required(true).build();
        VBox purchasePrice = FieldBuilder.builder().field(new TextField()).label("Purchase price").required(true).build();
        VBox category = FieldBuilder.builder().field(new TextField()).label("Category").required(true).build();

        Button openButton = new Button("Add image");
        TextField fileField = new TextField();
        VBox productPhoto = FieldBuilder.builder().field(fileField).label("Product Image").required(true).build();
//        productPhoto.getChildren().addAll(openButton, fileField);

        openButton.setOnAction(e -> {
            FileChooser photoChooser = new FileChooser();
            File initialDirectory = new File(System.getProperty("user.home"));
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
            photoChooser.setTitle("Upload image");
            photoChooser.setInitialDirectory(initialDirectory);
            photoChooser.getExtensionFilters().add(imageFilter);
            File selectedFile = photoChooser.showOpenDialog(primaryStage);
            if(selectedFile != null){
                fileField.setText(selectedFile.getAbsolutePath());
            }
        });

        VBox description = FieldBuilder.builder().field(new TextArea()).label("Description").required(true).build();
        Button save = new Button("Save");
        save.setPrefWidth(Double.MAX_VALUE);
        save.setOnAction(e ->{
            this.save();
        });
        content.getChildren().addAll(new Label("New Product"), productName, stock, sellingPrice, purchasePrice, category, description, openButton, productPhoto, save);


    }
    public void save(){
        parent.setContent(ViewFactory.get("product", parent).getView());

    }



}
