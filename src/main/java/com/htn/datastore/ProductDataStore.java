package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.DataStore;
import com.htn.api.datastore.ISettingsDataStore;
import com.htn.api.datastore.SettingsObserver;
import com.htn.data.item.Item;
import com.htn.data.settings.Settings;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.IOUtilFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductDataStore implements DataStore<Item>, SettingsObserver {
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
        Settings.getInstance().bind(this);
        Type type = new TypeToken<ObservableList<Item>>() {
        }.getType();
        Settings setting = SettingsDataStore.getInstance().getSettings();
        System.out.println(setting.getFileExtension());
        try {
            IFileReader reader = IOUtilFactory.getReader(setting.getFileExtension(),type);
            Object result = reader.readFile(setting.getPathDir()+ "/" + file + setting.getFileExtension());
            System.out.println(setting.getPathDir()+ "/" + file + setting.getFileExtension());
            data = FXCollections.observableList((ArrayList<Item>) result);
        } catch (IOException e) {
            data = FXCollections.observableList(new ArrayList<>());
        }
    }

    public void write() {
        Type type = new TypeToken<ObservableList<Item>>() {
        }.getType();
        Settings setting = SettingsDataStore.getInstance().getSettings();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(setting.getFileExtension(), type);
            if (writer != null) writer.writeData(setting.getPathDir()+"/" + file+ setting.getFileExtension(), new ArrayList<>(data));
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

    @Override
    public void update(ISettingsDataStore settings) {
        write();
        read();
    }
}
