package com.htn.view;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Getter
public class FieldBuilder extends VBox {
    private final String label;
    private final boolean required;
    private final Control field;
    @Builder
    public FieldBuilder(@NonNull String label, boolean required, @NonNull Control field) {
        this.label = label;
        this.required = required;
        this.field = field;

        this.getStylesheets().add("field.css");
        HBox labelContainer = new HBox();
        labelContainer.getChildren().add(new Label(this.label));
        Label requiredLabel = new Label("*");
        requiredLabel.setStyle("-fx-text-fill: #FF6868;");
        requiredLabel.setVisible(this.required);
        labelContainer.getChildren().add(requiredLabel);
        labelContainer.setSpacing(10);

        this.getChildren().addAll(labelContainer, field);
        this.setSpacing(10);
    }
}
