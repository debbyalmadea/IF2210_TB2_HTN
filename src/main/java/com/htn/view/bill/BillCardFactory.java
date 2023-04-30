package com.htn.view.bill;

import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class BillCardFactory {

    public static Pane getCard(@NotNull String request, BillView parent) {
        // TODO! request ganti jadi Customer, cek melalui instanceof
        if (request.equalsIgnoreCase("bill")) {
            return new BillCard(parent).getCard();
        }
        if (request.equalsIgnoreCase("fixedBill")) {
            return new FixedBillCard(parent).getCard();
        }

        return new Pane();
    }
}
