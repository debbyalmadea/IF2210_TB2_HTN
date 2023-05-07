package com.htn.api.datastore;

import com.htn.api.datastore.ISettingsDataStore;

public interface SettingsObserver {
    public void update(ISettingsDataStore settingsDataStore);
}
