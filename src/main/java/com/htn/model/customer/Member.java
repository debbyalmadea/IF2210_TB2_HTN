package com.htn.model.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Member extends Customer implements Rewardable {
    @NonNull String name;
    @NonNull String phoneNumber;
    @NonNull Integer point;
    boolean activated = true;
}
