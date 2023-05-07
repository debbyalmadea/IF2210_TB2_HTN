package com.htn.view.customer;

import com.htn.data.customer.Customer;
import com.htn.view.CardBuilder;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerCard {
    private Customer customer;
    private CustomerView parent;
    public Pane getCard() {
        VBox buttonContainer = new VBox();
        Button upgradeButton = new Button("Upgrade");
        upgradeButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        upgradeButton.setMaxWidth(Double.MAX_VALUE);
        upgradeButton.setOnAction(e -> {
            parent.create(customer);
        });
        buttonContainer.getChildren().add(upgradeButton);

        return CardBuilder.builder()
                .title(customer.getId().toString())
                .footer(buttonContainer)
                .build().getCard();
    }
}
