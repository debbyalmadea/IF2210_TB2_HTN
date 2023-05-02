package com.htn.model.customer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Customer {
    static int numOfCustomer;
    final Integer id = ++numOfCustomer;
}