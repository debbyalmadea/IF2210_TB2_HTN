package com.htn.view;

import javafx.scene.control.Tab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewFactory {
    public static @Nullable View get(@NotNull String request, Tab parent) {
        if (request.equalsIgnoreCase("customer")) {
            View view = new CustomerView(parent);
            // TODO! SEARCH BETTER WAY TO BIND THE TAB TEXT
            parent.textProperty().bindBidirectional(view.getTitle());
            return view;
        }

        return null;
    }
}
