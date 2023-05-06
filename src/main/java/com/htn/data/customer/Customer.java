package com.htn.data.customer;


import lombok.Getter;

import java.io.Serializable;

// SEMUA HARUS IMPLEMEN SERIALIZABLE KALO MAU DIPRINT
public class Customer implements Serializable {
    private static int numOfCustomer;
    @Getter private final Integer id;
    public Customer() {
        this.id = ++numOfCustomer;
    }
    public Customer(Integer id) {
        this.id = id;
    }
}