package stocks.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a portfolio which a user can add and sell stocks to and analyze in multiple ways.
 */
public class Portfolio {
  private final String name;
  private final Map<Stock, Map<Date, Double>> stockMap;

  /**
   * Creates a portfolio at the given date with the given name.
   *
   * @param name of the portfolio.
   */
  public Portfolio(String name) {
    this.name = name;
    this.stockMap = new LinkedHashMap<>();
  }

  /**
   * Creates a portfolio with a set stockMap.
   *
   * @param name of the portfolio.
   * @param stockMap to be used.
   */
  public Portfolio(String name, Map<Stock, Map<Date, Double>> stockMap) {
    this.name = name;
    this.stockMap = stockMap;
  }

  /**
   * Helper method to find the value.
   *
   * @return the total value of the portfolio.
   */
  public double findValueHelper(Date date) {
    double total = 0.0;
    boolean foundDate;
    Date tempDate = new Date(date.getDay(), date.getMonth(), date.getYear());
    for (Stock s : this.stockMap.keySet()) {
      foundDate = false;
      while (!foundDate) {
        try {
          total += s.valueOf(tempDate) * stockMap.get(s).get(tempDate);
          foundDate = true;
        } catch (IllegalArgumentException e) {
          tempDate.advance(1);
        }
      }
    }
    return total;
  }

  /**
   * Gets the name of the portfolio.
   *
   * @return the name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param s the stock to be added.
   * @param numShares num of shares to be added.
   * @param date date to be purchased.
   */
  public void purchaseShares(Stock s, Double numShares, Date date) {
    Map<Date, Double> dateShares = new LinkedHashMap<>();
    Map<Date, Double> tempMap = new LinkedHashMap<>();
    if (stockMap.isEmpty()) {
      for (int i = 0; i <  s.getTimeStampLength(); i++) {
        dateShares.put(s.getDate(i), numShares);
      }
      this.stockMap.put(s, dateShares);
      return;
    }
    if (this.stockMap.containsKey(s)) {
      tempMap = this.stockMap.get(s);
      Date recentDate;
      if (tempMap.containsKey(date)) {
        recentDate = date;
      }
      else {
        recentDate = this.getClosest(s, date);
      }

      for (Map.Entry<Date, Double> entry : tempMap.entrySet()) {
        if (!entry.getKey().toString().equals(recentDate.toString())) {
          tempMap.replace(entry.getKey(), entry.getValue() + numShares);
          continue;
        }
        tempMap.replace(entry.getKey(), entry.getValue() + numShares);
        return;
      }
    } else {
      for (int i = 0; i < s.getTimeStampLength(); i++) {
        tempMap.put(s.getDate(i), numShares);
      }
    }
    this.stockMap.put(s, tempMap);
  }

  /**
   * Sells shares from the given portfolio.
   *
   * @param s stock to be sold.
   * @param numShares of the stock to be sold.
   * @param date to sell the shares.
   */
  public void sellShares(Stock s, double numShares, Date date) {
    Map<Date, Double> tempMap = this.stockMap.get(s);
    for (Map.Entry<Date, Double> entry : tempMap.entrySet()) {
      if (!entry.getKey().toString().equals(date.toString())) {
        if (entry.getValue() - numShares < 0) {
          throw new IllegalArgumentException("Can't sell more shares than you own");
        }
        tempMap.replace(entry.getKey(), entry.getValue() - numShares);
      } else {
        tempMap.replace(entry.getKey(), entry.getValue() - numShares);
        break;
      }
    }
    this.stockMap.replace(s, tempMap);
  }

  /**
   * Finds a stock in the portfolio matching the given ticker or throws an exception.
   *
   * @param ticker of the stock to be found.
   * @return the stock from the portfolio or throw an exception if cannot find the stock.
   */
  public Stock findStock(String ticker) {
    for (Stock s : this.stockMap.keySet()) {
      if (ticker.equals(s.getName())) {
        return s;
      }
    }
    throw new IllegalArgumentException("Stock not found.");
  }

  /**
   * Finds the composition of this portfolio.
   *
   * @param date on which to find the composition.
   * @return a string containing the names and how many shares of all stocks in this portfolio.
   */
  public String findComposition(Date date) {
    StringBuilder result = new StringBuilder();
    result.append(this.name).append("\n");
    Date currDate;
    for (Stock s : this.stockMap.keySet()) {
      currDate = this.getMostRecent(s);
      try {
        this.checkMostRecent(s.getName(), date);
      } catch (IllegalArgumentException e) {
        continue;
      }
      result.append(s.getName()).append(" - ");
      result.append(this.stockMap.get(s).get(currDate)).append(" share(s)").append("\n");
    }
    return result.toString();
  }

  /**
   * Finds the distribution of this portfolio on a given date.
   *
   * @param date on which to find the distribution.
   * @return a string composed of all Stock names and their values within the portfolio.
   */
  public String findDistribution(Date date) {
    StringBuilder result = new StringBuilder();
    result.append(this.name).append("\n");
    for (Stock s : this.stockMap.keySet()) {
      double stockValue;
      try {
        stockValue = s.valueOf(date) * this.stockMap.get(s).get(date);
        result.append(s.getName()).append(" - $");
        result.append(stockValue).append("\n");
      }
      catch (IllegalArgumentException e) {
        // Continue to the next stock since at the current date, the stock is not in the portfolio
      }
    }
    return result.toString();
  }

  /**
   * Gets the size of the stock map.
   *
   * @return the size of the stock map.
   */
  public int getStockSize() {
    return this.stockMap.size();
  }

  /**
   * Gets the name of a stock at a given index.
   *
   * @param i index to find the stock.
   * @return the name of the stock at index i.
   */
  public String getStockName(int i) {
    int count = 0;
    for (Stock s : this.stockMap.keySet()) {
      if (count == i) {
        return s.getName();
      }
      count++;
    }
    throw new IllegalArgumentException("Stock not found.");
  }

  /**
   * Returns the share map of the given stock.
   *
   * @param ticker for the stock.
   * @return the whole share map of the stock.
   */
  public Map<Date, Double> getShareMap(String ticker) {
    for (Stock s : this.stockMap.keySet()) {
      if (ticker.equals(s.getName())) {
        return this.stockMap.get(s);
      }
    }
    throw new IllegalArgumentException("Stock not found.");
  }

  /**
   * Rebalances the portfolio to the given percentages on the given date.
   *
   * @param date to rebalance the portfolio.
   * @param percentages to set the percentage of each stock in the portfolio (adds to 100).
   */
  public void rebalancePortfolio(Date date, List<Double> percentages) {
    double total = 0;
    for (Double d : percentages) {
      total += d;
    }
    if (total != 100) {
      throw new IllegalArgumentException("Percentage must add to 100.");
    }
    double value = this.findValueHelper(date);
    int count = 0;
    double currPercent = 0;
    double desiredShares;
    for (Stock s : this.stockMap.keySet()) {
      currPercent = s.valueOf(date) * this.stockMap.get(s).get(date);
      currPercent /= value;
      currPercent *= 100;
      desiredShares = value * (percentages.get(count) / 100) / s.valueOf(date);
      if (currPercent > percentages.get(count)) {
        this.sellShares(s, stockMap.get(s).get(date) - desiredShares, date);
      } else if (currPercent < percentages.get(count)) {
        this.purchaseShares(s, desiredShares - stockMap.get(s).get(date), date);
      }
      count++;
    }
  }

  /**
   * Checks if a date is after the most recent purchase/sale, and if not throw an exception.
   *
   * @param stockName to be checked.
   * @param date of the desired purchase/sale.
   */
  public void checkMostRecent(String stockName, Date date) {
    try {
      findStock(stockName);
    } catch (IllegalArgumentException e) {
      return;
    }
    Date mostRecent = getMostRecent(findStock(stockName));
    if (!date.isAfter(mostRecent)) {
      throw new IllegalArgumentException("Date must be after the most recent purchase/sale.");
    }

  }

  private Date getMostRecent(Stock stock) {
    Map<Date, Double> dateShares = this.stockMap.get(stock);
    double shares = dateShares.get(stock.getDate(0));
    Date dateBefore = stock.getDate(0);
    for (Date date : dateShares.keySet()) {
      if (shares != dateShares.get(date)) {
        return dateBefore;
      }
      dateBefore = date;
    }
    dateBefore = stock.getDate(stock.getTimeStampLength() - 1);
    return dateBefore;
  }

  private Date getClosest(Stock stock, Date date) {
    Map<Date, Double> dateShares = this.stockMap.get(stock);
    Date dateBefore = stock.getDate(stock.getTimeStampLength() - 1);
    for (int i = stock.findIndex(dateBefore); i > 0; i--) {
      if (!date.isAfter(stock.getDate(i))) {
        return dateBefore;
      }
    }
    return dateBefore;
  }
}
