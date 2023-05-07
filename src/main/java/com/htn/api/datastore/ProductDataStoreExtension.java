package com.htn.api.datastore;

import com.htn.data.item.Item;

public interface ProductDataStoreExtension {
    public void onProductDataStoreChange(DataStore<Item> productDataStore);
}
