package com.htn.data.bill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class FixedBill implements Serializable {
    private final int id;
    private String name;
    private double price;
    private Date date;
    private ArrayList<Item> items;
}
