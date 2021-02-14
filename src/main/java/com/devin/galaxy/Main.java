package com.devin.galaxy;

import com.devin.galaxy.currency.CurrencyMaster;
import java.util.Date;

public class Main {

  public static void main(String[] args) {
    CurrencyMaster currencyMaster = new CurrencyMaster();
    Double a = currencyMaster.find("AED", "AUD", new Date("31-Dec-2020"));
    System.out.println("test" + a);
  }

}
