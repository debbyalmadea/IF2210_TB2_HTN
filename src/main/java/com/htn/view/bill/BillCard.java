package com.htn.view.bill;

import com.htn.data.bill.Bill;
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
    private Bill bill;
    public Pane getCard() {
        return CardBuilder.builder()
                .title(bill.getName())
                .subtitle(String.valueOf(bill.getPrice()))
                .body(this.body())
                .footer(this.footer())
                .styleSheets("card.css")
                .build().getCard();
    }
    private @NotNull Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label("BillId: " + String.valueOf(bill.getId())),
                new Label("Tanggal: " + bill.getDate().toLocaleString())
        );

        return bodyContainer;
    }
    private @NotNull Node footer() {
        HBox buttonContainer = new HBox();
        Button deleteButton = new Button("Hapus");
        deleteButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        deleteButton.setPrefWidth(105);
        deleteButton.setOnAction(e -> {
            parent.delete(bill);
        });

        Button editButton = new Button("Edit");
        editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        editButton.setPrefWidth(105);
//        editButton.setOnAction(e -> {
//            parent.edit(String.valueOf(bill.getId()));
//        });

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(deleteButton, editButton);

        return buttonContainer;
    }
}
