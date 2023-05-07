package com.htn.api.datastore;

import javafx.collections.ObservableList;

import java.util.List;

public interface DataStore <T> {
    public ObservableList<T> getData();
    public void read();
    public void write();
}
