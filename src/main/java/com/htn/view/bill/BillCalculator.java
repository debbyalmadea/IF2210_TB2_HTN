package com.htn.view.bill;

import com.htn.api.IBillCalculator;
import com.htn.controller.ProductController;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.data.item.Item;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class BillCalculator implements IBillCalculator {
    private Map<String, Integer> quantity;
    private Double price=0.0;
    private Double profit=0.0;
    private Double subTotal = 0.0;

    private Double usedPoints = 0.0;

    private String breakdown = "";

    private VBox summary;

    BillCalculator() {
    }

    public void setQuantity(Map<String, Integer> args) {
        this.quantity = args;
        init();
    }
    public void init() {
        summary = new VBox();
        summary.setSpacing(20);
        Double subtotal = 0.0;
        Double buyPrice = 0.0;
        breakdown = "";

        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String itemId = entry.getKey();
            Item item = ProductController.getProductWithId(itemId);
            int qty = entry.getValue();
            if (item != null) {
                subtotal += item.getSellingPrice() * qty;
                buyPrice += item.getPurchasingPrice() * qty;
            }
        }
        this.subTotal = subtotal;
        price = subTotal;
        profit = subTotal - buyPrice;
        breakdown += String.format(" SubTotal: %.4f\n", subtotal);
        summary.getChildren().addAll(new Label("  Subtotal: " + String.format("%.4f", subtotal)));
    }
    public VBox getSummary() {
        return summary;
    }

    public String getBreakDown() {
        return breakdown;
    }

    public void useDiscount(Member member) {
        if (member.isActivated() && member instanceof VIPMember) {
            double discount = member.getDiscount() * subTotal;
            price -= discount;
            profit -= discount;
            breakdown += String.format(" Discount: %.4f\n", discount);
            summary.getChildren().add(new Label("Discount: "+ String.format("%.4f", discount)));
        }
    }

    public void usePoint(Member member) {
        if (member.isActivated()) {

            double points = member.getPoint();
            usedPoints = Math.min(points, price);
            price -= usedPoints;
            profit -= usedPoints;
            breakdown += String.format(" Points: %.4f\n", usedPoints);
            summary.getChildren().add(new Label("Points: " + String.format("%.4f", usedPoints)));
        }
    }

    public void calculate(Customer cust, boolean isPoint) {
        init();
        if (cust == null) {
            return;
        }
        if (cust instanceof Member) {
            Member member = (Member) cust;
            if (member.isActivated()) {
                System.out.println("OK");
                useDiscount(member);
                if (isPoint) {
                    usePoint(member);
                }
            }
        } else {
            // Do Nothing
        }
    }

    public Double getPrice() {
        return price;
    };
    public Double getProfit() {
        return profit;
    };
    public Double getUsedPoints() {
        return usedPoints;
    };
    public String getBreakdown() {
        return breakdown;
    };

}
