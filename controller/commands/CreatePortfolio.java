package stocks.controller.commands;

import java.util.Scanner;

import stocks.viewer.Viewer;
import stocks.model.IModel;

/**
 * Represents a command to create a portfolio.
 */
public class CreatePortfolio extends Viewer implements Command {

  private final Appendable appendable;

  /**
   * Sets the appendable to the given one.
   *
   * @param appendable the appendable to be used.
   */
  public CreatePortfolio(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      String name = sc.next();
      model.createPortfolio(name);
      writeMessage("Created stock portfolio " + name + "\n", this.appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      String portName = inputs[0];
      model.createPortfolio(portName);
      return "Created portfolio " + portName;
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
