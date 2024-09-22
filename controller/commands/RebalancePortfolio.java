package stocks.controller.commands;

import java.util.ArrayList;
import java.util.Scanner;
import stocks.model.Date;
import stocks.model.IModel;
import stocks.viewer.Viewer;

/**
 * Represents the rebalancePortfolio command which balances a given portfolio to the
 * percentages desired by the user.
 */
public class RebalancePortfolio extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates a rebalance portfolio object.
   *
   * @param appendable to be appended.
   */
  public RebalancePortfolio(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      Date date = toDate(sc.next());
      ArrayList<Double> percentages = new ArrayList<Double>();
      for (int i = 0; i < model.getPort(portName).getStockSize(); i++) {
        percentages.add(Double.parseDouble(sc.next()));
      }
      model.getPort(portName).rebalancePortfolio(date, percentages);
      writeMessage("Rebalanced portfolio: " + portName
              + "\n", this.appendable);

    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      Date date = this.toDate(inputs[1]);
      ArrayList<Double> percentages = new ArrayList<Double>();
      for (int i = 0; i < model.getPort(portName).getStockSize(); i++) {
        percentages.add(Double.parseDouble(inputs[i + 2]));
      }
      model.getPort(portName).rebalancePortfolio(date, percentages);
      return "Rebalanced portolio: " + portName;
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
