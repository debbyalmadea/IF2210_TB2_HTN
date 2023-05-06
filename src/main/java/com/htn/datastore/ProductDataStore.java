package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.application.AppWindow;
import com.htn.data.item.Item;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductDataStore {
    @Getter
    private ObservableList<Item> products;
    private static ProductDataStore instance = null;
    @Getter
    @Setter
    private String file = "product.json";

    private ProductDataStore() {
        read();
    }

    public static ProductDataStore getInstance() {
        if (instance == null) {
            instance = new ProductDataStore();
        }
        return instance;
    }

    public void read() {
        Type type = new TypeToken<ObservableList<Item>>() {
        }.getType();
        IFileReader reader = new JSONUtil(type);
        Object result = null;
        try {
            result = reader.readFile(file);
            products = FXCollections.observableList((ArrayList<Item>) result);
        } catch (IOException e) {
            products = FXCollections.observableList(new ArrayList<>());
        }
    }

    // public ArrayList<Item> getProductWithCategory(String category) {
    // return products.stream().filter(product -> product.getCategory() == category)
    // .collect(Collectors.toCollection(ArrayList::new));
    // }
    //
    // public ArrayList<Item> getProductWithSellingPrice(double price) {
    // return products.stream().filter(product -> product.getSellingPrice() ==
    // price)
    // .collect(Collectors.toCollection(ArrayList::new));
    // }
    //
    // public ArrayList<Item> getProductWithName(String name) {
    // return products.stream().filter(product -> product.getName() == name)
    // .collect(Collectors.toCollection(ArrayList::new));
    // }

    public void write() {
        Type type = new TypeToken<ObservableList<Item>>() {
        }.getType();
        IDataWriter writer = new JSONUtil(type);
        try {
            writer.writeData(file, products);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewProduct(String id, String name, String description, String image, double sellingPrice,
            double purchasingPrice,
            int stock, String category) {
        products.add(new Item(id, name, description, image, sellingPrice, purchasingPrice, stock, category));
        write();
    }

    public void delete(Item item) {
        products.remove(item);
        write();
    }

    public void editProduct(@NotNull Item item, String name, String description, Double sellingPrice,
            Double purchasingPrice, String category, String image) {
        if (name != null)
            item.setName(name);
        if (description != null)
            item.setDescription(description);
        if (image != null)
            item.setImage(image);
        if (sellingPrice != null)
            item.setSellingPrice(sellingPrice);
        if (purchasingPrice != null)
            item.setPurchasingPrice(purchasingPrice);
        if (category != null)
            item.setCategory(category);
        write();
    }
    // private void populate(){
    // // List<com.htn.data.customer.Member> testMember = new ArrayList<>();
    // // testMember.add(new Member("Akane", "25372534123", 20));
    // // testMember.add(new Member("Kana", "25372534123", 20));
    // // testMember.add(new Member("Aqua", "25372534123", 20));
    // // Type type = new TypeToken<ArrayList<Member>>() {}.getType();
    // // IDataWriter writer = new JSONUtil(type);
    // // writer.writeData(file, testMember);
    // this.addNewProduct("10000", "Horse", "cute horse", "/sample_product.png",
    // 40.0, 15.0,
    // 12, "animal");

    // }
    // public static void main(String[]args){
    // ProductDataStore store = new ProductDataStore();
    // store.populate();
    // }

}
