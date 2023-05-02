package com.htn.data.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class VIPMember extends Customer implements Rewardable {
    @NonNull String name;
    @NonNull String phoneNumber;
    @NonNull Integer point;
    boolean activated = true;
}