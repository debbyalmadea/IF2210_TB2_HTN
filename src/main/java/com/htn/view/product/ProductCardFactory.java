package com.htn.view.product;

import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class ProductCardFactory {
    public static Pane getCard(@NotNull String request, ProductView parent){
        if(request.equalsIgnoreCase("product")){
            return new ProductCard(parent).getCard();
        }
        return new Pane();
    }
}
