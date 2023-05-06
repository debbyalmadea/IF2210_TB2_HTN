package com.htn.view.product;

import com.htn.data.item.Item;
import lombok.AllArgsConstructor;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class ProductCard {
    private Item item;
    private ProductView parent;

    public Pane getCard() {
        return CardBuilder.builder()
                .imageURI(item.getImage())
                .title(item.getName())
                .subtitle(Double.toString(item.getSellingPrice()))
                .body(this.information())
                .footer(this.footer())
                .build().getCard();
    }

    private @NotNull Node information() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("Purchasing price: " + Double.toString(item.getPurchasingPrice())),
                new Label("Stock: " + Integer.toString(item.getStock())),
                new Label("Category: " + item.getCategory()));
        return bodyContainer;
    }

    private @NotNull HBox footer() {
        HBox buttonContainer = new HBox();
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        deleteButton.setPrefWidth(105);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        editButton.setPrefWidth(105);

        deleteButton.setOnAction(e -> parent.delete(item));
        editButton.setOnAction(e -> parent.edit(item));

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(deleteButton, editButton);

        return buttonContainer;
    }

}
