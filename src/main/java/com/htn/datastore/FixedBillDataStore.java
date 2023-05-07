package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.api.datastore.DataStore;
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

public class FixedBillDataStore implements DataStore<FixedBill> {
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
            IFileReader reader = IOUtilFactory.getReader(Settings.getInstance().getFileExtension(), type);
            Object result = reader.readFile(file + Settings.getInstance().getFileExtension());
            data = FXCollections.observableList((ArrayList<FixedBill>) result);
        } catch (IOException e) {
            data = FXCollections.observableList( new ArrayList<FixedBill>());
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(Settings.getInstance().getFileExtension(), type);
            if (writer != null) writer.writeData(file + Settings.getInstance().getFileExtension(), new ArrayList<>(data));
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
}
