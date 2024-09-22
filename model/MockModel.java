package stocks.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a mock model to be used for testing.
 */
public class MockModel implements IModel {

  private final StringBuilder log;

  /**
   * Creates the mock model.
   *
   * @param log string builder to contain outputs.
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public double stockReturn(String name, Date from, Date to) {
    log.append(String.format("Name = " + name + ", From = " + from.toString()
            + ", To = " + to.toString() + "\n", name, from, to));
    return 0;
  }

  @Override
  public double movingAverage(String name, Date start, int xDays) {
    log.append("Name = " + name + ", Date = " + start.toString() + ", xDays = "
            + xDays + "\n");
    return 0;
  }

  @Override
  public List<Date> crossOver(String name, Date from, Date to, int xValue) {
    log.append("Name = ").append(name).append(", From = ")
            .append(from.toString()).append(", To = ")
            .append(to.toString()).append(", xValue = ").append(xValue).append("\n");
    return List.of();
  }

  @Override
  public void createPortfolio(String name) {
    log.append(String.format("Name = " + name + "\n", name));
  }

  @Override
  public void purchaseStock(String portName, String stockName, double numShares, Date date) {
    log.append("portName = ").append(portName)
            .append(", stockName = ").append(stockName)
            .append(", numShares = ").append(numShares)
            .append(", date = ").append(date.toString()).append("\n");
  }

  @Override
  public void sellStock(String portName, String stockName, double numShares, Date date) {
    log.append("portName = ").append(portName)
            .append(", stockName = ").append(stockName)
            .append(", numShares = ").append(numShares)
            .append(", date = ").append(date.toString()).append("\n");
  }

  @Override
  public double findValue(String portName, Date date) {
    log.append("portName = ").append(portName).append(", date = ").append(date.toString())
            .append("\n");
    return 0.0;
  }

  @Override
  public int getSizePort() {
    return 0;
  }

  @Override
  public Portfolio getPort(String portName) {
    return null;
  }

  @Override
  public String composition(String portName, Date date) {
    log.append("portName = ").append(portName).append(", date = ")
            .append(date.toString()).append("\n");
    return "";
  }

  @Override
  public String findDistribution(String portName, Date date) {
    log.append("portName = ").append(portName).append(", date = ")
            .append(date.toString()).append("\n");
    return "";
  }

  @Override
  public void rebalancePortfolio(String portName, Date date, List<Double> percentages) {
    log.append("portName = ").append(portName).append(", date = ").append(date.toString())
            .append(", percentages = ").append(percentages.toString()).append("\n");
  }

  @Override
  public String displayPerformance(String portName, Date fromDate, Date toDate) {
    log.append("portName = ").append(portName).append(", fromDate = ")
            .append(fromDate.toString()).append(", toDate = ")
            .append(toDate.toString()).append("\n");

    return "";
  }

  @Override
  public void addPort(Portfolio port) {
    log.append("port = ").append(port.getName()).append("\n");
  }


}
