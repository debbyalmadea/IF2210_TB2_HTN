package com.htn.view.bill;

import com.htn.data.bill.FixedBill;
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
    private FixedBill fixedBill;
    public Pane getCard() {
        return CardBuilder.builder()
                .title(fixedBill.getName())
                .subtitle(String.valueOf(fixedBill.getPricePaid()))
                .body(this.body())
                .footer(this.footer())
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("Bill Id: " + fixedBill.getId()),
                new Label("Tanggal: " + fixedBill.getDate())
        );

        return bodyContainer;
    }
    private @NotNull Node footer() {
        HBox buttonContainer = new HBox();
        Button printButton = new Button("Cetak");
        printButton.getStyleClass().setAll("btn", "btn-secondary", "btn-small");
        printButton.setPrefWidth(105);
        printButton.setOnAction(e -> {
            parent.printBill();
        });


        Button seeButton = new Button("Lihat");
        seeButton.getStyleClass().setAll("btn", "btn-small");
        seeButton.setPrefWidth(105);
        seeButton.setOnAction(e -> {
            parent.seeBill();
        });

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(printButton, seeButton);

        return buttonContainer;
    }
}

