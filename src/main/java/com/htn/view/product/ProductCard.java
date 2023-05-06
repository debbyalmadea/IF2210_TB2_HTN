package com.htn.view.product;

import com.htn.data.item.Item;
import com.htn.view.View;
import com.htn.view.bill.BillProductView;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import java.net.URL;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;



@AllArgsConstructor
public class ProductCard {
    private View parent;
    private Item item;
    public Pane getCard(){
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
