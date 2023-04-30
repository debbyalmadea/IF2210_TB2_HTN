package com.htn.data.item;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
    private final String name;
    private final String description;
    private final double price;
    private final Image image;
}
