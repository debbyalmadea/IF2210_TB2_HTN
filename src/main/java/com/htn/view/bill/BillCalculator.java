package com.htn.view.bill;

import com.htn.controller.ProductController;
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

        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
            String itemId = entry.getKey();
            Item item = ProductController.getProductWithId(itemId);
            int qty = entry.getValue();
            subtotal += item.getSellingPrice() * qty;
            buyPrice += item.getPurchasingPrice() * qty;
        }
        // TODO GANTI JD LEGIT
        Double diskon = subtotal * 0.10;
        Double pajak = subtotal * 0.07;
        Double total = subtotal - diskon + pajak;
        price = total;
        profit = total - buyPrice;
        breakdown = String.format("  SubTotal: %.2f\n  Diskon:  %.2f\n  Pajak: %.2f\n  Total: %.2f\n", subtotal,diskon, pajak, total);
        summary.getChildren().addAll(new Label("Cost Breakdown:"), new Label("  Subtotal: " + String.format("%.2f", subtotal)), new Label("  Diskon: " + String.format("%.2f", diskon)), new Label("  Pajak: " + String.format("%.2f", pajak)), new Label("  Total: " + String.format("%.2f", total)));
    }
    public VBox getSummary() {
        return summary;
    }

    public String getBreakDown() {
        return breakdown;
    }
}
