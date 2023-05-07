package com.htn.data.customer;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// SEMUA HARUS IMPLEMEN SERIALIZABLE KALO MAU DIPRINT
@Setter
@Getter
public class Customer implements Serializable {
    private static int numOfCustomer;

    private boolean purchased = false;
    @Getter private final Integer id;
    public Customer() {
        this.id = ++numOfCustomer;
    }
    public Customer(Integer id) {
        this.id = id;
    }

    public static void setNumOfCustomer(int numOfCustomer) {
        Customer.numOfCustomer = numOfCustomer;
    }
}