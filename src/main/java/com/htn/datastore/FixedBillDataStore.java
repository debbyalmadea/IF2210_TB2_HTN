package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.DataStore;
import com.htn.api.datastore.ISettingsDataStore;
import com.htn.api.datastore.SettingsObserver;
import com.htn.data.bill.FixedBill;
import com.htn.data.item.Item;
import com.htn.data.settings.Settings;
import com.htn.datastore.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class FixedBillDataStore implements DataStore<FixedBill>, SettingsObserver {
    @Getter
    private ObservableList<FixedBill> data;
    private static FixedBillDataStore instance = null;
    @Getter @Setter
    private String file = "fixed_bill";
    private FixedBillDataStore() {
        read();
    }
    public static FixedBillDataStore getInstance() {
        if (instance == null) {
            instance = new FixedBillDataStore();
        }
        return instance;
    }
    public void read() {
        Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstance().getSettings();
            IFileReader reader = IOUtilFactory.getReader(setting.getFileExtension(), type);
            Object result = reader.readFile(setting.getPathDir() + "/" + file + setting.getFileExtension());
            data = FXCollections.observableList((ArrayList<FixedBill>) result);
        } catch (IOException e) {
            data = FXCollections.observableList( new ArrayList<FixedBill>());
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstance().getSettings();
            IDataWriter writer = IOUtilFactory.getWriter(setting.getFileExtension(), type);
            if (writer != null) writer.writeData(setting.getPathDir()+"/" + file + setting.getFileExtension(), new ArrayList<>(data));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addNew(@NotNull int id, @NotNull String name, @NotNull double pricePaid, double priceProfit, String breakdown, @NotNull Date date, @NotNull ArrayList<Item> items) {
        data.add(new FixedBill(String.valueOf(id), name, pricePaid, priceProfit, breakdown, date, items));
        write();
    }
    public void addNew(FixedBill bill) {
        data.add(bill);
        write();
    }
    public void remove(FixedBill bill) {
        data.remove(bill);
        write();
    }

    @Override
    public void update(ISettingsDataStore settings) {
        write();
        read();
    }
}
