package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Date;
import stocks.model.IModel;
import stocks.viewer.Viewer;

/**
 * Represents the composition command which returns all stocks and shares of a portfolio.
 */
public class Composition extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates a composition object.
   *
   * @param appendable to be appended.
   */
  public Composition(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String portName = sc.next();
      Date date = this.toDate(sc.next());
      writeMessage(model.composition(portName, date), appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      Date date = this.toDate(inputs[1]);
      return model.composition(portName, date);
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
