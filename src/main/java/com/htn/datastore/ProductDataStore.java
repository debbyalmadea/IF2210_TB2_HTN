package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.DataStore;
import com.htn.api.datastore.IItem;
import com.htn.data.item.Item;
import com.htn.data.settings.Settings;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.IOUtilFactory;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductDataStore implements DataStore<Item> {
    @Getter
    private ObservableList<Item> data;
    private static ProductDataStore instance = null;
    @Getter
    @Setter
    private String file = "product";

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
        try {
            IFileReader reader = IOUtilFactory.getReader(Settings.getInstance().getFileExtension(), type);
            Object result = reader.readFile(file + Settings.getInstance().getFileExtension());
            data = FXCollections.observableList((ArrayList<Item>) result);
        } catch (IOException e) {
            data = FXCollections.observableList(new ArrayList<>());
        }
    }

    public void write() {
        Type type = new TypeToken<ObservableList<Item>>() {
        }.getType();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(Settings.getInstance().getFileExtension(), type);
            if (writer != null) writer.writeData(file + Settings.getInstance().getFileExtension(), new ArrayList<>(data));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewProduct(Item item) {
        data.add(item);
        write();
    }

    public void delete(Item item) {
        data.remove(item);
        write();
    }

    public void editProduct(@NotNull Item item, String name, String description, Double sellingPrice,
            Double purchasingPrice, String category, String image, Integer stock) {
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
        if (stock != null) {
            item.setStock(stock);
        }
        write();
        delete(item);
        addNewProduct(item);
    }

}
