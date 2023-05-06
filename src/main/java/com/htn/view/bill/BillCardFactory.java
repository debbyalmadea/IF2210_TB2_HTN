package com.htn.view.bill;

import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class BillCardFactory {

    public static Pane getCard(BillView parent, Object bill) {
        if (bill instanceof Bill) {
            return new BillCard(parent, (Bill) bill).getCard();
        }
        if (bill instanceof FixedBill) {
            return new FixedBillCard(parent, (FixedBill) bill).getCard();
        }

        return new Pane();
    }
}
