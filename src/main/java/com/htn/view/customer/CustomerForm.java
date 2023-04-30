package com.htn.view.customer;

import com.htn.view.View;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CustomerForm implements View {
    @Getter
    private final StackPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Customer Form");
    private VBox content;
    @Getter private final Tab parent;
    public CustomerForm(Tab parent) {
        view = new StackPane();
        this.parent = parent;
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

        TextField idField = new TextField("217836");
        idField.setDisable(true);
        VBox id = FieldBuilder.builder()
                .field(idField)
                .label("ID")
                .required(true).build();
        VBox name = FieldBuilder.builder()
                .field(new TextField())
                .label("Name")
                .required(true).build();
        VBox phone = FieldBuilder.builder()
                .field(new TextField())
                .label("Phone number")
                .required(true).build();
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Member", "VIP"));
        comboBox.setValue("Member");
        VBox status = FieldBuilder.builder()
                .field(comboBox)
                .label("Status")
                .required(true).build();

        Button save = new Button("Save");
        save.setPrefWidth(Double.MAX_VALUE);
        save.setOnAction(e -> {
            this.save();
        });
        content.getChildren().addAll(new Label("New Member"), id, name, phone, status, save);
    }
    public void save() {
        parent.setContent(ViewFactory.get("customer", parent).getView());
    }
}
