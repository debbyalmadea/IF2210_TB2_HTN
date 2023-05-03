package com.htn.view.customer;

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
    private String customer;
    private CustomerView parent;
    public Pane getCard() {
        return CardBuilder.builder()
                .title("Kim Jisoo")
                .subtitle("20 poin")
                .body(this.body())
                .footer(this.footer())
                .styleSheets(customer.equals("member") ? "card.css" : "vip-card.css")
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("2345"),
                new Label("082145xxxxxx")
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
