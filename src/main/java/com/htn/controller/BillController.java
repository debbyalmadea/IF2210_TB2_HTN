package com.htn.controller;

import com.htn.data.bill.Bill;
import com.htn.data.item.Item;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class BillController {

    private ArrayList<Bill> bills;

    public BillController() {
        bills = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("2");
        bills.add(new Bill("1", "Ananta", "1232", 324324,new Date(), items));
        bills.add(new Bill("2", "Ananta Sasha", "1232.123", 3242,new Date(), items));
    }

    public void create(Bill bill) {
        bills.add(bill);
    }

    public ArrayList<Bill> getAll() {
        return bills;
    }

    public Bill getId(String id) {
        Optional<Bill> elem = bills.stream().filter(e -> {
            return id.equalsIgnoreCase(String.valueOf(e.getId()));
        }).findFirst();

        if (elem.isPresent()) {
            return elem.get();
        } else {
            return null;
        }
    }

    public void delete(String id) {
        Bill billDelete = this.getId(id);
        System.out.println(id);
        System.out.println(billDelete);
        if (billDelete != null) {
            System.out.println("ok IN");
            System.out.println(bills.remove(billDelete));
        }
    }

    public void update(String id, Bill bill) {
        Bill billUpdate = this.getId(id);
        if (billUpdate != null) {
            bills.remove(billUpdate);
            bills.add(bill);
        }
    }
}
