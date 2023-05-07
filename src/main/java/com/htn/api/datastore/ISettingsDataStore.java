package com.htn.api.datastore;

import com.htn.data.settings.Settings;

public interface ISettingsDataStore {
    public Settings getSettings();
    public void write();
    public void read();
}
