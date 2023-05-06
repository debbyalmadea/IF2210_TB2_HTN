package com.htn.view.bill;

import com.htn.data.bill.Bill;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class BillCardFactory {

    public static Pane getCard(@NotNull String request, BillView parent, Object bill) {
        if (request.equalsIgnoreCase("bill")) {
            return new BillCard(parent, (Bill) bill).getCard();
        }
        if (request.equalsIgnoreCase("fixedBill")) {
            return new FixedBillCard(parent).getCard();
        }

        return new Pane();
    }
}
