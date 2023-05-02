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
    @NonNull int point;
    boolean activated = true;
}
