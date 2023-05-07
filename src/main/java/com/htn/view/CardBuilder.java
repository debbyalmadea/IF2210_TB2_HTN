package com.htn.view;

import com.htn.api.view.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CardBuilder implements Card {
    @Getter private String title = "";
    private final StringProperty titleProperty;
    @Getter private String subtitle = "";
    private final StringProperty subtitleProperty;
    @Getter @Setter private Pane body = new Pane();
    @Getter @Setter private Pane footer = new Pane();
    private List<String> styleSheets;
    @Getter private VBox card;

    @Builder
    public CardBuilder(String imageURI, String title, String subtitle, Pane body, Pane footer, @Singular List<String> styleSheets) {
        if (title != null) this.title = title;
        if (subtitle != null) this.subtitle = subtitle;
        if (body != null) this.body = body;
        if (footer != null) this.footer = footer;
        if (styleSheets != null) this.styleSheets = styleSheets;

        titleProperty = new SimpleStringProperty(title);
        subtitleProperty = new SimpleStringProperty(subtitle);

        card = new VBox();
        this.styleSheets = new ArrayList<>();
        this.styleSheets.add("card.css");

        card.getStylesheets().addAll(this.styleSheets);
        card.getStyleClass().setAll("card");
        card.setPrefWidth(200);
        card.setSpacing(10);

        if (imageURI != null) {
            ImageView imageView = new ImageView();
            Image image;
            try {
                image = new Image(getClass().getResource(imageURI).toExternalForm(),
                        180, 0, true, true);
            } catch (Exception e) {
                image = new Image(getClass().getResource("/sample_product.png").toExternalForm());
            }
            imageView.setViewport(new Rectangle2D(0, 0, 180, 140));
            imageView.setImage(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(140);
            card.getChildren().add(imageView);
        }

        this.body.getStyleClass().add("body");
        card.getChildren().addAll(this.header(), this.body, this.footer);
    }
    public void setTitle(String title) {
        titleProperty.set(title);
    }
    public void setSubtitle(String subtitle) {
        subtitleProperty.set(subtitle);
    }
//    public VBox getCard() {
//        VBox card = new VBox();
//        this.styleSheets = new ArrayList<>();
//        this.styleSheets.add("card.css");
//
//        card.getStylesheets().addAll(this.styleSheets);
//        card.getStyleClass().setAll("card");
//        card.setPrefWidth(200);
//        card.setSpacing(10);
//
//        if (imageURI != null) {
//            ImageView imageView = new ImageView();
//            Image image;
//            image = new Image(getClass().getResource(imageURI).toExternalForm(),
//                    180, 0, true, true);
//            imageView.setViewport(new Rectangle2D(0, 0, 180, 140));
//            imageView.setImage(image);
//            imageView.setFitWidth(180);
//            imageView.setFitHeight(140);
//            card.getChildren().add(imageView);
//        }
//
//        body.getStyleClass().add("body");
//        card.getChildren().addAll(this.header(), body, footer);
//        return card;
//    }
    private @NotNull Node header() {
        Label titleLabel = new Label();
        titleLabel.textProperty().bind(titleProperty);
        Label subtitleLabel = new Label();
        subtitleLabel.textProperty().bind(subtitleProperty);
        titleLabel.getStyleClass().add("title");
        subtitleLabel.getStyleClass().add("subtitle");
        VBox header = new VBox();
        header.getChildren().addAll(titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 10, 0));
        return header;
    }
}
