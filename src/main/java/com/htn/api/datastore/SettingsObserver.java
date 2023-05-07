package com.htn.api.datastore;

import com.htn.data.settings.Settings;

public interface SettingsObserver {
    public void update(ISettingsDataStore settings);
}
