package com.hibernate.example.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class MonetaryAmount {

    private final BigDecimal value;
    private final Currency currency;

    private MonetaryAmount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static MonetaryAmount fromString(String string) {
        String[] s = string.split(" ");
        return new MonetaryAmount(new BigDecimal(s[0]), Currency.getInstance(s[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonetaryAmount that = (MonetaryAmount) o;

        if (!Objects.equals(value, that.value)) return false;
        return Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return value.toString() + " " + currency.getCurrencyCode();
    }
}
