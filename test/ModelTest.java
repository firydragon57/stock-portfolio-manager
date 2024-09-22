import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import stocks.model.StockBuilder;
import stocks.model.Date;
import stocks.model.IModel;
import stocks.model.Model;
import stocks.model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This is the test class for the Model.
 */
public class ModelTest {
  private IModel model;
  private Date to;
  private Date from;
  private Date firstDate;

  @Before
  public void setUp() {
    model = new Model();
    from = new Date(28, 5, 2024);
    to = new Date(29, 5, 2024);
    firstDate = new Date(27, 3, 2014);
  }


  @Test
  public void stockReturn() {

    assertEquals(-.62, model.stockReturn("GOOG", from, to), .01);
    assertEquals(0.29, model.stockReturn("AAPL", from, to), .01);
    try {
      assertEquals(0.0, model.stockReturn("PQOL", from, to), .01);
    } catch (IllegalArgumentException e) {
      // Do nothing but catch the exception
    }
    assertEquals(88.60, model.stockReturn("GOOG",
            new Date(9, 1, 2023), to), .01);

    try {
      assertEquals(0.0, model.stockReturn("GOOG",
              new Date(27, 5, 2024), to), .01);
    } catch (IllegalArgumentException e) {
      assertEquals("Dates not found", e.getMessage());
    }

  }

  @Test
  public void movingAverage() {
    assertEquals(177.39, model.movingAverage("GOOG", from, 5), .01);
    assertEquals(189.43, model.movingAverage("AAPL", from, 4), .01);
    assertEquals(176.47, model.movingAverage("GOOG", from, 3), .01);
    assertEquals(176.46, model.movingAverage("GOOG",
            new Date(24, 5, 2024), 3), .01);
    assertEquals(177.53, model.movingAverage("GOOG",
            new Date(23, 5, 2024), 3), .01);
    assertEquals(176.96, model.movingAverage("GOOG", to, 5), .01);

    try {
      assertEquals(0.0, model.movingAverage("PQOL",
              new Date(27, 5, 2024), 5), .01);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found", e.getMessage());
    }

    try {
      assertEquals(0.0, model.movingAverage("GOOG",
              new Date(27, 5, 2024), 5), .01);
    } catch (IllegalArgumentException e) {
      assertEquals("Date not in timestamp.", e.getMessage());
    }
  }

  @Test
  public void crossOver() {
    List<Date> listDates = new ArrayList<Date>();
    listDates.add(from);
    assertEquals(listDates.toString(), model.crossOver(
            "GOOG", new Date(23, 5, 2024), from, 3).toString());
    listDates = new ArrayList<Date>();
    listDates.add(new Date(26, 4, 2024));
    listDates.add(new Date(29, 4, 2024));
    listDates.add(new Date(2, 5, 2024));
    listDates.add(new Date(3, 5, 2024));
    listDates.add(new Date(6, 5, 2024));
    listDates.add(new Date(7, 5, 2024));
    listDates.add(new Date(14, 5, 2024));
    listDates.add(new Date(15, 5, 2024));
    listDates.add(new Date(16, 5, 2024));
    listDates.add(new Date(17, 5, 2024));
    listDates.add(new Date(20, 5, 2024));
    listDates.add(new Date(21, 5, 2024));
    listDates.add(new Date(28, 5, 2024));
    assertEquals(listDates.toString(), model.crossOver(
            "GOOG", new Date(25, 4, 2024), from, 3).toString());

    try {
      assertEquals(listDates.toString(), model.crossOver(
              "GOOG", from, new Date(23, 5, 2024), 3).toString());
    } catch (IllegalArgumentException e) {
      assertEquals("From is greater than to.", e.getMessage());
    }

    listDates = new ArrayList<Date>();
    listDates.add(new Date(24, 5, 2024));
    listDates.add(new Date(28, 5, 2024));

    assertEquals(listDates.toString(), model.crossOver(
            "AAPL", new Date(23, 5, 2024), from, 3).toString());

    try {
      assertEquals(listDates.toString(), model.crossOver(
              "PQOL", new Date(23, 5, 2024), from, 3).toString());
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found", e.getMessage());
    }

  }

  @Test
  public void createPortfolio() {
    assertEquals(0, model.getSizePort());
    model.createPortfolio("TestPort");
    assertEquals(1, model.getSizePort());
    model.createPortfolio("Cool Portfolio");
    assertEquals(2, this.model.getSizePort());
    model.createPortfolio("Bad Portfolio");
    assertEquals(3, this.model.getSizePort());
  }

  @Test
  public void purchaseStockTest() {
    // Purchasing a stock with no portfolio
    try {
      model.purchaseStock("TestPort", "AMZN", 1, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("There are currently no portfolios", e.getMessage());
    }
    model.createPortfolio("TestPort");
    model.purchaseStock("TestPort", "GOOG", 50, firstDate);
    assertEquals((Double) 50.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(firstDate));
    model.purchaseStock("TestPort", "AAPL", 13, firstDate);
    assertEquals((Double) 13.0, model.getPort("TestPort").getShareMap("AAPL")
            .get(firstDate));
    model.purchaseStock("TestPort", "GOOG", 1,
            new Date(2, 4, 2014));
    assertEquals((Double) 50.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(firstDate));
    assertEquals((Double) 51.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(2, 4, 2014)));
    try {
      model.purchaseStock("TestPort", "GOOG", -1, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Number of shares must be greater than 0", e.getMessage());
    }
    assertEquals(2, model.getPort("TestPort").getStockSize());

    // Purchasing a stock that doesn't exist
    try {
      model.purchaseStock("TestPort", "PQOL", 1, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found", e.getMessage());
    }
    assertEquals(2, model.getPort("TestPort").getStockSize());
    model.purchaseStock("TestPort", "GOOG", 10,
            new Date(30, 5, 2024));
    assertEquals((Double) 61.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(3, 6, 2024)));
    assertEquals((Double) 51.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(29, 5, 2024)));

    this.model.purchaseStock("TestPort", "AMZN", 50,
            new Date(24, 5, 2024));
    assertEquals((Double) 50.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(24, 5, 2024)));
    this.model.purchaseStock("TestPort", "AMZN", 10,
            new Date(28, 5, 2024));
    assertEquals((Double) 60.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(28, 5, 2024)));
    try {
      this.model.purchaseStock("TestPort", "AMZN", 10,
              new Date(23, 5, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("Date must be after the most recent purchase/sale.", e.getMessage());
    }


    try {
      this.model.purchaseStock("TestPort", "AMZN", 10,
              new Date(25, 5, 2024));
      fail("This should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Date must be after the most recent purchase/sale.", e.getMessage());
    }
  }

  @Test
  public void sellStockInvalidTest() {
    // Selling with no portfolio
    try {
      model.sellStock("TestPort", "AMZN", 1, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio doesn't exist", e.getMessage());
    }
    // Selling with no stock
    model.createPortfolio("TestPort");
    try {
      model.sellStock("TestPort", "GOOG", 50, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found.", e.getMessage());
    }
    // Selling before first buy date
    this.model.purchaseStock("TestPort", "GOOG", 10, new Date(1, 5, 2024));
    try {
      this.model.sellStock("TestPort", "GOOG", 10, new Date(30, 4, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("Date must be after the most recent purchase/sale.", e.getMessage());
    }
    // Selling before most recent buy date
    this.model.purchaseStock("TestPort", "GOOG", 10, new Date(4, 5, 2024));
    try {
      this.model.sellStock("TestPort", "GOOG", 5, new Date(2, 5, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("Date must be after the most recent purchase/sale.", e.getMessage());
    }

    // Selling on the same day
    try {
      this.model.sellStock("TestPort", "GOOG", 10, new Date(4, 5, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("Date must be after the most recent purchase/sale.", e.getMessage());
    }
  }

  @Test
  public void sellStockValidTest() {
    this.model.createPortfolio("TestPort");
    model.purchaseStock("TestPort", "GOOG", 50,
            new Date(3, 5, 2024));
    assertEquals((Double) 50.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(3, 5, 2024)));
    model.sellStock("TestPort", "GOOG", 20,
            new Date(13, 5, 2024));
    assertEquals((Double) 30.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(13,5,2024)));
    assertEquals((Double) 30.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(14, 5, 2024)));
    assertEquals((Double) 50.0, model.getPort("TestPort").getShareMap("GOOG")
            .get(new Date(10, 5, 2024)));
    this.model.purchaseStock("TestPort", "AMZN", 10,
            new Date(3, 5, 2024));
    assertEquals((Double) 10.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(3, 5,
            2024)));
    model.sellStock("TestPort", "AMZN", 5,
            new Date(13,5,2024));
    assertEquals((Double) 5.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(13, 5, 2024)));
    assertEquals((Double) 5.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(14, 5, 2024)));
    assertEquals((Double) 10.0, model.getPort("TestPort").getShareMap("AMZN")
            .get(new Date(10, 5, 2024)));

    this.model.createPortfolio("good");
    this.model.purchaseStock("good", "GOOG", 50,
            new Date(1, 5, 2024));
    this.model.purchaseStock("good", "GOOG", 50,
            new Date(5, 5, 2024));
    this.model.sellStock("good", "GOOG", 25,
            new Date(10, 5, 2024));
    assertEquals((Double) 75.0, model.getPort("good").getShareMap("GOOG")
            .get(new Date(10, 5, 2024)));
  }


  @Test
  // TODO: Add more tests here with a different purchase date. Also add some sell stock methods too.
  public void findValueAllDatesExist() {
    Stock google = StockBuilder.makeStock("GOOG");
    Stock apple = StockBuilder.makeStock("AAPL");
    Stock amazon = StockBuilder.makeStock("AMZN");
    assertEquals(177.4, google.valueOf(new Date(29, 5, 2024)), 0.01);
    assertEquals(190.29, apple.valueOf(new Date(29, 5, 2024)), 0.01);
    assertEquals(182.02, amazon.valueOf(new Date(29, 5, 2024)), 0.01);
    model.createPortfolio("TestPort");
    model.purchaseStock("TestPort", "GOOG", 1, firstDate);
    model.purchaseStock("TestPort", "AAPL", 1, firstDate);
    model.purchaseStock("TestPort", "AMZN", 1, firstDate);
    model.createPortfolio("TestPort2");
    model.purchaseStock("TestPort2", "GOOG", 10, firstDate);
    model.purchaseStock("TestPort2", "AAPL", 20, firstDate);
    model.purchaseStock("TestPort2", "AMZN", 50, firstDate);
    try {
      model.purchaseStock("TestPort2", "PQOL", 50, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found", e.getMessage());
    }
    assertEquals(549.71, this.model.findValue("TestPort",
            new Date(29, 5, 2024)), 0.01);
    assertEquals(14680.80, this.model.findValue("TestPort2",
            new Date(29, 5, 2024)), 0.01);
  }

  @Test
  // TODO: Add more tests here with a different purchase date. Also add in some sellStock methods
  //  too
  public void findValueSomeDatesExist() {
    Stock google = StockBuilder.makeStock("GOOG");
    Stock apple = StockBuilder.makeStock("AAPL");
    Stock amazon = StockBuilder.makeStock("AMZN");
    assertEquals(176.33, google.valueOf(new Date(24, 5, 2024)), 0.01);
    assertEquals(189.98, apple.valueOf(new Date(24, 5, 2024)), 0.01);
    assertEquals(180.75, amazon.valueOf(new Date(24, 5, 2024)), 0.01);
    model.createPortfolio("TestPort");
    model.purchaseStock("TestPort", "GOOG", 1, firstDate);
    model.purchaseStock("TestPort", "AAPL", 1, firstDate);
    model.purchaseStock("TestPort", "AMZN", 1, firstDate);
    model.createPortfolio("TestPort2");
    model.purchaseStock("TestPort2", "GOOG", 10, firstDate);
    model.purchaseStock("TestPort2", "AAPL", 20, firstDate);
    model.purchaseStock("TestPort2", "AMZN", 50, firstDate);
    try {
      model.purchaseStock("TestPort2", "PQOL", 50, firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not found", e.getMessage());
    }
    assertEquals(550.16, this.model.findValue("TestPort",
            new Date(27, 5, 2024)), 0.01);
    assertEquals(14687.5, this.model.findValue("TestPort2",
            new Date(27, 5, 2024)), 0.01);
  }

  @Test
  public void rebalancePortfolioTest() {
    try {
      model.rebalancePortfolio("test", firstDate, new ArrayList<Double>());
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio doesn't exist", e.getMessage());
    }

    Date date = new Date(1,4,2024);
    Date date2 = new Date(5,4,2024);
    model.createPortfolio("test");
    model.purchaseStock("test", "GOOG", 10, date);
    model.purchaseStock("test", "AAPL", 20, date);
    model.purchaseStock("test", "TSLA", 30, date);
    model.purchaseStock("test", "AMZN", 40, date);
    assertEquals(17280.80, model.findValue("test",date2), .01);
    assertEquals((Double) 10.0, model.getPort("test").getShareMap("GOOG").get(date));
    assertEquals((Double) 20.0, model.getPort("test").getShareMap("AAPL").get(date));
    assertEquals((Double) 30.0, model.getPort("test").getShareMap("TSLA").get(date));
    assertEquals((Double) 40.0, model.getPort("test").getShareMap("AMZN").get(date));
    try {
      model.rebalancePortfolio("test", date2, new ArrayList<Double>());
    } catch (IllegalArgumentException e) {
      assertEquals("Percentage must add to 100.", e.getMessage());
    }

    List<Double> percentages = new ArrayList<Double>();
    percentages.add(10.0);
    percentages.add(30.0);
    percentages.add(35.5);
    percentages.add(25.0);
    try {
      model.rebalancePortfolio("test", date2, percentages);
    } catch (IllegalArgumentException e) {
      assertEquals("Percentage must add to 100.", e.getMessage());
    }

    percentages.remove(3);
    percentages.add(24.5);
    model.rebalancePortfolio("test", date2, percentages);
    assertEquals(17280.80, model.findValue("test", date2), .01);
    assertEquals((Double) 10.0, model.getPort("test").getShareMap("GOOG").get(date));
    assertEquals((Double) 20.0, model.getPort("test").getShareMap("AAPL").get(date));
    assertEquals((Double) 30.0, model.getPort("test").getShareMap("TSLA").get(date));
    assertEquals((Double) 40.0, model.getPort("test").getShareMap("AMZN").get(date));
    assertEquals((Double) 11.22, model.getPort("test").getShareMap("GOOG").get(date2), .01);
    assertEquals((Double) 30.57, model.getPort("test").getShareMap("AAPL").get(date2), .01);
    assertEquals((Double) 37.20, model.getPort("test").getShareMap("TSLA").get(date2), .01);
    assertEquals((Double) 22.87, model.getPort("test").getShareMap("AMZN").get(date2), .01);

  }

  @Test
  public void compositionTest() {
    // Composition before creating a portfolio
    try {
      this.model.composition("TestPort", firstDate);
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio doesn't exist", e.getMessage());
    }
    this.model.createPortfolio("TestPort");
    assertEquals("TestPort\n", this.model.composition("TestPort", firstDate));
    this.model.purchaseStock("TestPort", "GOOG", 1, new Date(2, 5, 2024));
    this.model.purchaseStock("TestPort", "AAPL", 15, new Date(20, 5, 2024));
    assertEquals("TestPort\nGOOG - 1.0 share(s)\n", this.model.composition("TestPort", new Date(2
            , 5, 2024)));
    assertEquals("TestPort\n", this.model.composition("TestPort", firstDate));
    assertEquals("TestPort\nGOOG - 1.0 share(s)\n", this.model.composition("TestPort", new Date(3
            ,5, 2024)));
    assertEquals("TestPort\nGOOG - 1.0 share(s)\n", this.model.composition("TestPort", new Date(4
            ,5, 2024)));
    assertEquals("TestPort\nGOOG - 1.0 share(s)\nAAPL - 15.0 share(s)\n", this.model.composition(
            "TestPort",
            new Date(20, 5, 2024)));
    assertEquals("TestPort\nGOOG - 1.0 share(s)\nAAPL - 15.0 share(s)\n", this.model.composition(
            "TestPort",
            new Date(21, 5, 2024)));
  }

  @Test
  public void findDistributionTest() {
    try {
      this.model.findDistribution("TestPort", new Date(29, 5, 2024));
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio doesn't exist", e.getMessage());
    }
    this.model.createPortfolio("TestPort");
    this.model.purchaseStock("TestPort", "GOOG", 10, firstDate);
    assertEquals("TestPort\nGOOG - $1774.0\n", this.model.findDistribution("TestPort", new Date(29
            , 5, 2024)));
    this.model.purchaseStock("TestPort", "AAPL", 0.5, new Date(28, 5, 2024));
    assertEquals("TestPort\nGOOG - $1774.0\nAAPL - $95.145\n", this.model.findDistribution(
            "TestPort",
            new Date(29, 5, 2024)));
    assertEquals("TestPort\nGOOG - $1763.3000000000002\n", this.model.findDistribution(
            "TestPort",
            new Date(24, 5, 2024)));
    this.model.sellStock("TestPort", "AAPL", 0.5, new Date(29,5, 2024));
    assertEquals("TestPort\nGOOG - $1774.0\nAAPL - $0.0\n", this.model.findDistribution("TestPort",
            new Date(29, 5, 2024)));
  }

  @Test
  public void testDisplayPerformance() {
    try {
      this.model.displayPerformance("TestPort", new Date(28, 5, 2024), new Date(29, 5, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio doesn't exist", e.getMessage());
    }
    this.model.createPortfolio("TestPort");
    try {
      this.model.displayPerformance("TestPort", new Date(29, 5, 2024), new Date(28, 5, 2024));
    } catch (IllegalArgumentException e) {
      assertEquals("From date can't be before to date", e.getMessage());
    }
    this.model.purchaseStock("TestPort", "GOOG", 10, new Date(1, 5, 2014));
    this.model.sellStock("TestPort", "GOOG", 5, new Date(7, 6, 2015));
    this.model.purchaseStock("TestPort", "GOOG", 20, new Date(1, 3, 2016));
    this.model.sellStock("TestPort", "GOOG", 15, new Date(8, 2, 2017));
    StringBuilder result = new StringBuilder();
    result.append("Performance of TestPort from 2014-05-01 to 2017-02-08\n\n");
    result.append("2014-05-01: ***************************\n" +
            "2014-08-10: *****************************\n" +
            "2014-11-19: ***************************\n" +
            "2015-02-28: *****************************\n" +
            "2015-06-09: ***************************\n" +
            "2015-09-18: ********************************\n" +
            "2015-12-28: ***************************************\n" +
            "2016-04-07: **********************************************************************" +
            "************************************************************************************" +
            "********************************\n" +
            "2016-07-17: ********************************************************************" +
            "************************************************************************************" +
            "********************************\n" +
            "2016-10-26: ***********************************************************************" +
            "***********************************************************************************" +
            "**********************************************\n" +
            "2017-02-04: " +
            "**********************************************************************************" +
            "***********************************************************************************" +
            "************************************\n" +
            "2017-02-08: *************************************************************************"
            + "********\n");
    result.append("\nScale: * = 100.0\n");
    assertEquals(result.toString(), this.model.displayPerformance("TestPort",
            new Date(1, 5, 2014),
            new Date(8, 2, 2017)));
  }
}
