package com.htn.view.customer;

import com.htn.api.view.View;
import com.htn.controller.CustomerController;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CustomerView implements View {
    @Getter private final ScrollPane view;
    @Getter private final StringProperty title = new SimpleStringProperty("Customer");
    private final VBox content;
    @Getter private final Tab parent;

    public CustomerView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        content = new VBox();
        init();
        view.getStylesheets().add("customer.css");
        view.setContent(content);
        content.setStyle("-fx-background-color: #F1F5F9;");

        CustomerController.bindCustomerData(this);
        CustomerController.bindMemberData(this);
        CustomerController.bindVIPMemberData(this);
    }
    public void init() {
        content.getChildren().clear();
        content.setPadding(new Insets(32, 40, 32, 40));
        content.setSpacing(20);

        Label VIPLabel = new Label("VIP");
        Label memberLabel = new Label("Member");
        Label customerLabel = new Label("Customer");

        content.getChildren().addAll(
                VIPLabel, getListView(CustomerController.getActiveVIPMembers()),
                memberLabel, getListView(CustomerController.getActiveMembers()),
                new Label("Deactivated VIP Member"), getListView(CustomerController.getDeactivateVIPMembers()),
                new Label("Deactivated Member"), getListView(CustomerController.getDeactivateMembers()),
                customerLabel, getListView(CustomerController.getCustomersOnly()));
    }
    private @NotNull <T extends Customer> Pane getListView(@NotNull List<T> customerList) {
        FlowPane listView = new FlowPane();
        if (customerList.size() == 0) {
            Label noItems = new Label("No items");
            noItems.getStylesheets().add("application.css");
            noItems.getStyleClass().add("body");
            listView.getChildren().add(noItems);
        } else {
            listView.setMaxWidth(Double.MAX_VALUE);
            listView.setHgap(20);
            listView.setVgap(20);
            for (T customer : customerList) {
                listView.getChildren().add(CustomerCardFactory.getCard(customer, this));
            }
        }

        return listView;
    }
    public void create(Customer customer) {
        title.set("New Member");
        parent.setContent(new CustomerForm(parent, customer).getView());
    }
    public void edit(Member member) {
        title.set("Edit Member");
        parent.setContent(new MemberForm(parent, member).getView());
    }
}