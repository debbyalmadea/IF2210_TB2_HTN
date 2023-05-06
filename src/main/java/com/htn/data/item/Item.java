package com.htn.data.item;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Item implements Serializable {
    final String id;
    @NotNull String name;
    @NotNull String description;
    @NotNull String image;

    @NotNull double sellingPrice;
    @NotNull double purchasingPrice;
    @NotNull int stock;
    @NotNull String category;
}
