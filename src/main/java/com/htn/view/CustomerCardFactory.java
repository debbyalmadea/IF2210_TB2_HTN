package com.htn.view;

import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class CustomerCardFactory {
    public static Pane getCard(@NotNull String request, CustomerView parent) {
        // TODO! request ganti jadi Customer, cek melalui instanceof
        if (request.equalsIgnoreCase("customer")) {
            return new CustomerCard(parent).getCard();
        }
        if (request.equalsIgnoreCase("member")) {
            return new MemberCard("member", parent).getCard();
        }
        if (request.equalsIgnoreCase("vip")) {
            return new MemberCard("vip", parent).getCard();
        }

        return new Pane();
    }
}
