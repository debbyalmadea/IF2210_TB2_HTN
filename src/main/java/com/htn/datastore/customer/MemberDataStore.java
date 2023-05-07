package com.htn.datastore.customer;

import com.google.gson.reflect.TypeToken;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.data.settings.Settings;
import com.htn.datastore.SettingsDataStore;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.IOUtilFactory;
import com.htn.datastore.utils.OBJUtil;
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
    private ObservableList<Member> data;
    private static MemberDataStore instance = null;
    private MemberDataStore() {
        super("member");
    }
    public static MemberDataStore getInstance() {
        if (instance == null) {
            instance = new MemberDataStore();
        }
        return instance;
    }
    public void read() {
        Type type = new TypeToken<ObservableList<com.htn.data.customer.Member>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstanceWithoutPlugin().getSettings();
            IFileReader reader = IOUtilFactory.getReader(setting.getFileExtension(), type);
            Object result = reader.readFile(setting.getPathDir() + "\\" + file + setting.getFileExtension());
            data = FXCollections.observableList((ArrayList<Member>) result);
            Customer.setNumOfCustomer(data.size());
        } catch (IOException e) {
            data = FXCollections.observableList(new ArrayList<>());
        }
    }
    public void write() {
        Type type = new TypeToken<ObservableList<Member>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstance().getSettings();
            IDataWriter writer = IOUtilFactory.getWriter(setting.getFileExtension(), type);
            writer.writeData(setting.getPathDir() + "\\" + file + setting.getFileExtension(), new ArrayList<>(data));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void create(@NotNull Customer customer, String name, String phoneNumber, Double point) {
        data.add(new Member(customer.getId(), name, phoneNumber, point));
        write();
    }
    public void create(@NotNull VIPMember vipmember) {
        if (!data.contains(vipmember)) {
            Member member = new Member(vipmember.getId(), vipmember.getName(), vipmember.getPhoneNumber(), vipmember.getPoint());
            member.setActivated(member.isActivated());
            data.add(member);
            write();
        }
    }
    public void create(Member member) {
        if (!data.contains(member)) {
            data.add(member);
            write();
        }
    }
    public void delete(Member member) {
        data.remove(member);
        write();
    }
    @Override
    public void update(@NonNull Member member, String name, String phoneNumber, Double point, Boolean activated) {
        super.update(member, name, phoneNumber, point, activated);
        delete(member);
        create(member);
    }
}
