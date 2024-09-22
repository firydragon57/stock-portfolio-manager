package stocks.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stock which has a name and list of dates that it exists and a corresponding
 * list of closing prices on those dates.
 */
public class Stock {
  private final String name;
  private final List<Date> timestamp;
  private final List<Double> close;

  /**
   * Creates a stock.
   *
   * @param name      of the stock.
   * @param timestamp list of the times of the stock.
   * @param open      list of opening price of the stock.
   * @param high      list of high price of the stock.
   * @param low       list of low price of the stock.
   * @param close     list of closing price of the stock.
   * @param volume    list of the volume of the stock.
   */
  public Stock(String name, List<Date> timestamp, List<Double> open,
               List<Double> high, List<Double> low, List<Double> close,
               List<Integer> volume) {
    this.name = name;
    this.timestamp = timestamp;
    this.close = close;
  }

  /**
   * Creates a stock object with given paramaters.
   * @param name of the stock.
   * @param timestamp list of timestamps for the stock.
   * @param close list of closing prices for the stock.
   */
  public Stock(String name, ArrayList<Date> timestamp, ArrayList<Double> close) {
    this.name = name;
    this.timestamp = timestamp;
    this.close = close;
  }

  /**
   * Gets the name of the stock.
   *
   * @return the name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the value of the stock at a given date.
   *
   * @param date to be found.
   * @return the closing price of the stock at the date.
   */
  public double valueOf(Date date) {
    int index = -1;
    for (int i = 0; i < timestamp.size(); i++) {
      if (timestamp.get(i).equals(date)) {
        index = i;
      }
    }
    if (index == -1) {
      throw new IllegalArgumentException("Dates not found");
    }
    return this.close.get(index);
  }

  /**
   * Gets the date at the given index.
   *
   * @param index at which to find the date.
   * @return a date at the index.
   */
  public Date getDate(int index) {
    if (index < 0 || index >= timestamp.size()) {
      throw new IllegalArgumentException("Index out of bounds");
    } else {
      return this.timestamp.get(index);
    }
  }

  /**
   * Gets the closing value on a given date.
   *
   * @param date to find the closing value.
   * @return the closing value on date.
   */
  public double getClose(Date date) {
    int index = this.findIndex(date);
    return this.close.get(index);
  }

  /**
   * Finds the index of a given date.
   *
   * @param date of which the index is found.
   * @return the index as an integer.
   */
  public int findIndex(Date date) {
    for (int i = 0; i < timestamp.size(); i++) {
      if (timestamp.get(i).equals(date)) {
        return i;
      }
    }
    throw new IllegalArgumentException("Date not in timestamp.");
  }

  /**
   * Finds the length of the timestamp list.
   *
   * @return the length.
   */
  public int getTimeStampLength() {
    return timestamp.size();
  }

  /**
   * Checks if the date is found in the list of timestamps.
   *
   * @param date to be found.
   */
  public void validDate(Date date) {
    if (!timestamp.contains(date)) {
      throw new IllegalArgumentException("Date not in timestamp.");
    }
  }

  /**
   * Remove all data that occurs before the buy date.
   *
   * @param date of purchase.
   * @return a new stock with only the necessary data.
   */
  public Stock removeBefore(Date date) {
    ArrayList<Date> newTime = new ArrayList<Date>();
    ArrayList<Double> newClose = new ArrayList<Double>();
    int index = 0;
    Date curr = timestamp.get(0);
    while (curr.isAfter(date) && index < timestamp.size() - 1) {
      newTime.add(timestamp.get(index));
      newClose.add(close.get(index));
      index++;
      curr = timestamp.get(index);
    }
    if (curr.toString().equals(date.toString())) {
      newTime.add(timestamp.get(index));
      newClose.add(close.get(index));
    }
    return new Stock(name, newTime, newClose);

  }



}
