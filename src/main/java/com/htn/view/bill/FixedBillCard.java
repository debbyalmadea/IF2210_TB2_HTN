package com.htn.view.bill;

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
public class FixedBillCard {
    private BillView parent;
    public Pane getCard() {
        return CardBuilder.builder()
                .title("Kim Jisoo")
                .subtitle("Rp 200k")
                .body(this.body())
                .footer(this.footer())
                .styleSheets("vip-card.css")
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("Bill Id: 2345"),
                new Label("Tanggal: 08/21/2003")
        );

        return bodyContainer;
    }
    private @NotNull Node footer() {
        HBox buttonContainer = new HBox();
        Button printButton = new Button("Cetak");
        printButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        printButton.setPrefWidth(105);
        printButton.setOnAction(e -> {
            parent.printBill();
        });


        Button seeButton = new Button("Lihat");
        seeButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        seeButton.setPrefWidth(105);
        seeButton.setOnAction(e -> {
            parent.seeBill();
        });

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(printButton, seeButton);

        return buttonContainer;
    }
}

