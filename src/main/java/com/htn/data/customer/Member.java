package com.htn.data.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Member extends Customer implements Serializable {
    @NonNull String name;
    @NonNull String phoneNumber;
    @NonNull Integer point;
    private final static double discount = 0.0;
    boolean activated = true;
    public Member(Integer id, @NonNull String name, @NonNull String phoneNumber, @NonNull Integer point) {
        super(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
    }
    public double getDiscount() {
        return Member.discount;
    }
}
