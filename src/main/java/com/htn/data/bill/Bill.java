package com.htn.data.bill;
import com.htn.data.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Bill implements Serializable {
    private final String id;
    private String name;
    private final String customerId;
    private double price;
    private Date date;
    private ArrayList<String> itemIds;
}