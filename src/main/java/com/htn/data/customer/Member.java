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
    @Setter @NonNull Double point = 0.0;
    private final static double discount = 0.0;
    @Getter boolean activated = true;
    public Member(Integer id, @NonNull String name, @NonNull String phoneNumber, @NonNull Double point) {
        super(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
    }
    public double getDiscount() {
        return Member.discount;
    }

    public void givePoints(Double value) {
        point += value * 0.01;
    }
}
