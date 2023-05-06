package com.htn.datastore.customer;

import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class AMemberDataStore {
    @Getter @Setter
    protected String file;
    protected AMemberDataStore(String file) {
        this.file = file;
        read();
    }
    public abstract void read();
    public abstract void write();
    public abstract void create(Member member);
    public abstract void create(VIPMember vipMember);
    @Builder(builderMethodName = "updateBuilder")
    public void update(@NonNull Member member, String name, String phoneNumber, Double point, Boolean activated) {
        if (name != null) member.setName(name);
        if (phoneNumber != null) member.setPhoneNumber(phoneNumber);
        if (point != null) member.setPoint(point);
        if (activated != null) member.setActivated(activated);
    }
    public abstract void delete(Member member);
}
