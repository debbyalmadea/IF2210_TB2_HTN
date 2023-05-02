package com.htn.data.customer;

public interface Rewardable {
    public Integer getPoint();
    public String getName();
    public String getPhoneNumber();
    public boolean isActivated();
    public Integer getId();
    public void setPoint(Integer point);
}
