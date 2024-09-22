package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModel;
import stocks.model.Portfolio;
import stocks.viewer.Viewer;
import stocks.viewer.XMLHelper;

/**
 * Represents the SavePortfolio command which takes a portfolio and creates a file on the users
 * computer representing the portfolio.
 */
public class SavePortfolio extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates a save portfolio object.
   *
   * @param appendable to be appended.
   */
  public SavePortfolio(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    try {
      Portfolio tempPort = model.getPort(sc.next());
      String fileName = sc.next();
      XMLHelper helper = new XMLHelper();
      helper.savePortfolio(fileName, tempPort);
      writeMessage("Saved " + tempPort.getName() + " as " + fileName, appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage(), appendable);
    }
  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {
    try {
      Portfolio tempPort = model.getPort(inputs[0]);
      String fileName = inputs[1];
      XMLHelper helper = new XMLHelper();
      helper.savePortfolio(fileName, tempPort);
      return "Saved " + tempPort.getName() + " as " + fileName;
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }


}
