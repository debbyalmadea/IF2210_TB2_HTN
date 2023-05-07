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
        if (CustomerController.create(customer, nameField.getText(), phoneField.getText(), statusField.getValue().toString())) {
            parent.setContent(ViewFactory.get("Customer", parent).getView());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input!");
            alert.setContentText(
                    "You should input every required field and phone number to be at least 9 characters");
            alert.showAndWait();
        }
    }
}
