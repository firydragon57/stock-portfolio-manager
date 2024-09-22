package stocks.model;

import java.util.List;

/**
 * Represents the interface IModel.
 */
public interface IModel {

  /**
   * Calculates the return on a stock from the given dates.
   *
   * @param name of the stock.
   * @param from date.
   * @param to   date.
   * @return the cost at the to date minus the cost at the from date.
   */
  double stockReturn(String name, Date from, Date to);

  /**
   * Calculates the average of the given stock from xDays before the start date.
   *
   * @param name  of the stock.
   * @param start date.
   * @param xDays back to calculate.
   * @return the average cost over that many days.
   */
  double movingAverage(String name, Date start, int xDays);

  /**
   * Returns the days which are above the moving average over the given period of time.
   *
   * @param name   of the stock.
   * @param from   start date.
   * @param to     end date.
   * @param xValue number of days to calculate the moving average.
   * @return a list of dates whihc are the dates above the moving average.
   */
  List<Date> crossOver(String name, Date from, Date to, int xValue);

  /**
   * Creates a portfolio which a user can input stocks.
   *
   * @param name of the portfolio.
   */
  void createPortfolio(String name);

  /**
   * Adds a stock to the given portfolio.
   *
   * @param portName  of the portfolio.
   * @param stockName of the stocks.
   * @param numShares of the stock.
   * @param date of the purchase.
   */
  public void purchaseStock(String portName, String stockName, double numShares, Date date);

  /**
   * Sells a stock from the given portfolio.
   *
   * @param portName  of the portfolio.
   * @param stockName of the stocks.
   * @param numShares of the stock.
   * @param date of the sale.
   */
  public void sellStock(String portName, String stockName, double numShares, Date date);


  /**
   * Finds the value for a given portfolio. If the stock at a specific date doesn't exist, then
   * the most recent date from the given date is used instead.
   *
   * @param portName of the portfolio.
   * @param date     to find the value.
   * @return the total value of the portfolio.
   */
  public double findValue(String portName, Date date);

  /**
   * Gets the size of portfolios.
   *
   * @return the size of the list.
   */
  public int getSizePort();

  /**
   * Gets the portfolio in the model with the given name.
   *
   * @param portName The name of the portfolio
   * @return The portfolio with the given name
   */
  public Portfolio getPort(String portName);

  /**
   * Finds the composition of a given portfolio.
   *
   * @param portName name of the portfolio.
   * @param date of the composition to be found.
   * @return a string containing how many shares of each stock are in a given portfolio.
   */
  public String composition(String portName, Date date);

  /**
   * Finds the distribution of a given portfolio.
   *
    * @param portName name of the portfolio.
   * @param date of the distribution to be found.
   * @return a string containing how much value each stock has in the given portfolio.
   */
  public String findDistribution(String portName, Date date);

  /**
   * Balances a portfolio to a given set of percentages.
   *
   * @param portName name of the portfolio.
   * @param date of the portfolio to be balanced.
   * @param percentages totalling 100 1 for each stock in the portfolio representing
   *                    how much percentage the stock should take up after the balancing.
   */
  public void rebalancePortfolio(String portName, Date date, List<Double> percentages);

  /**
   * Displays the performance of a portfolio over two given dates in the form of a bar chart.
   *
   * @param portName of the portfolio.
   * @param fromDate first date of the chart.
   * @param toDate last date of the chart.
   * @return a string that when printed displays a chart which is the performance of the portfolio.
   */
  public String displayPerformance(String portName, Date fromDate, Date toDate);

  /**
   * Adds a portfolio to the list of current portfolios.
   *
   * @param port portfolio to be added.
   */
  public void addPort(Portfolio port);

}
