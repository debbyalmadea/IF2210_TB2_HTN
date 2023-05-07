package plugin.htn.currencyplugin;

import com.htn.api.BasePlugin;
import com.htn.api.Plugin;
import com.htn.api.datastore.DataStore;
import com.htn.api.datastore.IItem;
import com.htn.api.datastore.ProductDataStoreExtension;
import com.htn.api.view.Card;
import com.htn.api.view.ProductViewExtension;
import com.htn.data.item.Item;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class CurrencyPlugin implements Plugin, ProductViewExtension, ProductDataStoreExtension {
    public void load() {
        System.out.println("Loading currency plugin...");
        BasePlugin.loadProductDataStore(this);
    }
    @Override
    public void updateCardDisplay(@NotNull Card card, @NotNull IItem item) {
        card.setSubtitle(Formatter.format(item.getSellingPrice()));
        Label purchasePrice = (Label) card.getBody().getChildren().get(0);
        purchasePrice.setText("Purchasing price: " + Formatter.format(item.getPurchasingPrice()));
    }
    public void onProductDataStoreChange(@NotNull DataStore<Item> productDataStore) {
        productDataStore.getData().forEach(item -> {
            item.setSellingPrice(convert(item.getSellingPrice()));
            item.setPurchasingPrice(convert(item.getPurchasingPrice()));
        });
        productDataStore.write();
    }
    private double convert(@NotNull Number number) {
        return number.doubleValue();
    }
}