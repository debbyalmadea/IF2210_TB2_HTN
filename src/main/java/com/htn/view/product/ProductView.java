package com.htn.view.product;

import com.htn.view.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class ProductView implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Product");

    private VBox content;

    @Getter
    private final Tab parent;

    public ProductView(Tab parent){
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
    }

    public void init(){
        // TextField to search products
        TextField search = new TextField();
        search.getStyleClass().add("search");
        search.setPromptText("Search product...");

        // Search button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            System.out.println(search.getText());
            Dialog<Void> dialog =  new Dialog<>();
            dialog.setTitle("Mock Products");
            dialog.setHeaderText("List of mock product that matched");

            VBox dialogContent = new VBox();
            ListView<String> listView = new ListView<>();
            listView.getItems().addAll("Product 1", "Product 2", "Product 3");
            dialogContent.getChildren().add(listView);

            dialog.showAndWait();

        });
        //Searching elements holder
        HBox searchBox = new HBox();
        HBox.setHgrow(search, Priority.ALWAYS); // This line sets the search field to use all available space
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(search, searchButton);


        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);


        Button AddProductButton = new Button("Add Product");
        Label AllProductLabel = new Label("All Products");
        HBox ProductBox = new HBox();
        ProductBox.getChildren().addAll(AllProductLabel, AddProductButton);
        ProductBox.setAlignment(Pos.CENTER_LEFT);
        ProductBox.setSpacing(70);
        content.getChildren().addAll(
                searchBox, ProductBox, getListView("product")
        );

    }
    private @NotNull Pane getListView(String request) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        for (int i = 0; i < 10; i++) {
            listView.getChildren().add(ProductCardFactory.getCard(request, this));
        }

        return listView;
    }

    public void edit(){
//        title.set("Edit product");
        // TODO : ProductForm
    }

    public void delete(){
//        title.set("Delete product");
        // TODO : productform
    }



}
