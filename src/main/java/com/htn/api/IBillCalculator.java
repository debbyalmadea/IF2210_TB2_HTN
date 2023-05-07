package com.htn.api;

import com.htn.controller.ProductController;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.data.item.Item;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Map;

public interface IBillCalculator {
    public void init();

    public void setQuantity(Map<String, Integer> map);
    public void calculate(Customer member, boolean isPoint);

    public Double getPrice();
    public Double getProfit();
    public Double getUsedPoints();
    public String getBreakDown();
    public VBox getSummary();
}
