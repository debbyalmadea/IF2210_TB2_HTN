package com.htn.view.customer;

import com.htn.controller.CustomerController;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.view.ViewFactory;
import javafx.scene.control.Tab;

public class MemberForm extends BaseCustomerForm<Member> {
    public MemberForm(Tab parent, Member customer) {
        super(parent, customer);
    }
    @Override
    public void init() {
        super.init();
        this.nameField.setText(customer.getName());
        this.phoneField.setText(customer.getPhoneNumber());
        this.statusField.setValue(customer instanceof VIPMember ? "VIP" : "Member");
    }
    public void save() {
        CustomerController.update(customer, nameField.getText(), phoneField.getText(), statusField.getValue());
        ViewFactory.getViews().keySet().forEach(System.out::println);

        parent.setContent(ViewFactory.get("Customer", parent).getView());
    }
}
