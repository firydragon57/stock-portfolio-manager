package stocks.controller.commands;

import java.util.List;
import java.util.Scanner;

import stocks.viewer.Viewer;
import stocks.model.Date;
import stocks.model.IModel;

/**
 * Represents the x day crossover command which returns all days over the x-day average in the
 * given range.
 */
public class XDayCross extends Viewer implements Command {

  private final Appendable appendable;

  /**
   * Sets the appendable to the given one.
   *
   * @param appendable the appendable to be used.
   */
  public XDayCross(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel port, Scanner sc) {
    try {
      String name = sc.next();
      Date from = toDate(sc.next());
      Date to = toDate(sc.next());
      int xValue = sc.nextInt();
      List<Date> dates = port.crossOver(name, from, to, xValue);
      StringBuilder s = new StringBuilder();
      for (Date d : dates) {
        s.append(d.toString());
      }
      writeMessage("The days where the closing value is above x-day-average from "
              + from.toString() + " to " + to.toString() + " are: " + s.toString()
              + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String stockName = inputs[0];
      Date from = toDate(inputs[1]);
      Date to = toDate(inputs[2]);
      int xDays = Integer.parseInt(inputs[3]);
      List<Date> dates = model.crossOver(stockName, from, to, xDays);
      StringBuilder s = new StringBuilder();
      for (Date d : dates) {
        s.append(d.toString());
      }
      return "The days where the closing value is above x-day-average from "
              + from.toString() + " to " + to.toString() + " are: " + s.toString();
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
