package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Date;
import stocks.model.IModel;
import stocks.viewer.Viewer;

/**
 * Represents the DisplayPerformance command which prints out a chart of a portfolios performance
 * over time.
 */
public class DisplayPerformance extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates the displayPerformance object.
   *
   * @param appendable to be appended.
   */
  public DisplayPerformance(Appendable appendable) {
    this.appendable = appendable;
  }


  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      Date fromDate = this.toDate(sc.next());
      Date toDate = this.toDate(sc.next());
      writeMessage(model.displayPerformance(portName, fromDate, toDate), this.appendable);
    }
    catch (IllegalArgumentException e) {
      writeMessage("Error" + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      Date fromDate = this.toDate(inputs[1]);
      Date toDate = this.toDate(inputs[2]);
      return model.displayPerformance(portName, fromDate, toDate);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
