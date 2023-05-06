package com.htn.view.product;

import com.htn.data.item.Item;
import com.htn.view.View;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class ProductCardFactory {
    public static Pane getCard(@NotNull String request, View parent,Object product){
        if(request.equalsIgnoreCase("product")){
            return new ProductCard(parent, (Item) product).getCard();
        }
        return new Pane();
    }
}
