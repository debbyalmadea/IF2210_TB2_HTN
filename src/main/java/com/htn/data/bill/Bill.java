package com.htn.data.bill;
import com.htn.controller.CustomerController;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Bill implements Serializable {
    private final String id;
    private double price;
    private final String customerId;
    private Date date;
    private ArrayList<String> itemIds;

    public String getName() {
        Member member = CustomerController.getMember(Integer.parseInt(customerId));
        if (member != null) {
            return member.getName();
        } else {
            return String.valueOf(customerId);
        }
    }
}
