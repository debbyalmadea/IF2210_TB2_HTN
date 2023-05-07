package com.htn.view.bill;

import com.htn.api.IBillCalculator;
import com.htn.controller.*;
import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.data.item.Item;
import com.htn.api.view.View;
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

    private Bill bill = null;

    private static IBillCalculator billCalculator=null;
    private boolean isPoint = false;

    public BillProductView(Tab parent){
        quantity = new HashMap<String, Integer>();
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        if (billCalculator == null) {
            billCalculator = new BillCalculator();
        }
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
        ProductController.bindProductData(this);
        CustomerController.bindVIPMemberData(this);
        CustomerController.bindMemberData(this);
    }

    public BillProductView(Tab parent, @NotNull Bill bill){
        quantity = bill.getCart();
        if (quantity == null) {
            quantity = new HashMap<String, Integer>();
        }
        this.bill = bill;
        if (billCalculator == null) {
            billCalculator = new BillCalculator();
        }
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
        ProductController.bindProductData(this);
        CustomerController.bindVIPMemberData(this);
        CustomerController.bindMemberData(this);
    }

    public static void setBillCalculator(IBillCalculator calculator) {
        billCalculator = calculator;
    }

    public void init(){
        content.getChildren().clear();
        billCalculator.setQuantity(quantity);
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
            this.pelanggan = bill.getName();
            comboBox.getItems().addAll(pelanggan);
            comboBox.setDisable(true);
        } else {
            comboBox.getItems().addAll(CustomerController.getAllMemberName());
        }
        checkValid();

        Customer customer = CustomerController.getAllByName(pelanggan);
        Button usePoint = null;
        if (customer != null) {
            if (customer instanceof Member && ((Member) customer).isActivated()) {
                usePoint = new Button("Use Point");
                usePoint.setOnAction(e-> {
                    isPoint = !isPoint;
                    init();
                });
            }
            billCalculator.calculate(customer, isPoint);
        }


        comboBox.setValue(pelanggan);
        comboBox.setOnAction(e->{
            pelanggan = comboBox.getValue();
            init();
        });

        Button simpanButton = new Button("Simpan");
        Button checkoutButton = new Button("Checkout");
        simpanButton.setOnAction(e-> {
            this.handleSimpan();
        });


        checkoutButton.setOnAction(e->{
            this.handleCheckOutButton();
        });

        // Add the components to the footer HBox
        footer.getChildren().addAll(simpanButton, checkoutButton);

        // Set the spacing and alignment for the footer HBox
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER_RIGHT);

        // Set the vertical grow priority of the footer to ALWAYS
        VBox.setVgrow(footer, Priority.ALWAYS);
        summary.getChildren().addAll(new Label("Breakdown: "), billCalculator.getSummary(), new Label("Total: " + billCalculator.getPrice()), footer);

        boxLeft.getChildren().addAll(
                searchBox, ProductBox, getListView("product")
        );
        searchButton.setOnAction(e -> {
            String textToSearch = search.getText();
            boxLeft.getChildren().set(2, getArrListView(ProductController.getSearchedProducts(textToSearch)));
        });


        // Add the components to the boxRight VBox
        boxRight.getChildren().addAll(titleLabel, comboBox, createBillListView(), summary);
        if (usePoint != null) {
            boxRight.getChildren().add(usePoint);
        }

        container.getChildren().addAll(boxLeft, boxRight);

        content.getChildren().addAll(container);

    }

    private void handleSimpan() {
        if (quantity.isEmpty()) {
            return;
        }

        Customer customer;
        if (pelanggan == null) {
            customer =  CustomerController.create();
        } else {
            customer = CustomerController.getAllByName(pelanggan);
            if (customer ==null) {
                return;
            }
        }
        ArrayList<String> itemIds = new ArrayList<>(quantity.keySet());
        BillController.addNewBill((new Bill(new Date().toLocaleString(), billCalculator.getPrice(), String.valueOf(customer.getId()), new Date(), itemIds, quantity)));
        this.pelanggan = null;
        this.quantity = new HashMap<String, Integer>();
        init();
    }
    private void handleCheckOutButton() {
        if (quantity.isEmpty()) {
            return;
        }
        Customer customer;
        if (pelanggan == null) {
            customer =  CustomerController.create();
        } else {
            customer = CustomerController.getAllByName(pelanggan);
            if (customer ==null) {
                System.out.println("NULL");
                return;
            }
            System.out.println("ID LG CHECKOUT" + customer.getId());
        }
        Double points = 0.0;
        if (customer instanceof Member) {
            Member memb = (Member) customer;
            System.out.println("CURRENT POINT: " + memb.getPoint());
            System.out.println("USED POINT: " + billCalculator.getUsedPoints());
            System.out.println("PRICE:" + billCalculator.getPrice());
            System.out.println("AWARDED: " + 0.01 * (billCalculator.getPrice() - billCalculator.getUsedPoints()));
            points = memb.getPoint() - billCalculator.getUsedPoints() + 0.01 * (billCalculator.getPrice());
            System.out.println("POITNS NOW:" + points);
        }
        ArrayList<String> itemIds = new ArrayList<>(quantity.keySet());
        ArrayList<Item> items = (ArrayList<Item>) ProductController.getListItem(itemIds);
        ArrayList<Item> persistentItems = new ArrayList<>();
        items.forEach(item->{
            Item dup = new Item(item);
            dup.setStock(quantity.get(item.getId()));
            persistentItems.add(dup);
        });
        BillController.addNewFixedBill(new FixedBill(String.valueOf(new Timestamp(new Date().getTime())), String.valueOf(customer.getId()), this.billCalculator.getPrice(), this.billCalculator.getProfit(),this.billCalculator.getBreakDown() + String.format(" Total: %.4f", billCalculator.getPrice()), new Date(), persistentItems));
        if (bill != null) {
            BillController.deleteBill(bill);
            bill = null;
        }
        items.forEach(item->{
            item.setStock(item.getStock() - quantity.get(item.getId()));
        });
        ProductController.sellProduct(items.get(0), items.get(0).getStock());
        CustomerController.setPurchased(customer, true);
        if (customer instanceof Member) {
            CustomerController.update((Member) customer, points);
        }
        this.pelanggan = null;
        this.quantity = new HashMap<String, Integer>();
        init();
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

    private @NotNull Pane getArrListView(@NotNull List<Item> products) {
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

    public void add(@NotNull Item product){
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
