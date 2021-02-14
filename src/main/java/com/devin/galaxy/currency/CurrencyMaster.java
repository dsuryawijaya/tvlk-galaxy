package com.devin.galaxy.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CurrencyMaster {

  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  private static final String PATH = "/currency/daily-rates.csv";
  private String[] headers = {
    "currency-from",
    "currency-to",
    "date",
    "rate"
  };

  private Map<String, Double> ratesData;

  public CurrencyMaster() {
    ratesData = load();
  }

  public Map<String, Double> load() {
    try {
      Reader in = new BufferedReader(new InputStreamReader(
        this.getClass().getResourceAsStream(PATH)));
      Iterable<CSVRecord> records = CSVFormat.DEFAULT
        .withHeader(headers)
        .withFirstRecordAsHeader()
        .parse(in);

      Map<String, Double> result = new HashMap<>();
      int count = 0;
      for (CSVRecord record : records) {
        String currencyFrom = record.get("currency-from");
        String currencyTo = record.get("currency-to");
        Date date = new Date(record.get("date"));
        Double rate = Double.valueOf(record.get("rate"));

        result.put(hash(currencyFrom, currencyTo, date), rate);
        count++;
      }
      System.out.println("[CURRENCY_MASTER] successfully loaded " + count + " currency data");
      return result;
    } catch (IOException e) {
      System.out.println("[CURRENCY_MASTER] error when loading currency data");
      return Collections.emptyMap();
    }
  }

  public Double find(String from, String to, Date date) {
    String hash = hash(from, to, date);
    return ratesData.get(hash);
  }

  public String hash(String from, String to, Date date) {
    return String.join("#", from, to, formatter.format(date));
  }

}
