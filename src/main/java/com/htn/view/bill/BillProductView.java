package com.htn.view.bill;

import com.htn.controller.ItemController;
import com.htn.data.item.Item;
import com.htn.view.View;
import com.htn.view.product.ProductCardFactory;
import com.htn.view.product.ProductForm;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.TextFields;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BillProductView implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Shopping");

    private VBox content;

    @Getter
    private final Tab parent;

    private final Map<String, Integer> quantity;
    private ItemController itemController = new ItemController();

    public BillProductView(Tab parent){
        quantity = new HashMap<String, Integer>();
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
    }

    public void init(){
        content.getChildren().clear();
        HBox container = new HBox();

        VBox boxLeft = new VBox();
        VBox boxRight = new VBox();
        boxLeft.setPrefWidth(1200);
        boxRight.setPrefWidth(500);
        boxRight.setStyle("-fx-background-color: #FFFFFF;");
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

        Label AllProductLabel = new Label("All Products");
        HBox ProductBox = new HBox();
        ProductBox.getChildren().addAll(AllProductLabel);
        ProductBox.setAlignment(Pos.CENTER_LEFT);
        ProductBox.setSpacing(70);
        boxLeft.setSpacing(20);
        boxLeft.getChildren().addAll(
                searchBox, ProductBox, getListView("product")
        );
        boxLeft.setPadding(new Insets(32, 40, 32, 40));


        VBox summary = new VBox();
        summary.setSpacing(20);
        HBox footer = new HBox();
        footer.setSpacing(20);
        Label titleLabel = new Label("Bill");

        SearchableComboBox<String> comboBox = new SearchableComboBox<String>();
        comboBox.getItems().addAll("Pelanggan 1", "Pelanggan 2", "Pelanggan 3");



        Label totalLabel = new Label("Total: $50.00"); // Set the initial price
        Button simpanButton = new Button("Simpan");
        Button checkoutButton = new Button("Checkout");

        // Add the components to the footer HBox
        footer.getChildren().addAll(totalLabel, simpanButton, checkoutButton);

        // Set the spacing and alignment for the footer HBox
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER_RIGHT);

        // Set the vertical grow priority of the footer to ALWAYS
        VBox.setVgrow(footer, Priority.ALWAYS);
        summary.getChildren().addAll(new Label("dskjlfasdf"),footer);

        // Add the components to the boxRight VBox
        boxRight.getChildren().addAll(titleLabel, comboBox, createBillListView(), summary);

        container.getChildren().addAll(boxLeft, boxRight);

        content.getChildren().addAll(container);

    }
    private @NotNull Pane getListView(String request) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        List<Item> items = itemController.getAll();
        items.forEach(e-> {
            listView.getChildren().add(ProductCardFactory.getCard(request, this, e));
        });
        return listView;
    }

    public ListView<HBox> createBillListView() {
        ListView<HBox> listView = new ListView<>();
        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String itemId = entry.getKey();
            Item item = itemController.getId(itemId);
            int qty = entry.getValue();


            Label titleLabel = new Label(item.getName());
            Label priceLabel = new Label(String.valueOf(item.getSellingPrice()));
            Button plusButton = new Button("+");
            Button minusButton = new Button("-");

            // plus button event listener
            plusButton.setOnAction(e -> {
                add(item);
            });

            // minus button event listener
            minusButton.setOnAction(e -> {
                // get the current quantity for this item
                int currentQty = quantity.get(itemId);

                // decrement the quantity by 1 if it's greater than 0
                if (currentQty > 0) {
                    int newQty = currentQty - 1;
                    if (newQty == 0) {
                        quantity.remove(itemId);
                    } else {
                        // update the quantity map
                        quantity.put(itemId, newQty);
                    }
                    init();
                }
            });


            Label quantityLabel = new Label(String.valueOf(qty));
            HBox hbox = new HBox(10, titleLabel, priceLabel, plusButton, quantityLabel, minusButton);
            hbox.setAlignment(Pos.CENTER_LEFT);
            listView.getItems().add(hbox);
        }
        return listView;
    }

    public ListView<String> getBillListView() {
        ListView<String> listView = new ListView<>();
        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String item = entry.getKey();
            int qty = entry.getValue();
            String text = item + " (" + qty + ")";
            listView.getItems().add(text);
        }
        return listView;
    }

    public void edit(){
        title.set("Edit product");
        parent.setContent(new ProductForm(parent, new Stage()).getView());
    }
    public void add(Item product){
        if (this.quantity.containsKey(product.getId())) {
            Integer quantity = this.quantity.get(product.getId());
            if (product.getStock() == quantity) {
                return;
            }
            this.quantity.put(product.getId(), this.quantity.get(product.getId()) + 1);
        } else {
            this.quantity.put(product.getId(), 1);
        };
        init();
    }


    public void delete(String id){
        title.set("Delete product");
    }



}
