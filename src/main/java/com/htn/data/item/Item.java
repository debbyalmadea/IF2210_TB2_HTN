package com.htn.data.item;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Item implements Serializable {
    static int numOfItem;
    final int id = ++numOfItem;
    @NotNull String name;
    @NotNull String description;
//    @NotNull double price;
    @NotNull Image image;

    @NotNull double sellingPrice;
    @NotNull double purchasingPrice;
    @NotNull int stock;
    @NotNull String category;



}
