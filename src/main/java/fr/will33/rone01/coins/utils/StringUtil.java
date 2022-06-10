package fr.will33.rone01.coins.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StringUtil {
    public static String formatCurrency(long balance) {
        StringBuilder string = new StringBuilder();
        String[] theAmount = BigDecimal.valueOf(balance).toPlainString().split("\\.");
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###,###", unusualSymbols);

        String amount;
        try {
            amount = decimalFormat.format(Double.parseDouble(theAmount[0]));
        } catch (NumberFormatException var8) {
            amount = theAmount[0];
        }

        string.append(amount).append(" ");
        return string.toString();
    }
}