import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import stocks.controller.StockController;
import stocks.model.MockModel;
import stocks.viewer.IView;
import stocks.viewer.MyView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Controller.
 */
public class ControllerTest {
  @Test
  public void testStockReturnInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("stock-return GOOG 28052024 29052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("Name = GOOG, From = 2024-05-28, To = 2024-05-29\n", log.toString());
  }


  @Test
  public void movingAverageInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("x-day-average GOOG 28052024 5 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("Name = GOOG, Date = 2024-05-28, xDays = 5\n", log.toString());
  }


  @Test
  public void crossOverInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("x-day-crossover GOOG 28052024 29052024 5 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("Name = GOOG, From = 2024-05-28, To = 2024-05-29, xValue = 5\n",
            log.toString());
  }


  @Test
  public void createPortfolioInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("stock-create-portfolio GOOG q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("Name = GOOG\n", log.toString());
  }


  @Test
  public void findValueInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("find-value port1 28052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("portName = port1, date = 2024-05-28\n", log.toString());
  }


  @Test
  public void purchaseStockInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("purchase-stock port2 GOOG 10000 28052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("portName = port2, stockName = GOOG, numShares = 10000.0" +
                    ", date = 2024-05-28\n",
            log.toString());
  }

  @Test
  public void sellStockInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("sell-stock port2 GOOG 10000 28052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("portName = port2, stockName = GOOG, numShares = 10000.0" +
                    ", date = 2024-05-28\n",
            log.toString());
  }

  @Test
  public void compositionInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("portfolio-composition port2 28052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("portName = port2, date = 2024-05-28\n", log.toString());
  }

  @Test
  public void findDistributionInput() {
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("value-distribution port2 28052024 q");
    MockModel mock = new MockModel(log);
    StringBuilder dontCareOutput = new StringBuilder();
    IView view = new MyView();
    StockController controller = new StockController(in, dontCareOutput, mock, view);
    controller.start(false);
    assertEquals("portName = port2, date = 2024-05-28\n", log.toString());
  }
}