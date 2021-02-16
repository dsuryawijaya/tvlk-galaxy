package com.galaxy.currency;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrencyAccessorMain {

    public static void main(String[] args) throws Exception {
        CurrencyAccessor currencyAccessor = new CurrencyAccessor();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2018-10-09");
        System.out.println(date.getTime());
        // expects 3.451600000000000
        System.out.println(currencyAccessor.getRates("THB", "JPY", date));
    }
}
