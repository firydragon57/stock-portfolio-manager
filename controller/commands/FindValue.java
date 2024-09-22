package stocks.controller.commands;

import java.util.Scanner;

import stocks.viewer.Viewer;
import stocks.model.Date;
import stocks.model.IModel;

/**
 * Represents a find value command which gets the value of a portfolio on a given date.
 */
public class FindValue extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Sets the appendable to the given one.
   *
   * @param appendable the appendable to be used.
   */
  public FindValue(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      Date date = toDate(sc.next());
      writeMessage("Total of given portfolio is: " + model.findValue(portName, date)
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
      return "Total of given portfolio is: " + model.findValue(portName, date);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
