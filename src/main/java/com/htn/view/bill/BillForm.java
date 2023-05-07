package com.htn.view.bill;

import com.htn.api.view.View;
import com.htn.view.FieldBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class BillForm implements View {
    @Getter
    private final StackPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Bill Form");
    private HBox content;
    @Getter
    private final Tab parent;

    public BillForm(Tab parent) {
        view = new StackPane();
        this.parent = parent;
        init();
        view.getStylesheets().add("customer.css");
        view.getChildren().add(content);
    }

    public void init() {
        content = new HBox();
        content.setFillHeight(true);

        ScrollPane leftColumn = new ScrollPane();
        leftColumn.fitToHeightProperty().set(true);
        leftColumn.setPrefWidth(1200);

        VBox leftColumnContent = new VBox();

        leftColumnContent.setPadding(new Insets(32, 40, 32, 40));
        leftColumnContent.setSpacing(20);
        leftColumnContent.setPrefWidth(1068);
        leftColumn.setStyle("-fx-background-color: #F1F5F9;");
        leftColumn.setContent(leftColumnContent);

        TextField searchField = new TextField();
        searchField.getStyleClass().addAll("search");
        searchField.setPromptText("Cari produk...");
        Button searchButton = new Button("Cari");

        HBox searchBox = new HBox();
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(searchField, searchButton);

        leftColumnContent.getChildren().add(searchBox);

        VBox rightColumn = new VBox();
        rightColumn.setPadding(new Insets(30));
        rightColumn.setSpacing(10);
        rightColumn.setPrefWidth(500);
        rightColumn.setStyle("-fx-background-color: #fff;");

        Label Title = new Label("Bill");
        Title.setStyle("-fx-text-fill: #000;");
        Label Name = new Label("Nama");
        Name.setStyle("-fx-font-size: 18px;");

        TextField inputName = new TextField();
        inputName.setPromptText("Nama pelanggan");

        Button save = new Button("Simpan");
        Button checkout = new Button("Checkout");

        rightColumn.getChildren().addAll(Title, Name, inputName);
        getProductOnBill(rightColumn);

        content.getChildren().addAll(leftColumn, rightColumn);

    }

    public void addProduct(Label productQuantity) {
        String quantity = productQuantity.getText();
        int quantityInt = Integer.parseInt(quantity);
        quantityInt++;
        productQuantity.setText(String.valueOf(quantityInt));
    }

    public void decreaseProduct(Label productQuantity) {
        String quantity = productQuantity.getText();
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt > 1) {
            quantityInt--;
            productQuantity.setText(String.valueOf(quantityInt));
        }
    }

    private void getProductOnBill(VBox content) {
        for (int i = 0; i < 3; i++) {
            Label ProductName = new Label("Meal Set");
            ProductName.setStyle("-fx-font-size: 15px; -fx-text-fill: #000;");
            Label ProductPrice = new Label("Rp. 20.000,00");
            ProductPrice.setStyle("-fx-font-size: 15px;");
            Button decreaseProduct = new Button("-");
            decreaseProduct.setPrefWidth(10);
            decreaseProduct.setStyle("-fx-font-size: 15px; -fx-padding: 1px;");
            int productQuantity = 1;
            Label productQuantityLabel = new Label(String.valueOf(productQuantity));
            productQuantityLabel.setStyle("-fx-font-size: 15px;");
            Button increaseProduct = new Button("+");
            increaseProduct.setPrefWidth(10);
            increaseProduct.setStyle("-fx-font-size: 15px;-fx-padding: 1px;");

            decreaseProduct.setOnAction(e -> {
                this.decreaseProduct(productQuantityLabel);
            });

            increaseProduct.setOnAction(e -> {
                this.addProduct(productQuantityLabel);
            });

            HBox productBox = new HBox();
            productBox.setSpacing(10);
            productBox.getChildren().addAll(ProductName, ProductPrice, decreaseProduct, productQuantityLabel,
                    increaseProduct);
            content.getChildren().add(productBox);
        }
    }



}
