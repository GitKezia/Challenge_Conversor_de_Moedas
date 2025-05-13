package com.kezia.converter;

public class ExchangeResponse {
    private String result;
    private String base_code;
    private String target_code;
    private double conversion_rate;

    public double getConversionRate() {
        return conversion_rate;
    }
}
