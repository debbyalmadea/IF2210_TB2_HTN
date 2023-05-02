package com.htn.data.bill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
public class FixedBill implements Serializable {
    private Customer customer;
    private double price;
    private ArrayList<Item> items;
}
