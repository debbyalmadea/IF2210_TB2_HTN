package com.htn.data.bill;
import com.htn.controller.CustomerController;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class FixedBill implements Serializable {
    private final String id;
    private String customerId;
    private double pricePaid;
    private double priceProfit;
    private String breakdown;
    private Date date;
    private ArrayList<Item> items;

    public String getName() {
        Member member = CustomerController.getMember(Integer.parseInt(customerId));
        if (member != null) {
            return member.getName();
        } else {
            return String.valueOf(customerId);
        }
    }
}
