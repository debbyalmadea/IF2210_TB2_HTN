package com.htn.view.customer;

import com.htn.data.customer.Customer;
import com.htn.data.customer.Rewardable;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class CustomerCardFactory {
    public static <T extends Customer> Pane getCard(@NotNull T customer, CustomerView parent) {
        if (customer instanceof Rewardable) {
            if (!((Rewardable) customer).isActivated())  {
                return new DeactivatedCard((Rewardable) customer, parent).getCard();
            }
            return new MemberCard((Rewardable) customer, parent).getCard();
        }

        return new CustomerCard(parent).getCard();
    }
}
