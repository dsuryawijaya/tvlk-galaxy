package com.devin.galaxy.currency;

import java.util.Date;

public class CurrencyMain {
  public static void main(String[] args) {
    CurrencyMaster currencyMaster = new CurrencyMaster();
    Double a = currencyMaster.find("AED", "AUD", new Date("31-Dec-2020"));
    System.out.println("test: " + a);
  }
}
