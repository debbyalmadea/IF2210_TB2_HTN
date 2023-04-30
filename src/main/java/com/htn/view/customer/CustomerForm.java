package com.htn.view.customer;

import com.htn.view.View;
import com.htn.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CustomerForm implements View {
    @Getter
    private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Customer Form");
    private VBox content;
    @Getter private final Tab parent;
    public CustomerForm(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        content.getStylesheets().add("customer.css");
        view.setContent(content);
    }
    public void init() {
        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Button save = new Button("Save");
        save.setOnAction(e -> {
            this.save();
        });
        content.getChildren().add(save);
    }
    public void save() {
        parent.setContent(ViewFactory.get("customer", parent).getView());
    }
}
