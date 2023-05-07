package com.htn.api.datastore;

import org.jetbrains.annotations.NotNull;

public interface IItem {
    public String getId();
    public @NotNull String getName();
    public @NotNull String getDescription();
    public @NotNull String getImage();
    public double getSellingPrice();
    public double getPurchasingPrice();
    public int getStock();
    public @NotNull String getCategory();
}
