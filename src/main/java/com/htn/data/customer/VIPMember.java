package com.htn.data.customer;

import lombok.*;

import java.io.Serializable;

public class VIPMember extends Member implements Serializable {
    @Getter private final double discount = 0.1;
    public VIPMember(String name, String phoneNumber, Integer point) {
        super(name, phoneNumber, point);
    }
    public VIPMember(Integer id, @NonNull String name, @NonNull String phoneNumber, @NonNull Integer point) {
        super(id, name, phoneNumber, point);
    }
}