package com.htn.view.customer;

import com.htn.data.customer.Member;
import com.htn.data.customer.Rewardable;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
class MemberCard {
    private Rewardable customer;
    private CustomerView parent;
    public Pane getCard() {
        return CardBuilder.builder()
                .title(customer.getName())
                .subtitle(customer.getPoint() + " point")
                .body(this.body())
                .styleSheets(customer instanceof Member ? "card.css" : "vip-card.css")
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
        HBox buttonContainer = new HBox();
        Button activeButton = new Button("Deactivate");
        activeButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        activeButton.setPrefWidth(105);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        editButton.setPrefWidth(105);
        editButton.setOnAction(e -> {
            parent.edit();
        });

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(activeButton, editButton);

        return buttonContainer;
    }
}
