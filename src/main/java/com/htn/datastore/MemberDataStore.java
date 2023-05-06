package com.htn.datastore;

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
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MemberDataStore {
    @Getter
    private ObservableList<Member> members;
    private static MemberDataStore instance = null;
    @Getter @Setter
    private String file = "member.json";
    private MemberDataStore() {
        read();
    }
    public static MemberDataStore getInstance() {
        if (instance == null) {
            instance = new MemberDataStore();
        }
        return instance;
    }
    public void read() {
        Type type = new TypeToken<ObservableList<com.htn.data.customer.Member>>() {}.getType();
        IFileReader reader = new JSONUtil(type);
        Object result = reader.readFile(file);
        members = FXCollections.observableList((ArrayList<Member>) result);
    }
    public void write() {
        Type type = new TypeToken<ObservableList<Member>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, members);
    }
    public void create(@NotNull Customer customer, String name, String phoneNumber, int point) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        members.add(new Member(customer.getId(), name, phoneNumber, point));
        write();
    }
    public void create(@NotNull VIPMember vipmember) {
        // TODO! Calculate the point ourselves (or is it in controller?)
        members.add(new Member(vipmember.getId(), vipmember.getName(), vipmember.getPhoneNumber(), vipmember.getPoint()));
        write();
    }
    public void create(Member member) {
        members.add(member);
        write();
    }
    @Builder(builderMethodName = "updateBuilder")
    public void update(@NonNull Member member, String name, String phoneNumber, Integer point, Boolean activated) {
        if (name != null) member.setName(name);
        if (phoneNumber != null) member.setPhoneNumber(phoneNumber);
        if (point != null) member.setPoint(point);
        if (activated != null) member.setActivated(activated);
        delete(member);
        create(member);
        write();
    }
    public void delete(Member member) {
        members.forEach(System.out::println);
        System.out.println("removing " + member);
        members.remove(member);
        write();
    }
    // TESTING
    private void populate() {
        List<com.htn.data.customer.Member> testMember = new ArrayList<>();
        testMember.add(new Member("Akane", "25372534123", 20));
        testMember.add(new Member("Kana", "25372534123", 20));
        testMember.add(new Member("Aqua", "25372534123", 20));
        Type type = new TypeToken<ArrayList<Member>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, testMember);
    }
}
