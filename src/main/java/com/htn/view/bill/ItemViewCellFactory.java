package com.htn.view.bill;

import com.htn.data.item.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class ItemViewCellFactory implements Callback<ListView<Item>, ListCell<Item>> {
    @Override
    public ListCell<Item> call(ListView<Item> listView) {
        return new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                ImageView imageView = new ImageView(item.getImage());
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);

                Label nameLabel = new Label(item.getName());
                nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                Label descLabel = new Label(item.getDescription());
                descLabel.setWrapText(true);
                descLabel.setMaxWidth(200);

                Label priceLabel = new Label("$" + item.getSellingPrice());
                Label stockLabel = new Label("QTY:" + item.getStock());
                priceLabel.setFont(Font.font("Arial", 14));

                HBox itemBox = new HBox(imageView, new VBox(nameLabel, descLabel), priceLabel, stockLabel);
                itemBox.setSpacing(10);
                itemBox.setAlignment(Pos.CENTER_LEFT);
                itemBox.setPadding(new Insets(10, 0, 10, 0));

                setGraphic(itemBox);
            }
        };
    }
}