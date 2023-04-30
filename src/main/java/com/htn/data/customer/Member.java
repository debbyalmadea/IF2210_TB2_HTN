package com.htn.data.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Member extends Customer {
    @NonNull String name;
    @NonNull String phoneNumber;
    @NonNull int point;
    boolean activated = true;
}
