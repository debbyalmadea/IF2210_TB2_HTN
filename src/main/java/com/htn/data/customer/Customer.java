package com.htn.data.customer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
// SEMUA HARUS IMPLEMEN SERIALIZABLE KALO MAU DIPRINT
public class Customer implements Serializable {
    static int numOfCustomer;
    final Integer id = ++numOfCustomer;
}