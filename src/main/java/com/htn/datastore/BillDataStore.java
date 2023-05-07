package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.DataStore;
import com.htn.data.bill.Bill;
import com.htn.data.settings.Settings;
import com.htn.datastore.utils.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class BillDataStore implements DataStore<Bill> {
    @Getter
    private ObservableList<Bill> data;
    private static BillDataStore instance = null;
    @Getter @Setter
    private String file = "bill";
    private BillDataStore() {
        read();
    }

    public static BillDataStore getInstance() {
        if (instance == null) {
            instance = new BillDataStore();
        }
        return instance;
    }

    public void read() {
        Type type = new TypeToken<ObservableList<Bill>>() {}.getType();
        try {
            IFileReader reader = IOUtilFactory.getReader(Settings.getInstance().getFileExtension(), type);
            Object result = reader.readFile(file + Settings.getInstance().getFileExtension());
            data = FXCollections.observableList((ArrayList<Bill>) result);
        } catch (IOException e) {
            data = FXCollections.observableList((new ArrayList<>()));
        }
    }

    public void write() {
        Type type = new TypeToken<ObservableList<Bill>>() {}.getType();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(Settings.getInstance().getFileExtension(), type);
            if (writer != null) writer.writeData(file + Settings.getInstance().getFileExtension(), new ArrayList<>(data));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNew(String id, String name, String customerId, double price, Date date, ArrayList<String> itemIds, Map<String, Integer> quant) {
        data.add(new Bill(id, price, customerId, date, itemIds, quant));
        write();
    }

    public void addNew(Bill bill) {
        data.add(bill);
        write();
    }

    public void remove(Bill bill) {
        data.remove(bill);
        write();
    }

    public void update(@NonNull Bill bill, String name, Double price, Date date, ArrayList<String> itemIds) {
        if (price != null) {
            bill.setPrice(price);
        }
        if (date != null) {
            bill.setDate(date);
        }
        if (itemIds != null) {
            bill.setItemIds(itemIds);
        }
        remove(bill);
        addNew(bill);
        write();
    }
}
