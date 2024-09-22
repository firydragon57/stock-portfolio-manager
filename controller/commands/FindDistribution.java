package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Date;
import stocks.model.IModel;
import stocks.viewer.Viewer;

/**
 * Represents the findDistribution command which displays how the total value of a portfolio
 * is split into each of its stocks.
 */
public class FindDistribution extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates a findDistribution object.
   *
   * @param appendable to be appended.
   */
  public FindDistribution(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      Date date = toDate(sc.next());
      writeMessage("Distribution of given portfolio is: "
              + model.findDistribution(portName, date) + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      Date date = this.toDate(inputs[1]);
      return "Distribution of given portfolio is: " + model.findDistribution(portName, date);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
