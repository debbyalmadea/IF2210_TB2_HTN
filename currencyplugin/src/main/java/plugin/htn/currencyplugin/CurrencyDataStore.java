package plugin.htn.currencyplugin;

import com.google.gson.reflect.TypeToken;
import com.htn.data.settings.Settings;
import com.htn.datastore.utils.IDataWriter;
import com.htn.datastore.utils.IFileReader;
import com.htn.datastore.utils.IOUtilFactory;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyDataStore {
    @Getter private CurrencySetting data;
    private final String file = "currency";
    private static CurrencyDataStore instance = null;
    private CurrencyDataStore() {
        read();
    }
    public static CurrencyDataStore getInstance() {
        if (instance == null) return new CurrencyDataStore();
        return instance;
    }
    public void update(String currency) {
        data.setCurrentCurrency(currency);
        write();
    }
    public void write() {
        Type type = new TypeToken<CurrencySetting>() {}.getType();
        try {
            IDataWriter writer = IOUtilFactory.getWriter(Settings.getInstance().getFileExtension(), type);
            if (writer != null) writer.writeData(file + Settings.getInstance().getFileExtension(), data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void read() {
        Type type = new TypeToken<CurrencySetting>() {}.getType();
        data = new CurrencySetting();
        try {
            IFileReader reader = IOUtilFactory.getReader(Settings.getInstance().getFileExtension(), type);
            Object result = reader.readFile(file + Settings.getInstance().getFileExtension());
            data.setCurrentCurrency(((CurrencySetting) result).getCurrentCurrency());
            data.setCurrencies(((CurrencySetting) result).getCurrencies());
        } catch (IOException e) {
            data.setCurrentCurrency("IDR");
        }
    }
    public List<String> getAllCurrencyCode() {
        return data.getCurrencies().stream()
                .map(c -> c.getCurrencyCode())
                .collect(Collectors.toList());
    }
    public Currency getCurrency(String currencyCode) {
        List<Currency> filtered = data.getCurrencies().stream()
                .filter(c -> c.getCurrencyCode().equals(currencyCode)).collect(Collectors.toList());
        if (filtered.size() > 0) return filtered.get(0);
        return null;
    }
}
