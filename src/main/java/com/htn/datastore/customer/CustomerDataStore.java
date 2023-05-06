package com.htn.datastore.customer;

import com.htn.data.customer.Customer;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.google.gson.reflect.TypeToken;
import com.htn.datastore.utils.OBJUtil;
import com.htn.datastore.utils.JSONUtil;
import com.htn.datastore.utils.XMLUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
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
        System.out.println("READ");
        try {
            Object result = reader.readFile(file);
            customers = FXCollections.observableList((ArrayList<Customer>) result);
            Customer.setNumOfCustomer(customers.size());
        } catch (IOException e) {
            customers = FXCollections.observableList(new ArrayList<>());
        }
    }
    public void write() {
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        IDataWriter writer = new JSONUtil(type);
        try {
            writer.writeData(file, new ArrayList<>(customers));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public Customer create() {
        Customer baru = new Customer();
        customers.add(baru);
        write();
        return baru;
    }

    public void update(Customer cust, boolean purchased) {
        customers.remove(cust);
        cust.setPurchased(true);
        customers.add(cust);
        write();
    }
    private void seed() {
        int i;
        for (i = 0; i < 10; i++) {
            customers.add(new Customer());
        }
        write();
    }
}
