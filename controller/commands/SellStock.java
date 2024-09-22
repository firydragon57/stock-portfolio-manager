package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Date;
import stocks.model.IModel;
import stocks.viewer.Viewer;

/**
 * Represents the sell stock command which sells shares from a given portfolio.
 */
public class SellStock extends Viewer implements Command {

  private final Appendable appendable;

  /**
   * Creates a sell stock object.
   *
   * @param appendable to be appended.
   */
  public SellStock(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      String stockName = sc.next();
      int numShares = sc.nextInt();
      Date date = toDate(sc.next());
      model.sellStock(portName, stockName, numShares, date);
      writeMessage("Sold " + numShares + " share(s) of " + stockName + " from "
              + portName + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      String stockName = inputs[1];
      double numShares = Double.parseDouble(inputs[2]);
      Date date = toDate(inputs[3]);
      model.sellStock(portName, stockName, numShares, date);
      return "Sold " + numShares + " shares(s) of " + stockName + " from " + portName;
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
