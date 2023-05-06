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

public class MemberDataStore extends AMemberDataStore {
    @Getter
    private ObservableList<Member> members;
    private static MemberDataStore instance = null;
    private MemberDataStore() {
        super("member.obj");
    }
    public static MemberDataStore getInstance() {
        if (instance == null) {
            instance = new MemberDataStore();
        }
        return instance;
    }
    public void read() {
        Type type = new TypeToken<ObservableList<com.htn.data.customer.Member>>() {}.getType();
        IFileReader reader = new OBJUtil(type);
        try {
            Object result = reader.readFile(file);
            members = FXCollections.observableList((ArrayList<Member>) result);
        } catch (IOException e) {
            members = FXCollections.observableList(new ArrayList<>());
            System.out.println("NO FILE READ MEMBER");
        }
    }
    public void write() {
        Type type = new TypeToken<ObservableList<Member>>() {}.getType();
        IDataWriter writer = new OBJUtil(type);
        try {
            writer.writeData(file, new ArrayList<>(members));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void create(@NotNull Customer customer, String name, String phoneNumber, Double point) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        members.add(new Member(customer.getId(), name, phoneNumber, point));
        write();
    }
    public void create(@NotNull VIPMember vipmember) {
        if (!members.contains(vipmember)) {
            // TODO! Calculate the point ourselves (or is it in controller?)
            VIPMember member = new VIPMember(vipmember.getId(), vipmember.getName(), vipmember.getPhoneNumber(), vipmember.getPoint());
            member.setActivated(member.isActivated());
            members.add(member);
            write();
        }
    }
    public void create(Member member) {
        if (!members.contains(member)) {
            members.add(member);
            write();
        }
    }
    public void delete(Member member) {
        members.remove(member);
        write();
    }
    @Override
    public void update(@NonNull Member member, String name, String phoneNumber, Double point, Boolean activated) {
        super.update(member, name, phoneNumber, point, activated);
        delete(member);
        create(member);
    }
}
