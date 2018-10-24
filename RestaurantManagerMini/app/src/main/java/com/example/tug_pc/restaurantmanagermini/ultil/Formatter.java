package com.example.tug_pc.restaurantmanagermini.ultil;

import java.text.DecimalFormat;

public class Formatter {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###.##");

    public static String format(long currency) {
        return DECIMAL_FORMAT.format(currency) + " ₫";
    }
}
