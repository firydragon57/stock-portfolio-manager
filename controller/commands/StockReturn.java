package stocks.controller.commands;

import java.util.Scanner;

import stocks.viewer.Viewer;
import stocks.model.Date;
import stocks.model.IModel;

/**
 * Represents a stock return command which returns the return on a stock.
 */
public class StockReturn extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Sets the appendable to the given one.
   *
   * @param appendable the appendable to be used.
   */
  public StockReturn(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String name = sc.next();
      Date from = toDate(sc.next());
      Date to = toDate(sc.next());
      writeMessage(("The return of " + name + " from " + from.toString() + " to " +
              to.toString()) + " is: " + model.stockReturn(name, from, to) + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String stockName = inputs[0];
      Date from = toDate(inputs[1]);
      Date to = toDate(inputs[2]);
      return "The return of " + stockName + " from " + from.toString() + " to " + to.toString()
              + " is: " + model.stockReturn(stockName, from, to);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
