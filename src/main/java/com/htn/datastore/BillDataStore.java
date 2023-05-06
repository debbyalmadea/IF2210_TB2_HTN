package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.data.bill.Bill;
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

public class BillDataStore {
    @Getter
    private ObservableList<Bill> bills;
    private static BillDataStore instance = null;
    @Getter @Setter
    private String file = "bill.json";
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
            IFileReader JSONreader = new JSONUtil(type);
            Object result = JSONreader.readFile(file);
            bills = FXCollections.observableList((ArrayList<Bill>) result);
        } catch (IOException e) {
            bills = FXCollections.observableList((new ArrayList<>()));
        }
    }

    public void write() {
        Type type = new TypeToken<ObservableList<Bill>>() {}.getType();
        try {
            IDataWriter JSONwriter = new JSONUtil(type);
            JSONwriter.writeData(file, bills);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNew(String id, String name, String customerId, double price, Date date, ArrayList<String> itemIds) {
        bills.add(new Bill(id, name, customerId, price, date, itemIds));
        write();
    }

    public void addNew(Bill bill) {
        bills.add(bill);
        write();
    }

    public void remove(Bill bill) {
        bills.remove(bill);
        write();
    }

    public void update(@NonNull Bill bill, String name, Double price, Date date, ArrayList<String> itemIds) {
        if (name != null) {
            bill.setName(name);
        }
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
