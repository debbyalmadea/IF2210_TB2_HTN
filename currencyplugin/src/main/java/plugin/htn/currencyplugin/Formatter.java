package plugin.htn.currencyplugin;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Formatter {
    @Contract(pure = true)
    public static @NotNull String format(@NotNull Number number) {
        return "Rp" + number + ",00";
    }
}
