package com.htn.view.customer;

import com.htn.data.customer.Customer;
import com.htn.view.FieldBuilder;
import com.htn.view.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public abstract class BaseCustomerForm<T extends Customer> implements View {
    @Getter
    protected final StackPane view;
    @Getter protected final StringProperty title = new SimpleStringProperty("Customer Form");
    protected VBox content;
    @Getter protected final Tab parent;
    protected TextField idField;
    protected TextField nameField;
    protected TextField phoneField;
    protected ComboBox<String> statusField;
    protected final T customer;
    public BaseCustomerForm(Tab parent, T customer) {
        this.parent = parent;
        this.customer = customer;
        view = new StackPane();
        init();
        view.getStylesheets().add("customer.css");
        view.setAlignment(Pos.CENTER);
        view.getChildren().add(content);
    }
    public void init() {
        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);
        content.getStyleClass().add("form");

        idField = new TextField(customer.getId().toString());
        idField.setDisable(true);
        VBox id = FieldBuilder.builder()
                .field(idField)
                .label("ID")
                .required(true).build();
        nameField = new TextField();
        VBox name = FieldBuilder.builder()
                .field(nameField)
                .label("Name")
                .required(true).build();
        phoneField = new TextField();
        VBox phone = FieldBuilder.builder()
                .field(phoneField)
                .label("Phone number")
                .required(true).build();
        statusField = new ComboBox<>(FXCollections.observableArrayList(
                "Member", "VIP"));
        statusField.setValue("Member");
        VBox status = FieldBuilder.builder()
                .field(statusField)
                .label("Status")
                .required(true).build();

        Button save = new Button("Save");
        save.setPrefWidth(Double.MAX_VALUE);
        save.setOnAction(e -> {
            this.save();
        });
        content.getChildren().addAll(new Label("New Member"), id, name, phone, status, save);
    }
    public abstract void save();
}
