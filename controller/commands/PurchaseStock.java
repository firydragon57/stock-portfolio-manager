package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Date;
import stocks.viewer.Viewer;
import stocks.model.IModel;

/**
 * Represents the purchaseStock command which purchases shares of a stock and adds them
 * to a portfolio.
 */
public class PurchaseStock extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates the appendable.
   *
   * @param appendable to be used.
   */
  public PurchaseStock(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      String stockName = sc.next();
      double numShares = sc.nextDouble();
      Date date = toDate(sc.next());
      model.purchaseStock(portName, stockName, numShares, date);
      writeMessage("Purchased " + numShares + " share(s) of " + stockName + " to "
              + portName + "\n", this.appendable);
    } catch (Exception e) {
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
      model.purchaseStock(portName, stockName, numShares, date);
      return "Purchased " + numShares + " shares(s) of " + stockName + " to " + portName;
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
