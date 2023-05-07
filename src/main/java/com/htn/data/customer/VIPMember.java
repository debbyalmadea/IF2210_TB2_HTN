package com.htn.data.customer;

import lombok.*;

import java.io.Serializable;

public class VIPMember extends Member implements Serializable {
    private final static double discount = 0.1;

    public VIPMember(Integer id, @NonNull String name, @NonNull String phoneNumber, @NonNull Double point) {
        super(id, name, phoneNumber, point);
    }
    public double getDiscount() {
        return VIPMember.discount;
    }
}