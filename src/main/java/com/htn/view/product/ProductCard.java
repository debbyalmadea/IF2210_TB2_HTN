package com.htn.view.product;

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
    private ProductView parent;
    public Pane getCard(){
        return CardBuilder.builder()
                .imageURI("/sample_product.png")
                .title("Meal Set")
                .subtitle("Rp 25.000,00")
                .body(this.information())
                .footer(this.footer())
                .build().getCard();
    }

    private @NotNull Node information(){
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("Harga beli: Rp 20.000,00"),
                new Label("Stock: 20"),
                new Label("Category: food")

        );
        return bodyContainer;
    }

    private @NotNull HBox footer(){
        HBox buttonContainer = new HBox();
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        deleteButton.setPrefWidth(105);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        editButton.setPrefWidth(105);

        deleteButton.setOnAction(e -> parent.delete());
        editButton.setOnAction(e -> parent.edit());

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(deleteButton, editButton);

        return buttonContainer;
    }


}
