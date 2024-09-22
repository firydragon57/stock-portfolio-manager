package stocks.controller.commands;

import java.util.Scanner;

import stocks.viewer.Viewer;
import stocks.model.Date;
import stocks.model.IModel;

/**
 * Represents the x day average command which returns the average over the last x days of the
 * given stock.
 */
public class XDayAvg extends Viewer implements Command {

  private final Appendable appendable;

  /**
   * Sets the appendable to the given one.
   *
   * @param appendable the appendable to be used.
   */
  public XDayAvg(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel port, Scanner sc) {
    try {
      String name = sc.next();
      Date start = toDate(sc.next());
      int xDays = sc.nextInt();
      writeMessage("The average of " + name + " from " + xDays + " days before "
              + start.toString() + " is: " + port.movingAverage(name, start, xDays)
              + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String stockName = inputs[0];
      Date start = toDate(inputs[1]);
      int xDays = Integer.parseInt(inputs[2]);
      return "The average of " + stockName + " from " + xDays + " days before "
              + start.toString() + " is: " + model.movingAverage(stockName, start, xDays);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
