package com.htn.controller;

import com.htn.api.view.View;
import com.htn.data.bill.Bill;
import com.htn.data.bill.FixedBill;
import com.htn.data.item.Item;
import com.htn.datastore.BillDataStore;
import com.htn.datastore.FixedBillDataStore;
import com.htn.datastore.ProductDataStore;
import javafx.collections.ListChangeListener;

import java.util.List;

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


}
