
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import stocks.controller.StockController;
import stocks.model.Date;
import stocks.model.MockModel;
import stocks.viewer.IView;
import stocks.viewer.MyView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the viewer.
 */
public class ViewerTest {
  StringBuilder log;
  Reader in;
  MockModel mock;
  StringBuilder out;
  StockController controller;
  IView view;

  private void setUp() {
    this.log = new StringBuilder();
    this.in = new StringReader("");
    this.mock = new MockModel(log);
    this.out = new StringBuilder();
    this.view = new MyView();
    this.controller = new StockController(in, out, mock, view);
  }

  @Test
  public void writeMessage() {
    this.setUp();
    assertEquals("", out.toString());
    this.controller.writeMessage("Here are the dates: \n", this.out);
    assertEquals("Here are the dates: \n", this.out.toString());
    this.controller.writeMessage("Stock return: \n", this.out);
    assertEquals("Here are the dates: \nStock return: \n", this.out.toString());
    this.controller.writeMessage("Portfolio value: \n", this.out);
    assertEquals("Here are the dates: \nStock return: \nPortfolio value: \n",
            this.out.toString());
  }

  @Test
  public void welcomeMessage() {
    this.setUp();
    StringBuilder answer = new StringBuilder();
    this.controller.writeMessage("Welcome to the stock \n", this.out);
    this.controller.writeMenu(this.out);
    this.controller.welcomeMessage(answer);
    assertEquals(this.out.toString(), answer.toString());
  }

  @Test
  public void goodbyeMessage() {
    this.setUp();
    StringBuilder answer = new StringBuilder();
    this.controller.writeMessage("Thank you for using this program! Goodbye.", this.out);
    this.controller.goodbyeMessage(answer);
    assertEquals(out.toString(), answer.toString());
  }

  @Test
  public void writeMenu() {
    this.setUp();
    StringBuilder answer = new StringBuilder();
    this.controller.writeMessage("Supported user instructions are: "
            + System.lineSeparator(), out);
    this.controller.writeMessage("Format all dates as ddmmyyyy " +
            "(EX: 05012022 - January 5 2022)"
            + System.lineSeparator(), out);
    this.controller.writeMessage("stock-return ticker start-date end-date " +
            "(print the return "
            + "over the given period" + System.lineSeparator(), out);
    this.controller.writeMessage("x-day-average ticker start-date xDays " +
            "(print the average from the "
            + "past x days to the given date)" + System.lineSeparator(), out);
    this.controller.writeMessage("x-day-crossover ticker start-date end-date value " +
            "(print the days "
            + "where the closing price is greater than the given value)"
            + System.lineSeparator(), out);
    this.controller.writeMessage("stock-create-portfolio name (create a portfolio with " +
            "the given "
            + "name that you can add stocks to)" + System.lineSeparator(), out);
    this.controller.writeMessage("purchase-stock portName ticker numShares date "
            + "(purchases the given amount of shares of the given stock on the given date"
            + " to the given portfolio)" + System.lineSeparator(), out);
    this.controller.writeMessage("sell-stock portName ticker numShares date (sells " +
            "the given amount"
            + "of shares of the given stock on the given date from the given portfolio)"
            + System.lineSeparator(), out);
    this.controller.writeMessage("find-value portName date (finds the value of " +
            "portfolio with "
            + "the given name and date)" + System.lineSeparator(), out);
    this.controller.writeMessage("portfolio-composition portName date (find the " +
            "composition of the " +
            "given portfolio" + System.lineSeparator(), out);
    this.controller.writeMessage("value-distribution portName date (shows the " +
            "distribution of the"
            +  " value of the portfolio)" + System.lineSeparator(), out);
    this.controller.writeMessage("balance-portfolio portName date stock1Percent " +
            "stock2Percent "
            + "stock3Percent... (rebalances the portfolio on the given date with the given "
            + "percentages)" + System.lineSeparator(), out);
    this.controller.writeMessage("display-performance portName fromDate toDate " +
            "(shows the performance"
            + " of a given portfolio between the two dates as a graph)"
            + System.lineSeparator(), out);
    this.controller.writeMessage("save-portfolio portName fileName " +
            "(Creates a file in this folder "
            + "with the given portfolio)", out);
    this.controller.writeMessage("download-portfolio fileName "
            + "(finds the file and adds the portfolio to the model)", out);
    this.controller.writeMessage("menu (Print supported instruction list)"
                    + System.lineSeparator(),
            out);
    this.controller.writeMessage("q or quit (quit the program) "
            + System.lineSeparator(), out);
    this.controller.writeMenu(answer);
    assertEquals(out.toString(), answer.toString());
  }

  @Test
  public void toDate() {
    this.setUp();
    assertEquals(new Date(28, 5, 2024), this.controller.toDate("28052024"));
    assertEquals(new Date(17, 7, 2022), this.controller.toDate("17072022"));
    assertEquals(new Date(1, 1, 2021), this.controller.toDate("01012021"));
  }
}
