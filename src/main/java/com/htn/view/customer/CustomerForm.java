package com.htn.view.customer;

import com.htn.controller.CustomerController;
import com.htn.data.customer.Customer;
import com.htn.view.ViewFactory;
import javafx.scene.control.*;

public class CustomerForm extends BaseCustomerForm<Customer> {
    public CustomerForm(Tab parent, Customer customer) {
        super(parent, customer);
    }
    public void save() {
        CustomerController.create(customer, nameField.getText(), phoneField.getText(), statusField.getValue().toString());
        ViewFactory.getViews().keySet().forEach(System.out::println);

        parent.setContent(ViewFactory.get("Customer", parent).getView());
    }
}
