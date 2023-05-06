package com.htn.datastore.customer;

import com.htn.data.customer.Customer;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.JSONUtil;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.ArrayList;
public class CustomerDataStore {
    @Getter private ObservableList<Customer> customers;
    private static CustomerDataStore instance = null;
    private final String file = "customer.json";
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
        IFileReader reader = new JSONUtil(type);
        Object result = reader.readFile(file);
        customers = FXCollections.observableList((ArrayList<Customer>) result);
    }
    public void write() {
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        writer.writeData(file, customers);
    }
    public void create() {
        customers.add(new Customer());
        write();
    }
}
