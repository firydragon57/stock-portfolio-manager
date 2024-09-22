package stocks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.controller.AlphaVantageStreamReader;

/**
 * Represents a stock builder which creates stocks based on the alpha vantage.
 */
public class StockBuilder {

  /**
   * Creates a stock from the database.
   *
   * @param ticker of the stock.
   * @return a stock object.
   */
  public static Stock makeStock(String ticker) {
    Readable readable = new AlphaVantageStreamReader(ticker).getReadable();
    Scanner scanner = new Scanner(readable);
    List<Date> timestamps = new ArrayList<Date>();
    List<Double> open = new ArrayList<Double>();
    List<Double> high = new ArrayList<Double>();
    List<Double> low = new ArrayList<Double>();
    List<Double> close = new ArrayList<Double>();
    List<Integer> volume = new ArrayList<Integer>();

    scanner.nextLine();
    while (scanner.hasNext()) {
      String line = scanner.next();
      String[] values = line.split(",");
      if (values[0].equals("\"Error")) {
        throw new IllegalArgumentException("Stock not found");
      }
      timestamps.add(new Date(Integer.parseInt(values[0].substring(8)),
              Integer.parseInt(values[0].substring(5, 7)),
              Integer.parseInt(values[0].substring(0, 4))));
      open.add(Double.parseDouble(values[1]));
      high.add(Double.parseDouble(values[2]));
      low.add(Double.parseDouble(values[3]));
      close.add(Double.parseDouble(values[4]));
      volume.add(Integer.parseInt(values[5]));
    }
    return new Stock(ticker, timestamps, open, high, low, close, volume);
  }
}
