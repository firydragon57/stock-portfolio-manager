package stocks.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model for the stock program.
 */
public class Model implements IModel {
  private final List<Portfolio> portfolios;
  private final List<Stock> stocks;

  /**
   * Creates a model and sets all values to their default.
   * Adds 3 stocks: Google, amazon, and tesla.
   */
  public Model() {
    this.portfolios = new ArrayList<Portfolio>();
    this.stocks = new ArrayList<Stock>();
    this.stocks.add(StockBuilder.makeStock("GOOG"));
    this.stocks.add(StockBuilder.makeStock("AMZN"));
    this.stocks.add(StockBuilder.makeStock("TSLA"));
  }

  @Override
  public double stockReturn(String name, Date from, Date to) {
    Stock stock = this.findStock(name);
    return stock.valueOf(to) - stock.valueOf(from);
  }

  @Override
  public double movingAverage(String name, Date start, int xDays) {
    if (xDays < 0) {
      throw new IllegalArgumentException("xDays cannot be negative");
    }
    Stock stock = this.findStock(name);
    double average = 0.0;
    int index = stock.findIndex(start);
    if (index + xDays > stock.getTimeStampLength()) {
      throw new IllegalArgumentException("Not enough days");
    }
    for (int i = index; i < index + xDays; i++) {
      average += stock.valueOf(stock.getDate(i));
    }

    return average / xDays;
  }

  @Override
  public List<Date> crossOver(String name, Date from, Date to, int xValue) {


    if (from.isAfter(to)) {
      throw new IllegalArgumentException("From is greater than to.");
    }
    if (xValue < 0) {
      throw new IllegalArgumentException("xValue is less than 0");
    }
    ArrayList<Date> dates = new ArrayList<Date>();
    Stock stock = this.findStock(name);
    Date d = from;
    stock.validDate(from);
    stock.validDate(to);
    Date end = stock.getDate(stock.findIndex(to) - 1);
    while (!d.toString().equals(end.toString())) {
      double movingAverage = this.movingAverage(name, d, xValue);
      double closing = stock.valueOf(d);
      if (closing > movingAverage) {
        dates.add(d);
      }
      d = stock.getDate(stock.findIndex(d) - 1);
    }
    return dates;
  }

  @Override
  public void createPortfolio(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    portfolios.add(new Portfolio(name));
  }

  @Override
  public double findValue(String portName, Date date) {
    for (Portfolio p : this.portfolios) {
      if (p.getName().equals(portName)) {
        return p.findValueHelper(date);
      }
    }
    throw new IllegalArgumentException("Cannot find portfolio");
  }

  @Override
  public void purchaseStock(String portName, String stockName, double numShares, Date date) {
    if (numShares <= 0) {
      throw new IllegalArgumentException("Number of shares must be greater than 0");
    }
    if (this.portfolios.isEmpty()) {
      throw new IllegalArgumentException("There are currently no portfolios");
    }
    Stock stock = this.findStock(stockName);
    Stock currStock;
    Portfolio tempPort = this.getPort(portName);
    tempPort.checkMostRecent(stockName, date);

    try {
      currStock = tempPort.findStock(stockName);
    } catch (IllegalArgumentException e) {
      tempPort.purchaseShares(stock.removeBefore(date), numShares, date);
      return;
    }

    tempPort.purchaseShares(currStock, numShares, date);
  }

  @Override
  public void sellStock(String portName, String stockName, double numShares, Date date) {
    Stock stock;

    Portfolio p = this.getPort(portName);
    stock = p.findStock(stockName);
    if (numShares <= 0) {
      throw new IllegalArgumentException("Number of shares must be greater than 0");
    }
    if (this.portfolios.isEmpty()) {
      throw new IllegalArgumentException("There are currently no portfolios");
    }
    p.checkMostRecent(stockName, date);
    p.sellShares(stock, numShares, date);


  }

  private Stock findStock(String name) {
    for (Stock s : stocks) {
      if (name.equals(s.getName())) {
        return s;
      }
    }
    return StockBuilder.makeStock(name);
  }


  @Override
  public int getSizePort() {
    return this.portfolios.size();
  }

  @Override
  public Portfolio getPort(String portName) {
    for (Portfolio p : this.portfolios) {
      if (p.getName().equals(portName)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Portfolio doesn't exist");
  }

  @Override
  public String composition(String portName, Date date) {
    Portfolio tempPort = this.getPort(portName);
    return tempPort.findComposition(date);
  }

  @Override
  public String findDistribution(String portName, Date date) {
    Portfolio tempPort = this.getPort(portName);
    return tempPort.findDistribution(date);
  }

  @Override
  public void rebalancePortfolio(String portName, Date date, List<Double> percentages) {
    Portfolio tempPort = this.getPort(portName);
    tempPort.rebalancePortfolio(date, percentages);
  }

  @Override
  public String displayPerformance(String portName, Date fromDate, Date toDate) {
    if (fromDate.isAfter(toDate)) {
      throw new IllegalArgumentException("From date can't be before to date");
    }
    StringBuilder chart = new StringBuilder();
    chart.append("Performance of ").append(portName).append(" from ").append(fromDate.toString())
            .append(" to ").append(toDate.toString()).append("\n").append("\n");
    Portfolio port = this.getPort(portName);
    int interval = fromDate.findInterval(toDate);
    double scale = this.scaleValue(port, fromDate, toDate);
    double value;
    while (!fromDate.isAfter(toDate)) {
      value = port.findValueHelper(fromDate);
      chart.append(fromDate.toString()).append(": ");
      for (int i = 0; i < value / scale; i++) {
        chart.append("*");
      }
      chart.append("\n");
      fromDate.advance(interval);
    }
    chart.append(toDate.toString()).append(": ");
    value = port.findValueHelper(toDate);
    for (int i = 0; i < value / scale; i++) {
      chart.append("*");
    }
    chart.append("\n");
    chart.append("\nScale: * = ").append(scale).append("\n");
    return chart.toString();
  }

  private double scaleValue(Portfolio port, Date from, Date to) {
    double averageValue = 0.0;
    int numAverages = 0;
    int interval = from.findInterval(to);
    Date fromDate = new Date(from.getDay(), from.getMonth(), from.getYear());
    while (!fromDate.isAfter(to)) {
      averageValue += port.findValueHelper(from);
      numAverages++;
      fromDate.advance(interval);
    }
    if (numAverages == 0) {
      numAverages += 1;
    }
    averageValue /= numAverages;

    int scale = 1;
    while (averageValue / 10 > 10) {
      scale *= 10;
      averageValue /= 10;
    }
    return scale;
  }

  /**
   * Adds the given portfolio to the list of portfolios.
   * @param port The portfolio being added.
   */
  public void addPort(Portfolio port) {
    this.portfolios.add(port);
  }




}
