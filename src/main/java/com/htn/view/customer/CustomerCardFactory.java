package com.htn.view.customer;

import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class CustomerCardFactory {
    public static <T extends Customer> Pane getCard(@NotNull T customer, CustomerView parent) {
        if (customer instanceof Member) {
            return new MemberCard((Member) customer, parent).getCard();
        }

        return new CustomerCard(customer, parent).getCard();
    }
}
