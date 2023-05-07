package plugin.htn.currencyplugin;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Getter
    private String currencyCode = "IDR";
    private String prefix = "Rp";
    @Getter private Map<String, Double> converter;
}
