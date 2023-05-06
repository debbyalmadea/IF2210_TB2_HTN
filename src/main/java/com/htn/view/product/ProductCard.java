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
    private Item product;
    public Pane getCard(){
        return CardBuilder.builder()
                .imageURI(product.getImage())
                .title(product.getName())
                .subtitle("Rp. " + String.valueOf(product.getSellingPrice()))
                .body(this.information())
                .footer(this.footer())
                .build().getCard();
    }

    private @NotNull Node information(){
        VBox bodyContainer = new VBox();

        bodyContainer.getChildren().addAll(
                new Label("Harga beli: " + String.valueOf(product.getPurchasingPrice())),
                new Label("Stock: " + String.valueOf(product.getStock())),
                new Label("Category: " + product.getCategory())
        );
        return bodyContainer;
    }

    private @NotNull HBox footer(){
        HBox buttonContainer = new HBox();
        if (parent instanceof ProductView) {
            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
            deleteButton.setPrefWidth(105);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
            editButton.setPrefWidth(105);

            deleteButton.setOnAction(e -> ((ProductView)parent).delete());
            editButton.setOnAction(e -> ((ProductView)parent).edit());

            buttonContainer.setSpacing(10);
            buttonContainer.getChildren().addAll(deleteButton, editButton);
        } else if (parent instanceof BillProductView) {
            Button tambahButton = new Button("Tambah");
            tambahButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
            tambahButton.setPrefWidth(210);
            tambahButton.setOnAction(e -> ((BillProductView)parent).add(product));
            buttonContainer.setSpacing(10);
            buttonContainer.getChildren().addAll(tambahButton);
        }
        return buttonContainer;
    }


}
