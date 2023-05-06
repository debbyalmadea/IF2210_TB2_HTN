package com.htn.controller;

import com.htn.data.item.Item;
import com.htn.datastore.ProductDataStore;
import com.htn.view.View;
import javafx.collections.ListChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    public static void bindProductData(View view){
        ProductDataStore productData = ProductDataStore.getInstance();
        productData.getProducts().addListener((ListChangeListener<Item>) c -> view.init());
    }
    public static List<Item> getAllProducts(){
        return ProductDataStore.getInstance().getProducts();
    }
     public static List<Item> getProductWithCategory(String category) {
         return getAllProducts().stream().filter(product -> product.getCategory().equals(category))
         .collect(Collectors.toList());
     }

     public static List<Item> getProductWithSellingPrice(double price) {
         return getAllProducts().stream().filter(product -> product.getSellingPrice() == price)
         .collect(Collectors.toList());
     }

     public static List<Item> getProductWithName(String name) {
         return getAllProducts().stream().filter(product -> product.getName().equals(name))
         .collect(Collectors.toList());
     }

     // return product with a certain id
     public static Item getProductWithId(String id){
        return getAllProducts().stream().filter(product->product.getId().equals(id))
                .findFirst().orElse(null);
     }

     public static void addNewProduct(Item item){
        ProductDataStore.getInstance().addNewProduct(item);
     }
     public static void deleteProduct(Item item){
        ProductDataStore.getInstance().delete(item);
     }
     public static void editProduct(Item item, String name, String description, Double sellingPrice, Double purchasingPrice, String category, String image){
        ProductDataStore.getInstance().editProduct(item, name, description, sellingPrice, purchasingPrice, category, image);
     }



}
