package com.htn.view.bill;

import com.htn.controller.ProductController;
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

@Getter
@Setter
public class BillCalculator {
    private Map<String, Integer> quantity;
    private Double price=0.0;
    private Double profit=0.0;
    private Double subTotal = 0.0;

    private Double usedPoints = 0.0;

    private String breakdown = "";

    private VBox summary;

    BillCalculator(Map<String, Integer> args) {
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
        breakdown += String.format(" SubTotal: %.2f\n", subtotal);
        summary.getChildren().addAll(new Label("  Subtotal: " + String.format("%.2f", subtotal)));
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
            breakdown += String.format(" Discount: %.2f\n", discount);
            summary.getChildren().add(new Label("Discount: "+ String.format("%.2f", discount)));
        }
    }

    public void usePoint(Member member) {
        if (member.isActivated()) {

            double points = member.getPoint();
            usedPoints = Math.min(points, price);
            price -= usedPoints;
            profit -= usedPoints;
            breakdown += String.format(" Points: %.2f\n", usedPoints);
            summary.getChildren().add(new Label("Points: " + String.format("%.2f", usedPoints)));
        }
    }

    public void useMember(Member member, boolean isPoint) {
        if (member == null) {
            return;
        }
        init();
        if (member.isActivated()) {
            System.out.println("OK");
            useDiscount(member);
            if (isPoint) {
                usePoint(member);
            }
        }
    }
}
