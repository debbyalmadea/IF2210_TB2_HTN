package com.htn.datastore.customer;

import com.htn.api.datastore.DataStore;
import com.htn.data.customer.Customer;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.google.gson.reflect.TypeToken;
import com.htn.datastore.utils.OBJUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
public class CustomerDataStore implements DataStore {
    @Getter private ObservableList<Customer> data;
    private static CustomerDataStore instance = null;
    private final String file = "customer.obj";
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
        IFileReader reader = new OBJUtil(type);
        System.out.println("READ");
        try {
            Object result = reader.readFile(file);
            data = FXCollections.observableList((ArrayList<Customer>) result);
        } catch (IOException e) {
            data = FXCollections.observableList(new ArrayList<>());
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        IDataWriter writer = new OBJUtil(type);
        try {
            writer.writeData(file, new ArrayList<>(data));
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
    private void seed() {
        int i;
        for (i = 0; i < 10; i++) {
            data.add(new Customer());
        }
        write();
    }
}
