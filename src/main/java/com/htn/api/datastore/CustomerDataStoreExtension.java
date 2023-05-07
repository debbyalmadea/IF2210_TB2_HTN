package com.htn.api.datastore;

import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;

public interface CustomerDataStoreExtension {
    public void onCustomerDataStoreChange(DataStore<Customer> customerDataStore);
    public void onMemberDataStoreChange(DataStore<Member> memberDataStore);
    public void onVIPMemberDataStoreChange(DataStore<VIPMember> vipMemberDataStore);
}
