package com.htn.controller;

import com.htn.data.bill.Bill;
import com.htn.data.item.Item;
import com.htn.view.product.ProductCardFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ItemController {

    private ArrayList<Item> items;

    public ItemController() {
        items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Item(String.valueOf(i), "Barang " + String.valueOf(i),"deskripsi", "/sample_product.png", 100000, 50000, 5, "Mainan" ));
        }
    }

    public ArrayList<Item> getAll() {
        return items;
    }

    public Item getId(String id) {
        Optional<Item> elem = items.stream().filter(e -> {
            return id.equalsIgnoreCase(String.valueOf(e.getId()));
        }).findFirst();

        if (elem.isPresent()) {
            return elem.get();
        } else {
            return null;
        }
    }
}
