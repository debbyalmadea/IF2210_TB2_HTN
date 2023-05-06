package com.htn.data.customer;

public interface IMember {
    public Integer getPoint();
    public void setPoint(Integer point);
    public String getName();
    public void setName(String name);
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
    public boolean isActivated();
    public void setActivated(boolean activated);
    public Integer getId();
}
