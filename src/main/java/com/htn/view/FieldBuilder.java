package com.htn.view;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.NonNull;

public class FieldBuilder extends VBox {
    @Builder
    public FieldBuilder(@NonNull String label, boolean required, @NonNull Control field) {
        this.getStylesheets().add("field.css");
        HBox labelContainer = new HBox();
        labelContainer.getChildren().add(new Label(label));
        Label requiredLabel = new Label("*");
        requiredLabel.setStyle("-fx-text-fill: #FF6868;");
        requiredLabel.setVisible(required);
        labelContainer.getChildren().add(requiredLabel);
        labelContainer.setSpacing(10);

        this.getChildren().addAll(labelContainer, field);
        this.setSpacing(10);
    }
}
