package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import com.htn.datastore.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FixedBillDataStore {
    @Getter
    private ArrayList<FixedBill> bills;
    private static FixedBillDataStore instance = null;
    @Getter @Setter
    private String filename = "bills.json";
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
        IFileReader JSONreader = new JSONUtil(type);
        Object result = JSONreader.readFile(filename);
        bills = (ArrayList<FixedBill>) result;
    }

    public void write() {
        Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
        IDataWriter JSONwriter = new JSONUtil(type);
        JSONwriter.writeData(filename, bills);
    }

    public void addNew(@NotNull Customer customer, double price, ArrayList<Item> items) {
        bills.add(new FixedBill(customer, price, items));
        write();
    }

    public void addNew(FixedBill bill) {
        bills.add(bill);
        write();
    }

    public void remove(FixedBill bill) {
        bills.remove(bill);
        write();
    }
}
