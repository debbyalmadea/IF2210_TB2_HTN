package plugin.htn.pluginsis2;

import com.htn.api.Plugin;
import com.htn.api.view.SettingsViewExtension;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;

import com.htn.api.IBillCalculator;
import com.htn.controller.ProductController;
import com.htn.data.item.Item;
import com.htn.view.bill.BillProductView;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

public class PluginBillCalculator implements IBillCalculator, Plugin, SettingsViewExtension {

    private Map<String, Integer> quantity;
    private Double price=0.0;
    private Double profit=0.0;
    private Double subTotal = 0.0;

    private Double usedPoints = 0.0;

    private String breakdown = "";

    private VBox summary;

    // TODO: SET FROM SETTINGS
    private static Double tax = 0.10;

    private static Double serviceCharge = 0.07;

    private static Double discount = 0.05;

    private Customer cust = null;
    private boolean _usePoint = false;

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
        if (quantity != null) {
            for (Map.Entry<String, Integer> entry : quantity.entrySet()) {
                String itemId = entry.getKey();
                Item item = ProductController.getProductWithId(itemId);
                int qty = entry.getValue();
                if (item != null) {
                    subtotal += item.getSellingPrice() * qty;
                    buyPrice += item.getPurchasingPrice() * qty;
                }
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

    private void useTax() {
        Double _tax = tax * subTotal;
        price += _tax;
        profit += _tax;
        breakdown += String.format(" Tax: %.4f\n", _tax);
        summary.getChildren().add(new Label("Tax: " + String.format("%.4f", _tax)));
    }

    public void useServiceCharge() {
        Double _tax = serviceCharge * subTotal;
        price += _tax;
        profit += _tax;
        breakdown += String.format(" Serv. Charge: %.4f\n", _tax);
        summary.getChildren().add(new Label("Serv. Charge: " + String.format("%.4f", _tax)));
    }

    public void useCustomDiscount() {
        Double _tax = discount * subTotal;
        price -= _tax;
        profit -= _tax;
        breakdown += String.format(" Plg. Disc.: %.4f\n", _tax);
        summary.getChildren().add(new Label("Plg. Disc.: " + String.format("%.4f", _tax)));
    }

    public Pane displaySetting() {
        VBox allPane = new VBox();

        HBox discountField = new HBox();
        Text discTitle = new Text("Discount Percent (0-1)");
        TextField discountInput = new TextField(discount + "");
        discountField.setSpacing(10);
        discountField.setAlignment(Pos.CENTER_LEFT);
        discountField.getChildren().addAll(discTitle, discountInput);
        discountInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                discount = Double.parseDouble(newValue);
                if (discount < 0.0 || discount > 1.0) {
                    discountInput.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                discount = 0.0;
                discountInput.setText("");
            }
            calculate(cust, _usePoint);
        });

        HBox taxField = new HBox();
        Text taxTitle = new Text("Tax Percent (0-1)");
        TextField taxInput = new TextField(tax+ "");
        taxField.setSpacing(10);
        taxField.setAlignment(Pos.CENTER_LEFT);
        taxField.getChildren().addAll(taxTitle, taxInput);
        taxInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                tax = Double.parseDouble(newValue);
                if (tax < 0.0 || tax > 1.0) {
                    taxInput.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                tax = 0.0;
                taxInput.setText("");
            }
            calculate(cust, _usePoint);
        });

        HBox serviceChargeField = new HBox();
        Text serviceTitle = new Text("Service Charge (0-1)");
        TextField serviceInput = new TextField(""+ serviceCharge);
        serviceChargeField.setSpacing(10);
        serviceChargeField.setAlignment(Pos.CENTER_LEFT);
        serviceChargeField.getChildren().addAll(serviceTitle, serviceInput);
        serviceInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                serviceCharge = Double.parseDouble(newValue);
                if (serviceCharge < 0.0 || serviceCharge > 1.0) {
                    serviceInput.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                serviceCharge = 0.0;
                serviceInput.setText("");
            }
            calculate(cust, _usePoint);
        });

        allPane.getChildren().addAll(taxField, serviceChargeField, discountField);

        return allPane;
    };


    public void calculate(Customer cust, boolean isPoint) {
        this.cust = cust;
        this._usePoint = isPoint;
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