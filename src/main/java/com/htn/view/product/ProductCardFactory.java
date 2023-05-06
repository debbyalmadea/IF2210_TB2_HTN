package com.htn.view.product;

import com.htn.data.item.Item;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class ProductCardFactory {
    public static Pane getCard(@NotNull Item item, ProductView parent){
        if(item instanceof Item){
            return new ProductCard(item, parent).getCard();
        }
        return new Pane();

    }
}
