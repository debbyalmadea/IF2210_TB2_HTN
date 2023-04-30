package com.htn.view;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class CustomerCard {
    private CustomerView parent;
    public Pane getCard() {
        Button upgradeButton = new Button("Upgrade");
        upgradeButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        upgradeButton.setMaxWidth(Double.MAX_VALUE);
        upgradeButton.setOnAction(e -> {
            parent.create();
        });

        return CardBuilder.builder()
                .title("tes")
                .footer(upgradeButton)
                .build().getCard();
    }
}
