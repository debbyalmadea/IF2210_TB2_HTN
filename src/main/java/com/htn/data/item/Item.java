package com.htn.data.item;

import com.htn.api.datastore.IItem;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Item implements Serializable, IItem {
    // static int numOfItem;
    // final int id = ++numOfItem;
    final String id;
    @NotNull String name;
    @NotNull String description;
    @NotNull String image;

    @NotNull double sellingPrice;
    @NotNull double purchasingPrice;
    @NotNull int stock;
    @NotNull String category;


    @Contract(pure = true)
    public Item(@NotNull Item item) {
        id = item.id;
        name = item.name;
        description = item.description;
        image = item.image;
        sellingPrice = item.sellingPrice;
        purchasingPrice = item.purchasingPrice;
        stock = item.stock;
        category = item.category;
    }
}
