package com.htn.datastore.customer;

import com.google.gson.reflect.TypeToken;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.OBJUtil;
import com.htn.datastore.utils.XMLUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class VIPMemberDataStore extends AMemberDataStore {
    @Getter
    private ObservableList<VIPMember> vipMembers;
    private static VIPMemberDataStore instance = null;
    private VIPMemberDataStore() {
        super("vip-member.obj");
    }
    public static VIPMemberDataStore getInstance() {
        if (instance == null) {
            instance = new VIPMemberDataStore();
        }
        return instance;
    }
    public  void read() {
        Type type = new TypeToken<ArrayList<VIPMember>>() {}.getType();
        IFileReader reader = new OBJUtil(type);
        try {
            Object result = reader.readFile(file);
            vipMembers = FXCollections.observableList((ArrayList<VIPMember>) result);
        } catch (IOException e) {
            vipMembers = FXCollections.observableList(new ArrayList<>());
            System.out.println("NO FILE");
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<VIPMember>>() {}.getType();
        IDataWriter writer = new OBJUtil(type);
        try {
            writer.writeData(file, new ArrayList<>(vipMembers));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void create(@NotNull Customer customer, String name, String phoneNumber, int point) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        vipMembers.add(new VIPMember(customer.getId(), name, phoneNumber, point));
        write();
    }
    @Override
    public void create(@NotNull Member member) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        if (member instanceof VIPMember) {
            vipMembers.add((VIPMember) member);
        }
        if (!vipMembers.contains(member)) {
            VIPMember vipMember = new VIPMember(member.getId(), member.getName(), member.getPhoneNumber(), member.getPoint());
            vipMember.setActivated(member.isActivated());
            vipMembers.add(vipMember);
            write();
        }
    }
    @Override
    public void create(VIPMember vipMember) {
        if (!vipMembers.contains(vipMember)) {
            vipMembers.add(vipMember);
            write();
        }
    }
    @Override
    public void delete(Member member) {
        vipMembers.remove((VIPMember) member);
        write();
    }
    @Override
    public void update(@NonNull Member member, String name, String phoneNumber, Integer point, Boolean activated) {
        super.update(member, name, phoneNumber, point, activated);
        delete(member);
        create((VIPMember) member);
    }
}
