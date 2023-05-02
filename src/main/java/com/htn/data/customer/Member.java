package com.htn.data.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Member extends Customer implements Serializable, Rewardable {
    @NonNull String name;
    @NonNull String phoneNumber;
    @NonNull Integer point;
    boolean activated = true;
}
