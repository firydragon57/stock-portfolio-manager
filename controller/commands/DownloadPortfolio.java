package stocks.controller.commands;

import java.io.File;
import java.util.Scanner;

import stocks.model.IModel;
import stocks.viewer.Viewer;
import stocks.viewer.XMLHelper;

/**
 * Represents the download portfolio command which searches for a file, then turns it back into
 * a portfolio, and adds it to the list of portfolios.
 */
public class DownloadPortfolio extends Viewer implements Command {
  private final Appendable appendable;

  /**
   * Creates a download portfolio object.
   *
   * @param appendable to be appended.
   */
  public DownloadPortfolio(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void execute(IModel model, Scanner sc) {
    XMLHelper helper = new XMLHelper();
    File file = new File(sc.next());
    try {
      model.addPort(helper.downloadPortfolio(file));
      writeMessage("Downloaded portfolio to model" + System.lineSeparator(), appendable);
    } catch (IllegalArgumentException e) {
      writeMessage("Error: " + e.getMessage() + System.lineSeparator(), this.appendable);
    }

  }

  @Override
  public String executeGUI(IModel model, String[] inputs) {

    XMLHelper helper = new XMLHelper();
    File file = new File(inputs[0]);
    try {
      model.addPort(helper.downloadPortfolio(file));
      return "Downloaded portfolio to model";
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    } catch (Exception e) {
      return "Invalid inputs";
    }
  }
}
