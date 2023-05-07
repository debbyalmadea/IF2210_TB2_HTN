package com.htn.api;

import com.htn.api.datastore.CustomerDataStoreExtension;
import com.htn.api.view.View;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.datastore.customer.CustomerDataStore;
import com.htn.datastore.customer.MemberDataStore;
import com.htn.datastore.customer.VIPMemberDataStore;
import com.htn.view.ViewFactory;
import javafx.collections.ListChangeListener;
import org.jetbrains.annotations.NotNull;

public class BasePlugin {
    public static void addPage(String title, Class<? extends View> view) {
        System.out.println("add " + title);
        ViewFactory.getViews().putIfAbsent(title, view);
        if (ViewFactory.getViews().get(title) != null && !ViewFactory.getViews().get(title).equals(view)) {
            System.out.println("Ooops..duplicated name");
        }
    }
    public static void bindCustomerDataStore(@NotNull CustomerDataStoreExtension plugin) {
        plugin.onCustomerDataStoreChange(CustomerDataStore.getInstance());
        CustomerDataStore.getInstance().getData().addListener((ListChangeListener<Customer>) c -> {
            plugin.onCustomerDataStoreChange(CustomerDataStore.getInstance());
        });
        plugin.onMemberDataStoreChange(MemberDataStore.getInstance());
        MemberDataStore.getInstance().getData().addListener((ListChangeListener<Member>) c -> {
            plugin.onMemberDataStoreChange(MemberDataStore.getInstance());
        });
        plugin.onVIPMemberDataStoreChange(VIPMemberDataStore.getInstance());
        VIPMemberDataStore.getInstance().getData().addListener((ListChangeListener<VIPMember>) c -> {
            plugin.onVIPMemberDataStoreChange(VIPMemberDataStore.getInstance());
        });
    }
}