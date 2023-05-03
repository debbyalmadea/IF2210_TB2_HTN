package com.htn.data.item;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Item implements Serializable {
    private final String name;
    private final String description;
    private final double price;
    private final Image image;
}
