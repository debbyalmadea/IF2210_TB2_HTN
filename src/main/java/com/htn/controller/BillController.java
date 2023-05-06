package com.htn.controller;

import com.htn.data.bill.Bill;
import com.htn.data.item.Item;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class BillController {

    static private ArrayList<Bill> bills;

    public static void init() {
        bills = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("2");
        items.add("3");
        bills.add(new Bill("1", 32423.42, String.valueOf(2), new Date(), items));
        bills.add(new Bill("2", 324234.324, String.valueOf(3), new Date(), items));
    }

    public static void create(Bill bill) {
        bills.add(bill);
    }

    public static ArrayList<Bill> getAll() {
        return bills;
    }

    public static Bill getId(String id) {
        Optional<Bill> elem = bills.stream().filter(e -> {
            return id.equalsIgnoreCase(String.valueOf(e.getId()));
        }).findFirst();

        if (elem.isPresent()) {
            return elem.get();
        } else {
            return null;
        }
    }

    public static void delete(String id) {
        Bill billDelete = BillController.getId(id);
        System.out.println(id);
        System.out.println(billDelete);
        if (billDelete != null) {
            System.out.println("ok IN");
            System.out.println(bills.remove(billDelete));
        }
    }

    public static void update(String id, Bill bill) {
        Bill billUpdate = BillController.getId(id);
        if (billUpdate != null) {
            bills.remove(billUpdate);
            bills.add(bill);
        }
    }
}
