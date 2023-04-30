package com.htn.view.customer;

import com.htn.view.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


public class CustomerView implements View {
    @Getter private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Customer");
    private VBox content;
    @Getter private final Tab parent;

    public CustomerView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        view.getStylesheets().add("customer.css");
        view.setContent(content);
        content.setStyle("-fx-background-color: #F1F5F9;");
    }
    public void init() {
        content = new VBox();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Label VIPLabel = new Label("VIP");
        Label memberLabel = new Label("Member");
        Label customerLabel = new Label("Customer");

        content.getChildren().addAll(
                VIPLabel, getListView("vip"),
                memberLabel, getListView("member"),
                customerLabel, getListView("customer"));
    }
    private @NotNull Pane getListView(String request) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        for (int i = 0; i < 10; i++) {
            listView.getChildren().add(CustomerCardFactory.getCard(request, this));
        }

        return listView;
    }
    public void update() {
           init();
    }
    public void create() {
        title.set("New Member");
        parent.setContent(new CustomerForm(parent).getView());
    }
    public void edit() {
        title.set("Edit Member");
        parent.setContent(new CustomerForm(parent).getView());
    }
    public void delete() {
        parent.setContent(new CustomerForm(parent).getView());
    }
}
