package com.htn.view.customer;

import com.htn.model.customer.Rewardable;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class DeactivatedCard {
    private Rewardable customer;
    private CustomerView parent;
    public Pane getCard() {
        return CardBuilder.builder()
                .title(customer.getName())
                .subtitle(customer.getPoint() + " point")
                .body(this.body())
                .styleSheets("deactivated-card.css")
                .footer(this.footer())
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label(customer.getId().toString()),
                new Label(customer.getPhoneNumber())
        );

        return bodyContainer;
    }
    private @NotNull Node footer() {
        Button activateButton = new Button("Activate");
        activateButton.getStyleClass().setAll("btn", "btn-green", "btn-small");
        activateButton.setPrefWidth(210);
        activateButton.setOnAction(e -> {
            parent.edit();
        });

        return activateButton;
    }
}
