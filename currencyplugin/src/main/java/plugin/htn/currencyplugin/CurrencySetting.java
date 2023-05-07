package plugin.htn.currencyplugin;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class CurrencySetting {
    @Getter @Setter
    private String currentCurrency = "IDR";
    @Getter @Setter
    private ArrayList<Currency> currencies = new ArrayList<>();
}
