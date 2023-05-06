package com.htn.view.bill;

import com.htn.controller.*;
import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import com.htn.datastore.customer.CustomerDataStore;
import com.htn.view.View;
import com.htn.view.product.ProductCardFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.controlsfx.control.SearchableComboBox;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.*;

public class BillProductView implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Shopping");

    private VBox content;

    @Getter
    private final Tab parent;

    private Map<String, Integer> quantity;

    private String pelanggan = null;

    private Double price = null;
    private Double profit = null;

    private Bill bill = null;

    public BillProductView(Tab parent){
        quantity = new HashMap<String, Integer>();
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
        ProductController.bindProductData(this);
    }

    public BillProductView(Tab parent, Bill bill){
        this.bill = bill;
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
        boxLeft.setPrefWidth(1100);
        boxRight.setPrefWidth(500);
        boxRight.setPadding(new Insets(20,20,20,20));
        boxRight.setSpacing(20);

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
        boxLeft.setPadding(new Insets(32, 40, 32, 40));

        VBox summary = new VBox();
        summary.setSpacing(20);
        HBox footer = new HBox();
        footer.setSpacing(20);
        Label titleLabel = new Label("Bill");

        SearchableComboBox<String> comboBox = new SearchableComboBox<String>();
        if (bill != null) {
            this.quantity = bill.getCart();
            this.pelanggan = bill.getName();
            comboBox.getItems().addAll(pelanggan);
        } else {
            comboBox.getItems().addAll(CustomerController.getAllMemberName());
        }
        checkValid();
        comboBox.setValue(pelanggan);
        comboBox.setOnAction(e->{
            pelanggan = comboBox.getValue();
            System.out.println(comboBox.getValue());
        });

        Button simpanButton = new Button("Simpan");
        Button checkoutButton = new Button("Checkout");
        simpanButton.setOnAction(e-> {
            if (quantity.isEmpty()) {
                return;
            }
            Customer customer;
            if (pelanggan == null) {
                customer =  CustomerController.create();
            } else {
                customer = CustomerController.getCustomerById(pelanggan);
                if (customer == null) {
                    customer = CustomerController.getMemberByName(pelanggan);
                    if (customer == null) {
                        return;
                    }
                }
            }
            System.out.println(customer.getId());
            ArrayList<String> itemIds = new ArrayList<>(quantity.keySet());
            BillController.addNewBill((new Bill(new Date().toLocaleString(), price, String.valueOf(customer.getId()), new Date(),itemIds, quantity)));
            System.out.println(BillController.getAllBill().toString());
        });

        checkoutButton.setOnAction(e->{
            if (quantity.isEmpty()) {
                return;
            }
            String name;
            Customer customer;
            System.out.println("PELANGGAN");
            if (pelanggan == null) {
                System.out.println("NULL");
                customer =  CustomerController.create();
                name = String.valueOf(customer.getId());
            } else {
                customer = CustomerController.getMemberByName(pelanggan);
                System.out.println("SATU + " + customer);
                if (customer == null) {
                    customer = CustomerController.getCustomerById(pelanggan);
                    System.out.println("SATU + " + customer);
                    if (customer == null) {
                        return;
                    }
                    name = String.valueOf(customer.getId());
                } else {
                    name = CustomerController.getMemberByName(pelanggan).getName();
                }
            }
            ArrayList<String> itemIds = new ArrayList<>(quantity.keySet());
            ArrayList<Item> items = (ArrayList<Item>) ProductController.getListItem(itemIds);
            items.forEach(item->{
                ProductController.sellProduct(item, quantity.get(item.getId()));
            });
            BillController.addNewFixedBill(new FixedBill(String.valueOf(new Timestamp(new Date().getTime())), String.valueOf(customer.getId()), price, profit,"hsdifakldf", new Date(), items));
            CustomerController.setPurchased(customer, true);
            if (bill != null) {
                BillController.deleteBill(bill);
                bill = null;
            }
        });

        // Add the components to the footer HBox
        footer.getChildren().addAll(simpanButton, checkoutButton);

        // Set the spacing and alignment for the footer HBox
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER_RIGHT);

        // Set the vertical grow priority of the footer to ALWAYS
        VBox.setVgrow(footer, Priority.ALWAYS);
        summary.getChildren().addAll(getSummary(),footer);

        boxLeft.getChildren().addAll(
                searchBox, ProductBox, getListView("product")
        );
        // Add the components to the boxRight VBox
        boxRight.getChildren().addAll(titleLabel, comboBox, createBillListView(), summary);

        container.getChildren().addAll(boxLeft, boxRight);

        content.getChildren().addAll(container);

    }
    private void checkValid() {
        Iterator<Map.Entry<String, Integer>> iterator = quantity.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String itemId = entry.getKey();
            int qty = entry.getValue();
            Item item = ProductController.getProductWithId(itemId);

            if (item == null) {
                // Remove the entry from the map
                iterator.remove();
                continue;
            }
            if (item.getStock() < qty) {
                entry.setValue(item.getStock());
            }
        }

    }

    private VBox getSummary() {
        VBox summary = new VBox();
        summary.setSpacing(20);
        Double subtotal = 0.0;
        Double buyPrice = 0.0;

        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String itemId = entry.getKey();
            Item item = ProductController.getProductWithId(itemId);
            int qty = entry.getValue();
            subtotal += item.getSellingPrice() * qty;
            buyPrice += item.getPurchasingPrice() * qty;
        }
        // TODO GANTI JD LEGIT
        Double diskon = subtotal * 0.10;
        Double pajak = subtotal * 0.07;
        Double total = subtotal - diskon + pajak;
        price = total;
        profit = total - buyPrice;
        summary.getChildren().addAll(new Label("Cost Breakdown"), new Label("  Subtotal: " + String.format("%.2f", subtotal)), new Label("  Diskon: " + String.format("%.2f", diskon)), new Label("  Pajak: " + String.format("%.2f", pajak)), new Label("  Total: " + String.format("%.2f", total)));
        return summary;
    }
    private @NotNull Pane getListView(String request) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        List<Item> items = ProductController.getAllProductsNonZero();
        items.forEach(e-> {
            listView.getChildren().add(ProductCardFactory.getCard(e, this));
        });
        return listView;
    }

    public ListView<HBox> createBillListView() {
        ListView<HBox> listView = new ListView<>();
        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String itemId = entry.getKey();
            Item item = ProductController.getProductWithId(itemId);
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

}
