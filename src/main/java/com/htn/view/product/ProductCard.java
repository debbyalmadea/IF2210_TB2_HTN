package com.htn.view.product;

import com.htn.api.view.Card;
import com.htn.api.view.ProductViewExtension;
import com.htn.application.PluginManager;
import com.htn.data.item.Item;
import com.htn.api.view.View;
import com.htn.view.bill.BillProductView;
import lombok.AllArgsConstructor;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
public class ProductCard {
    private View parent;
    private Item item;
    public Pane getCard() {
        CardBuilder cardBuilder = CardBuilder.builder()
                .imageURI(item.getImage())
                .title(item.getName())
                .subtitle(Double.toString(item.getSellingPrice()))
                .body(this.information())
                .footer(this.footer())
                .build();
        List<Object> plugins = PluginManager.getPluginsWithClass(ProductViewExtension.class);
        plugins.forEach(plugin -> {
            ProductViewExtension productPlugin = (ProductViewExtension) plugin;
            productPlugin.updateCardDisplay(cardBuilder, item);
            System.out.println("updating card display...");
        });
        return cardBuilder.getCard();
    }

    private @NotNull VBox information() {
        VBox bodyContainer = new VBox();

        bodyContainer.getChildren().addAll(
                new Label("Purchasing price: " + Double.toString(item.getPurchasingPrice())),
                new Label("Stock: " + Integer.toString(item.getStock())),
                new Label("Category: " + item.getCategory()));
        return bodyContainer;
    }

    private @NotNull HBox footer() {
        HBox buttonContainer = new HBox();
        if (parent instanceof ProductView) {
            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
            deleteButton.setPrefWidth(105);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
            editButton.setPrefWidth(105);

            deleteButton.setOnAction(e -> ((ProductView)parent).delete(item));
            editButton.setOnAction(e -> ((ProductView)parent).edit(item));

            buttonContainer.setSpacing(10);
            buttonContainer.getChildren().addAll(deleteButton, editButton);
        } else if (parent instanceof BillProductView) {
            Button tambahButton = new Button("Tambah");
            tambahButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
            tambahButton.setPrefWidth(210);
            tambahButton.setOnAction(e -> ((BillProductView)parent).add(item));
            buttonContainer.setSpacing(10);
            buttonContainer.getChildren().addAll(tambahButton);
        }
        return buttonContainer;
    }


}
