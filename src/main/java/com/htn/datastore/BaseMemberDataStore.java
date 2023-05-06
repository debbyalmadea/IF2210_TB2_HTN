package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.data.customer.Member;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

// GATAU KELAS INI MAU DIPAKE APA GA
// TPI JANGAN DULU DIHAPUS
public class BaseMemberDataStore<T extends Member> {
    @Getter
    protected ObservableList<T> members;
    @Getter @Setter
    protected String file;
    protected BaseMemberDataStore(String file) {
        this.file = file;
        read();
    }
    public void read() {
        Type type = new TypeToken<ObservableList<T>>() {}.getType();
        IFileReader reader = new JSONUtil(type);
        Object result = reader.readFile(file);
        members = FXCollections.observableList((ArrayList<T>) result);
    }
    public void write() {
        Type type = new TypeToken<ObservableList<T>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, members);
    }
    public ArrayList<Member> getActiveMembers() {
        return members.stream().filter(Member::isActivated).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Member> getDeactivateMember() {
        return members.stream().filter(member -> !member.isActivated()).collect(Collectors.toCollection(ArrayList::new));
    }
    public void create(T member) {
        members.add(member);
        write();
    }
    @Builder(builderMethodName = "update")
    public void update(@NonNull T member, String name, String phoneNumber, Integer point, Boolean activated) {
        if (name != null) member.setName(name);
        if (phoneNumber != null) member.setPhoneNumber(phoneNumber);
        if (point != null) member.setPoint(point);
        if (activated != null) member.setActivated(activated);
        delete(member);
        create(member);
        write();
    }
    public void delete(T member) {
        members.remove(member);
        write();
    }
}
