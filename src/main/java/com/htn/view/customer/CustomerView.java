package com.htn.view.customer;

import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

        // TESTING ONLY
        List<Customer> testCustomer = new ArrayList<>();
        testCustomer.add(new Customer());
        testCustomer.add(new Customer());
        testCustomer.add(new Customer());

        List<Member> testMember = new ArrayList<>();
        testMember.add(new Member("Akane", "25372534123", 20));
        testMember.add(new Member("Kana", "25372534123", 20));
        testMember.add(new Member("Aqua", "25372534123", 20));

        List<VIPMember> testVIP = new ArrayList<>();
        testVIP.add(new VIPMember("Jisoo", "62746573464", 103));
        testVIP.add(new VIPMember("Lisa", "62746573464", 103));
        testVIP.add(new VIPMember("Jennie", "62746573464", 103));
        testVIP.add(new VIPMember("Rose", "62746573464", 103));
        testVIP.get(0).setActivated(false);
        testVIP.get(1).setActivated(false);

        Label VIPLabel = new Label("VIP");
        Label memberLabel = new Label("Member");
        Label customerLabel = new Label("Customer");

        content.getChildren().addAll(
                VIPLabel, getListView(testVIP.stream().filter(VIPMember::isActivated).collect(Collectors.toCollection(ArrayList::new))),
                memberLabel, getListView(testMember),
                new Label("Deactivated Member"), getListView(testVIP.stream().filter(member -> !member.isActivated()).collect(Collectors.toCollection(ArrayList::new))),
                customerLabel, getListView(testCustomer));
    }
    private @NotNull <T extends Customer> Pane getListView(@NotNull List<T> customerList) {
        FlowPane listView = new FlowPane();
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setHgap(20);
        listView.setVgap(20);
        for (T customer : customerList) {
            listView.getChildren().add(CustomerCardFactory.getCard(customer, this));
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
