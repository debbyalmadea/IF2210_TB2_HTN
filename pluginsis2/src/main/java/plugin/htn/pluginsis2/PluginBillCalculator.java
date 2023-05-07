package plugin.htn.pluginsis2;

import com.htn.api.Plugin;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;

import com.htn.api.IBillCalculator;
import com.htn.controller.ProductController;
import com.htn.data.item.Item;
import com.htn.view.bill.BillProductView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Map;

public class PluginBillCalculator implements IBillCalculator, Plugin {

    private Map<String, Integer> quantity;
    private Double price=0.0;
    private Double profit=0.0;
    private Double subTotal = 0.0;

    private Double usedPoints = 0.0;

    private String breakdown = "";

    private VBox summary;

    // TODO: SET FROM SETTINGS
    private Double tax = 0.10;

    private Double serviceCharge = 0.1;

    private Double discount = 0.1;

    public void load() {
        System.out.println("LOADED");
        BillProductView.setBillCalculator(new PluginBillCalculator());
    }
    public PluginBillCalculator() {

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

    private void useTax() {
        Double _tax = tax * subTotal;
        price += _tax;
        profit += _tax;
        breakdown += String.format(" Tax: %.2f\n", _tax);
        summary.getChildren().add(new Label("Tax: " + String.format("%.2f", _tax)));
    }

    public void useServiceCharge() {
        Double _tax = serviceCharge * subTotal;
        price += _tax;
        profit += _tax;
        breakdown += String.format(" Serv. Charge: %.2f\n", _tax);
        summary.getChildren().add(new Label("Serv. Charge: " + String.format("%.2f", _tax)));
    }

    public void useCustomDiscount() {
        Double _tax = discount * subTotal;
        price -= _tax;
        profit -= _tax;
        breakdown += String.format(" Plg. Disc.: %.2f\n", _tax);
        summary.getChildren().add(new Label("Plg. Disc.: " + String.format("%.2f", _tax)));
    }

    public void calculate(Customer cust, boolean isPoint) {
        init();
        useTax();
        useServiceCharge();
        useCustomDiscount();
        if (cust instanceof Member) {
            Member member = (Member) cust;
            if (member.isActivated()) {
                System.out.println("OK");
                useDiscount(member);
                if (isPoint) {
                    usePoint(member);
                }
            }
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

    @Override
    public void setQuantity(Map<String, Integer> quantity) {
        this.quantity = quantity;
        calculate(null, false);
    }


}
