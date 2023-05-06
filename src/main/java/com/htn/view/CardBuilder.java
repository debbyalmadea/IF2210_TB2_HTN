package com.htn.view;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

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
    @Singular
    private List<String> styleSheets;

    public VBox getCard() {
        VBox card = new VBox();
        this.styleSheets = new ArrayList<>();
        this.styleSheets.add("card.css");

        card.getStylesheets().addAll(this.styleSheets);
        card.getStyleClass().setAll("card");
        card.setPrefWidth(200);
        card.setSpacing(10);

        if (imageURI != null) {
            ImageView imageView = new ImageView();
            Image image;
            image = new Image(getClass().getResource(imageURI).toExternalForm(),
                    180, 0, true, true);
            imageView.setViewport(new Rectangle2D(0, 0, 180, 140));
            imageView.setImage(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(140);
            card.getChildren().add(imageView);
        }

        body.getStyleClass().add("body");
        card.getChildren().addAll(this.header(), body, footer);
        return card;
    }
    private Node header() {
        Label titleLabel = new Label(title);
        Label subtitleLabel = new Label(subtitle);
        titleLabel.getStyleClass().add("title");
        subtitleLabel.getStyleClass().add("subtitle");
        VBox header = new VBox();
        header.getChildren().addAll(titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 10, 0));
        return header;
    }
}
