package com.htn.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Builder;

@Builder
public class CardBuilder {
    private String imageURI;
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String subtitle = "";

    @Builder.Default
    private Node body = new Pane();
    @Builder.Default
    private Node footer = new Pane();
    @Builder.Default
    private String styleSheets = "card.css";
    public VBox getCard() {
        VBox card = new VBox();
        card.getStylesheets().addAll("card.css", this.styleSheets);
        card.getStyleClass().setAll("card");
        card.setPrefWidth(200);
        card.setSpacing(10);

        Label titleLabel = new Label(title);
        Label subtitleLabel = new Label(subtitle);
        titleLabel.getStyleClass().add("title");
        subtitleLabel.getStyleClass().add("subtitle");
        body.getStyleClass().add("body");
        VBox header = new VBox();
        header.getChildren().addAll(titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 10, 0));

        card.getChildren().addAll(header, body, footer);
        return card;
    }
}
