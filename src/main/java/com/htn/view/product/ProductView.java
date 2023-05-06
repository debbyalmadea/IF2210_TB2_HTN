package com.htn.view.product;

import com.htn.controller.ProductController;
import com.htn.data.item.Item;
import com.htn.view.View;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductView implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Product");

    private VBox content;

    @Getter
    private final Tab parent;

    public ProductView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
        ProductController.bindProductData(this);
    }

    public void init() {
        // TextField to search products
        TextField search = new TextField();
        search.getStyleClass().add("search");
        search.setPromptText("Search product...");

        // Search button
        Button searchButton = new Button("Search");

        // Searching elements holder
        HBox searchBox = new HBox();
        HBox.setHgrow(search, Priority.ALWAYS); // This line sets the search field to use all available space
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(search, searchButton);

        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Button AddProductButton = new Button("Add Product");
        AddProductButton.setOnAction(e -> {
            this.add();
        });
        Label AllProductLabel = new Label("All Products");
        HBox ProductBox = new HBox();
        ProductBox.getChildren().addAll(AllProductLabel, AddProductButton);
        ProductBox.setAlignment(Pos.CENTER_LEFT);
        ProductBox.setSpacing(70);
        content.getChildren().addAll(
                searchBox, ProductBox, getListView(ProductController.getAllProducts()));
        searchButton.setOnAction(e -> {
            String textToSearch = search.getText();
            AllProductLabel.setText("Hasil pencarian: " + textToSearch);
            content.getChildren().set(2, getListView(ProductController.getSearchedProducts(textToSearch)));

        });
    }

    private @NotNull Pane getListView(@NotNull List<Item> products) {
        FlowPane listView = new FlowPane();
        if (products.size() == 0) {
            Label noItems = new Label("No products");
            noItems.getStylesheets().add("application.css");
            noItems.getStyleClass().add("body");
            listView.getChildren().add(noItems);
        } else {
            listView.setMaxWidth(Double.MAX_VALUE);
            listView.setHgap(20);
            listView.setVgap(20);
            for (Item i : products) {
                listView.getChildren().add(ProductCardFactory.getCard(i, this));
            }
        }

        return listView;
    }

    public void edit(Item item) {
        title.set("Edit product");
        parent.setContent(new ProductForm(parent, new Stage(), item).getView());
    }

    public void add() {
        title.set("Add product");
        parent.setContent(new ProductForm(parent, new Stage(), null).getView());
    }

    public void delete(Item item) {
        ProductController.deleteProduct(item);
        parent.setContent(ViewFactory.get("product", parent).getView());
    }

}
