package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.data.bill.FixedBill;
import com.htn.data.item.Item;
import com.htn.datastore.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class FixedBillDataStore {
    @Getter
    private ArrayList<FixedBill> fixedBills;
    private static FixedBillDataStore instance = null;
    @Getter @Setter
    private String file = "fixed_bill.json";
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
            IFileReader JSONreader = new JSONUtil(type);
            Object result = JSONreader.readFile(file);
            fixedBills = (ArrayList<FixedBill>) result;
        } catch (IOException e) {
            fixedBills = new ArrayList<>();
        }
    }

    public void write() {
        Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
        try {
            IDataWriter JSONwriter = new JSONUtil(type);
            JSONwriter.writeData(file, fixedBills);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNew(@NotNull int id, @NotNull String name, @NotNull double price, @NotNull Date date, @NotNull ArrayList<Item> items) {
        fixedBills.add(new FixedBill(id, name, price, date, items));
        write();
    }

    public void addNew(FixedBill bill) {
        fixedBills.add(bill);
        write();
    }

    public void remove(FixedBill bill) {
        fixedBills.remove(bill);
        write();
    }
}
