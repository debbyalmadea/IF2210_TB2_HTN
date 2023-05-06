package com.htn.view.product;

import com.htn.data.item.Item;
import com.htn.view.View;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class ProductCardFactory {
    public static Pane getCard(@NotNull Item item, View parent){
        if(item instanceof Item){
            return new ProductCard(parent, item).getCard();
        }
        return new Pane();

    }
}
