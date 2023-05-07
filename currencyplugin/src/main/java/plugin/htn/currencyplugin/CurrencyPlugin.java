package plugin.htn.currencyplugin;

import com.htn.api.BasePlugin;
import com.htn.api.Plugin;
import com.htn.api.datastore.*;
import com.htn.api.view.Card;
import com.htn.api.view.ProductViewExtension;
import com.htn.api.view.SettingsViewExtension;
import com.htn.data.item.Item;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyPlugin implements Plugin, ProductViewExtension, ProductDataStoreExtension, SettingsViewExtension {
    private String prevCurrency;
    public void load() {
        System.out.println("Loading currency plugin...");
    }
    @Override
    public void updateCardDisplay(@NotNull Card card, @NotNull Item item) {
        card.setSubtitle(format(item.getSellingPrice()));
        Label purchasePrice = (Label) card.getBody().getChildren().get(0);
        purchasePrice.setText("Purchasing price: " + format(item.getPurchasingPrice()));
    }
    public void onProductDataStoreChange(@NotNull DataStore<Item> productDataStore) {
        productDataStore.getData().forEach(item -> {
            item.setSellingPrice(convert(item.getSellingPrice()));
            item.setPurchasingPrice(convert(item.getPurchasingPrice()));
        });
        Item temp = productDataStore.getData().get(productDataStore.getData().size() - 1);
        productDataStore.getData().remove(temp);
        productDataStore.getData().add(temp);
        productDataStore.write();
    }
    public Pane displaySetting() {
        Text title = new Text("Currency");
        ComboBox<String> currencyBox = new ComboBox<>();
        currencyBox.getItems().addAll(CurrencyDataStore.getInstance().getAllCurrencyCode());
        currencyBox.setValue(CurrencyDataStore.getInstance().getData().getCurrentCurrency());
        currencyBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            CurrencyDataStore.getInstance().update(newValue);
            prevCurrency = oldValue;
            BasePlugin.loadProductDataStore(this);
        });
        HBox currencyField = new HBox();
        currencyField.setSpacing(10);
        currencyField.setAlignment(Pos.CENTER_LEFT);
        currencyField.getChildren().addAll(title, currencyBox);
        return currencyField;
    };
    private double convert(@NotNull Number number) {
        String current = CurrencyDataStore.getInstance().getData().getCurrentCurrency();

        List<Currency> currencyList = CurrencyDataStore.getInstance().getData().getCurrencies()
                .stream().filter(c -> c.getCurrencyCode().equalsIgnoreCase(prevCurrency)).collect(Collectors.toList());
        if (currencyList.size() > 0) {
            Number converter = currencyList.get(0).getConverter().get(current);
            System.out.println(prevCurrency + current + converter);
            return number.doubleValue() * converter.doubleValue();
        }
        return number.doubleValue();
    }
    @Contract(pure = true)
    public static @NotNull String format(@NotNull Number number) {
        String current = CurrencyDataStore.getInstance().getData().getCurrentCurrency();
        Currency currentCurrency = CurrencyDataStore.getInstance().getCurrency(current);
        if (currentCurrency != null) {
            return currentCurrency.getPrefix() + String.format("%.2f", number.doubleValue());
        }
        return number.toString();
    }
}