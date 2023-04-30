package com.htn.view.bill;

import com.htn.view.CardBuilder;
import com.htn.view.customer.CustomerView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class BillCard {
    private BillView parent;
    public Pane getCard() {
        return CardBuilder.builder()
                .title("Kim Jisoo")
                .subtitle("Rp 200k")
                .body(this.body())
                .footer(this.footer())
                .styleSheets("card.css")
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("BillId: 2345"),
                new Label("Tanggal: 08/21/2003")
        );

        return bodyContainer;
    }
    private @NotNull Node footer() {
        HBox buttonContainer = new HBox();
        Button activeButton = new Button("Hapus");
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
