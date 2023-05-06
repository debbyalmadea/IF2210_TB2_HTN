package com.htn.datastore.customer;

import com.google.gson.reflect.TypeToken;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VIPMemberDataStore {
    @Getter
    private ObservableList<VIPMember> vipMembers;
    private static VIPMemberDataStore instance = null;
    private final String file = "vip-member.json";
    private VIPMemberDataStore() {
        read();
    }
    public static VIPMemberDataStore getInstance() {
        if (instance == null) {
            instance = new VIPMemberDataStore();
        }
        return instance;
    }
    public  void read() {
        Type type = new TypeToken<ArrayList<VIPMember>>() {}.getType();
        IFileReader reader = new JSONUtil(type);
        Object result = reader.readFile(file);
        vipMembers = FXCollections.observableList((ArrayList<VIPMember>) result);
    }
    public void write() {
        Type type = new TypeToken<ArrayList<VIPMember>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, vipMembers);
    }
    public void create(@NotNull Customer customer, String name, String phoneNumber, int point) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        vipMembers.add(new VIPMember(customer.getId(), name, phoneNumber, point));
        write();
    }
    public void create(@NotNull Member member) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        vipMembers.add(new VIPMember(member.getId(), member.getName(), member.getPhoneNumber(), member.getPoint()));
        write();
    }
    public void create(VIPMember vipMember) {
        vipMembers.add(vipMember);
        write();
    }
    @Builder(builderMethodName = "updateBuilder")
    public void update(@NonNull VIPMember vipMember, String name, String phoneNumber, Integer point, Boolean activated) {
        if (name != null) vipMember.setName(name);
        if (phoneNumber != null) vipMember.setPhoneNumber(phoneNumber);
        if (point != null) vipMember.setPoint(point);
        if (activated != null) vipMember.setActivated(activated);
        delete(vipMember);
        create(vipMember);
        write();
    }
    public void delete(VIPMember member) {
        vipMembers.remove(member);
        write();
    }
    // TESTING
    private void populate() {
        List<VIPMember> testVIP = new ArrayList<>();
        testVIP.add(new VIPMember("Jisoo", "62746573464", 103));
        testVIP.add(new VIPMember("Lisa", "62746573464", 103));
        testVIP.add(new VIPMember("Jennie", "62746573464", 103));
        testVIP.add(new VIPMember("Rose", "62746573464", 103));
        testVIP.get(0).setActivated(false);
        testVIP.get(1).setActivated(false);
        Type type = new TypeToken<ArrayList<Member>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, testVIP);
    }
}
