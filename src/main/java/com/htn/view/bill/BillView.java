package com.htn.view.bill;
import com.htn.controller.BillController;
import com.htn.controller.CustomerController;
import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.view.View;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BillView implements View {
    @Getter
    private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Bill");
    private VBox content;
    @Getter private final Tab parent;


    public BillView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        init();
        content.setStyle("-fx-background-color: #F1F5F9;");
        view.getStylesheets().add("customer.css");
        view.setContent(content);
        BillController.bindBillData(this);
        BillController.bindFixedBillData(this);
        CustomerController.bindMemberData(this);
        CustomerController.bindVIPMemberData(this);
    }
    public void init() {
        content.getChildren().clear();
        content.setPrefHeight(Double.MAX_VALUE);
        // Create a TextField for the search query
        TextField searchField = new TextField();
        searchField.getStyleClass().addAll("search");
        searchField.setPromptText("Search");

        // Create a Button for performing the search
        Button searchButton = new Button("Search");
//        searchButton.setOnAction(event -> {
//            // Perform search logic here
//            // You can access the search query with: searchField.getText()
//            System.out.println(searchField.getText());
//                Dialog<Void> dialog = new Dialog<>();
//                dialog.setTitle("Mock Items");
//                dialog.setHeaderText("List of mock items in the bill");
//
//                VBox dialogContent = new VBox();
//                ListView<String> listView = new ListView<>();
//                listView.getItems().addAll("Item 1", "Item 2", "Item 3");
//                dialogContent.getChildren().add(listView);
//
//                dialog.getDialogPane().setContent(dialogContent);
//                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//                dialog.showAndWait();
//        });
//        // IF WANT TO SET VALIDATION ON FIELD
//        searchField.setTextFormatter(new TextFormatter<>(change -> {
//            if (!change.isContentChange()) {
//                return change;
//            }
//
//            String newText = change.getControlNewText();
//            if (newText.matches("\\d*")) {
//                return change;
//            } else {
//                return null;
//            }
//        }));

        // Create a HBox to hold the search elements
        HBox searchBox = new HBox();
        HBox.setHgrow(searchField, Priority.ALWAYS); // This line sets the search field to use all available space
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(searchField, searchButton);


        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Button printButton = new Button("Print All Fixed Bill");
//        printButton.setOnAction(e -> {
//            // Show the print dialog here
//            Image image = new Image(getClass().getResource("/asset.jpg").toExternalForm());
//            ObservableList<Item> items = FXCollections.observableArrayList(
//                    new Item("Item 1", "description 1", 10.0, image),
//                    new Item("Item 2", "description 2", 20.0, image),
//                    new Item("Item 1", "description 1", 10.0, image),
//                    new Item("Item 2", "description 2", 20.0, image),
//                    new Item("Item 1", "description 1", 10.0, image),
//                    new Item("Item 2", "description 2", 20.0, image),
//                    new Item("Item 3", "description 3", 30.0, image)
//            );
//            Dialog dialog = getDialog(items, "All Items Sold", "Details for bill");
//
//            dialog.showAndWait();
//        });

        Label BillLabel = new Label("Bill");
        Label FixedBillLabel = new Label("Fixed Bill");

        HBox fixedBillBox = new HBox();
        fixedBillBox.getChildren().addAll(FixedBillLabel, printButton);
        fixedBillBox.setAlignment(Pos.CENTER_LEFT);
        fixedBillBox.setSpacing(10);
        System.out.println(BillController.getAllBill());
        content.getChildren().addAll(
                searchBox, BillLabel, getListView("bill"),
                fixedBillBox, getListView("fixedbill"));
        content.getChildren().forEach(e-> {
            System.out.println(e);
        });
        System.out.println("MAYBE2");
    }
    private @NotNull Pane getListView(String request) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        System.out.println(request);
        if (request.equalsIgnoreCase("bill")) {
            List<Bill> bills = BillController.getAllBill();
            bills.forEach(bill-> {
                listView.getChildren().add(BillCardFactory.getCard(  this,bill));
            });
        } else {
            List<FixedBill> fixedBills = BillController.getAllFixedBill();
            fixedBills.forEach(e-> {
                listView.getChildren().add(BillCardFactory.getCard(  this,e));
            });
//            for (int i = 0; i < 10; i++) {
//                listView.getChildren().add(BillCardFactory.getCard(request,  this));
//            }
        }

        return listView;
    }
    public void update() {
        init();
    }

    public void delete(Object bill) {
        if (bill instanceof Bill) {
            BillController.deleteBill((Bill) bill);
        }
        init();
    }

    public void edit(Object bill) {
        if (bill instanceof Bill) {
            Tab tab = new Tab();
            tab.setText("Edit bill");
            tab.setContent(new BillProductView(tab, (Bill) bill).getView());
            parent.getTabPane().getTabs().add(tab);
            parent.getTabPane().getSelectionModel().select(tab);
        }
    }
    public void seeBill() {
//        Image image = new Image(getClass().getResource("/asset.jpg").toExternalForm());
//        ObservableList<Item> items = FXCollections.observableArrayList(
//                new Item("Item 1", "description 1", 10.0, image),
//                new Item("Item 2", "description 2", 20.0, image),
//                new Item("Item 3", "description 3", 30.0, image)
//        );
//        Dialog dialog = getDialog(items, "Bill", "Details for bill");

//        dialog.showAndWait();
    }
    public void printBill() {
//        Image image = new Image(getClass().getResource("/asset.jpg").toExternalForm());
//        ObservableList<Item> items = FXCollections.observableArrayList(
//                new Item("Item 1", "description 1", 10.0, image),
//                new Item("Item 2", "description 2", 20.0, image),
//                new Item("Item 3", "description 3", 30.0, image)
//        );
//        Dialog dialog = getDialog(items, "Printing Bill", "Printing Items.. Please wait 10s");
//
//        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setDisable(true); // disable the close button
//
//        // create the thread to wait for 10 seconds
//        Thread delayThread = new Thread(() -> {
//        try {
//            Thread.sleep(10000);
//            Platform.runLater(() -> dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setDisable(false)); // enable the close button
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        });
//        delayThread.start();
//
//        dialog.showAndWait();
    }
//    private Dialog getDialog(ObservableList items, String title, String header) {
////        Dialog<Void> dialog = new Dialog<>();
////        dialog.setTitle(title);
////        dialog.setHeaderText(header);
////
////        VBox dialogContent = new VBox();
////        ListView<Item> listView = new ListView<>();
////        listView.setItems(items);
////        listView.setCellFactory(new ItemViewCellFactory());
////        dialogContent.getChildren().add(listView);
////
////        dialog.getDialogPane().setContent(dialogContent);
////        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
////        return dialog;
//    }
}
