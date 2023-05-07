package com.htn.controller;

import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.item.Item;
import com.htn.datastore.BillDataStore;
import com.htn.datastore.FixedBillDataStore;
import com.htn.datastore.ProductDataStore;
import com.htn.view.View;
import javafx.collections.ListChangeListener;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillController {

    public static void bindFixedBillData(View view) {
        FixedBillDataStore productData = FixedBillDataStore.getInstance();
        productData.getFixedBills().addListener((ListChangeListener<FixedBill>) c -> view.init());
    }

    public static void bindBillData(View view) {
        BillDataStore productData = BillDataStore.getInstance();
        productData.getBills().addListener((ListChangeListener<Bill>) c -> view.init());
    }

    public static List<FixedBill> getAllFixedBill() {
        return FixedBillDataStore.getInstance().getFixedBills();
    }

    public static List<Bill> getAllBill() {
        return BillDataStore.getInstance().getBills();
    }

    public static void deleteBill(Bill bill) {
        BillDataStore.getInstance().remove(bill);
    }


    public static void addNewBill(Bill bill) {
        BillDataStore.getInstance().addNew(bill);
    }
    public static void addNewFixedBill(FixedBill bill) {
        FixedBillDataStore.getInstance().addNew(bill);
    }
    public static void deleteProduct(Item item) {
        ProductDataStore.getInstance().delete(item);
    }
    public static FixedBill getFixedBillWithId(String id){return getAllFixedBill().stream().filter(fixedBill -> fixedBill.getId().equals(id))
            .findFirst().orElse(null);}

    public static List<Object> getSearchedBill(String  textToSearch) {
        return (List) getAllBill().stream().filter(bill->bill.getId().equalsIgnoreCase(textToSearch) || bill.getName().equalsIgnoreCase(textToSearch)).toList();
    }

    public static List<Object> getSearchedFixedBill(String  textToSearch) {
        return (List) getAllFixedBill().stream().filter(bill->bill.getId().equalsIgnoreCase(textToSearch) || bill.getName().equalsIgnoreCase(textToSearch)).toList();
    }


}
