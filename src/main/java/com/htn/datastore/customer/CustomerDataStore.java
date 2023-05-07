package com.htn.datastore.customer;

import com.htn.api.datastore.DataStore;
import com.htn.api.datastore.ISettingsDataStore;
import com.htn.api.datastore.SettingsObserver;
import com.htn.data.customer.Customer;
import com.htn.data.settings.Settings;
import com.htn.datastore.SettingsDataStore;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.google.gson.reflect.TypeToken;
import com.htn.datastore.utils.IOUtilFactory;
import com.htn.datastore.utils.JSONUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
public class CustomerDataStore implements DataStore, SettingsObserver {
    @Getter private ObservableList<Customer> data;
    private static CustomerDataStore instance = null;
    private final String file = "customer";
    private CustomerDataStore() {
        read();
    }
    public static CustomerDataStore getInstance() {
        if (instance == null) {
            instance = new CustomerDataStore();
        }
        return instance;
    }
    public  void read() {
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstance().getSettings();
            IFileReader reader = IOUtilFactory.getReader(setting.getFileExtension(), type);
            Object result = reader.readFile(setting.getPathDir() + "\\" + file + setting.getFileExtension());
            data = FXCollections.observableList((ArrayList<Customer>) result);
            Customer.setNumOfCustomer(data.size());
        } catch (IOException e) {
            data = FXCollections.observableList(new ArrayList<>());
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        try {
            Settings setting = SettingsDataStore.getInstance().getSettings();
            IDataWriter writer = IOUtilFactory.getWriter(setting.getFileExtension(), type);
            if (writer != null) writer.writeData(setting.getPathDir() + "\\" + file + setting.getFileExtension(), new ArrayList<>(data));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public Customer create() {
        Customer baru = new Customer();
        data.add(baru);
        write();
        return baru;
    }

    public void update(Customer customer, boolean purchased) {
        data.remove(customer);
        customer.setPurchased(true);
        data.add(customer);
        write();
    }
    private void seed() {
        int i;
        for (i = 0; i < 10; i++) {
            data.add(new Customer());
        }
        write();
    }

    @Override
    public void update(ISettingsDataStore settings) {
        write();
        read();
    }
}
