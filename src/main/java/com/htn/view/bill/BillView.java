package com.htn.view.bill;

import com.htn.controller.BillController;
import com.htn.controller.CustomerController;
import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import com.htn.datastore.BillDataStore;
import com.htn.view.View;

import javafx.application.Platform;
import javafx.beans.Observable;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BillView implements View {
    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Bill");
    private VBox content;
    @Getter
    private final Tab parent;

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

        HBox searchBox = new HBox();
        HBox.setHgrow(searchField, Priority.ALWAYS); // This line sets the search field to use all available space
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(searchField, searchButton);

        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Button printButton = new Button("Print All Fixed Bill");
        printButton.setOnAction(e -> {
            String x = "";
            List<FixedBill> fixedBills = BillController.getAllFixedBill();
            for (FixedBill f : fixedBills) {
                x += ("idBill : " + f.getId() + "\n");
                for (Item i : f.getItems()) {
                    x += ("id item: " + i.getId() + "\n");
                    x += ("item name: " + i.getName() + "\n");
                    x += ("description: " + i.getDescription() + "\n");
                    x += ("selling price: " + i.getSellingPrice() + "\n");
                    x += ("purchasing price: " + i.getPurchasingPrice() + "\n");
                    x += ("category: " + i.getCategory() + "\n");
                    x += ("stock : " + i.getStock() + "\n");
                    x += ("image: " + i.getImage() + "\n");
                    x += "\n";
                }
                x += "\n";
            }
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("outputAll.pdf"));
                document.open();
                document.add(new com.itextpdf.text.Paragraph(x));
                document.close();
            } catch (DocumentException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

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
        content.getChildren().forEach(e -> {
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
            bills.forEach(bill -> {
                listView.getChildren().add(BillCardFactory.getCard(this, bill));
            });
        } else {
            List<FixedBill> fixedBills = BillController.getAllFixedBill();
            fixedBills.forEach(e -> {
                listView.getChildren().add(BillCardFactory.getCard(this, e));
            });

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

    public void seeBill(String id) {
        //
        ArrayList<Item> items = BillController.getFixedBillWithId(id).getItems();
        Dialog dialog = getDialog(items, "Bill", "Details for bill");

        dialog.showAndWait();
    }

    public void printBill(String id) throws IOException {
        ArrayList<Item> items = BillController.getFixedBillWithId(id).getItems();
        String x = "";
        for (Item i : items) {
            x += "id: " + i.getId() + "\n";
            x += "name: " + i.getName() + "\n";
            x += "category: " + i.getCategory() + "\n";
            x += "description: " + i.getDescription() + "\n";
            x += "purchasing price: " + i.getPurchasingPrice() + "\n";
            x += "selling price: " + i.getSellingPrice() + "\n";
            x += "stock: " + i.getStock() + "\n";
            x += "\n";
        }
        try {
            Thread.sleep(10000);
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
                document.open();
                document.add(new com.itextpdf.text.Paragraph(x));
                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Dialog getDialog(ArrayList<Item> items, String title, String header) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        VBox dialogContent = new VBox();
        ListView<Item> listView = new ListView<>();
        ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
        listView.setItems(observableItems);
        listView.setCellFactory(new ItemViewCellFactory());
        dialogContent.getChildren().add(listView);

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        return dialog;
    }
}
