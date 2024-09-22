package stocks.viewer;

import java.io.IOException;

import stocks.model.Date;

/**
 * Represents a viewer class which is used to interact with the user.
 */
public abstract class Viewer {

  /**
   * Appends the given string to the current message.
   *
   * @param message    to be added.
   * @param appendable current appendable.
   * @throws IllegalStateException when the message cannot be appended.
   */
  public void writeMessage(String message, Appendable appendable) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Writes the welcome message.
   *
   * @param appendable the current appendable.
   */
  public void welcomeMessage(Appendable appendable) {
    writeMessage("Welcome to the stock " + System.lineSeparator(), appendable);
    writeMenu(appendable);
  }

  /**
   * Writes the goodbye message.
   *
   * @param appendable the current appendable.
   */
  public void goodbyeMessage(Appendable appendable) {
    writeMessage("Thank you for using this program! Goodbye.", appendable);
  }

  /**
   * Writes the menu message.
   *
   * @param appendable the current appendable.
   */
  public void writeMenu(Appendable appendable) {
    writeMessage("Supported user instructions are: " + System.lineSeparator(), appendable);
    writeMessage("Format all dates as ddmmyyyy (EX: 05012022 - January 5 2022)"
            + System.lineSeparator(), appendable);
    writeMessage("stock-return ticker start-date end-date (print the return "
            + "over the given period" + System.lineSeparator(), appendable);
    writeMessage("x-day-average ticker start-date xDays (print the average from the "
            + "past x days to the given date)" + System.lineSeparator(), appendable);
    writeMessage("x-day-crossover ticker start-date end-date value (print the days "
            + "where the closing price is greater than the given value)"
            + System.lineSeparator(), appendable);
    writeMessage("stock-create-portfolio name (create a portfolio with the given "
            + "name that you can add stocks to)" + System.lineSeparator(), appendable);
    writeMessage("purchase-stock portName ticker numShares date "
            + "(purchases the given amount of shares of the given stock on the given date"
            + " to the given portfolio)" + System.lineSeparator(), appendable);
    writeMessage("sell-stock portName ticker numShares date (sells the given amount"
            + "of shares of the given stock on the given date from the given portfolio)"
            + System.lineSeparator(), appendable);
    writeMessage("find-value portName date (finds the value of portfolio with "
            + "the given name and date)" + System.lineSeparator(), appendable);
    writeMessage("portfolio-composition portName date (find the composition of the " +
            "given portfolio" + System.lineSeparator(), appendable);
    writeMessage("value-distribution portName date (shows the distribution of the"
            +  " value of the portfolio)" + System.lineSeparator(), appendable);
    writeMessage("balance-portfolio portName date stock1Percent stock2Percent "
            + "stock3Percent... (rebalances the portfolio on the given date with the given "
            + "percentages)" + System.lineSeparator(), appendable);
    writeMessage("display-performance portName fromDate toDate (shows the performance"
            + " of a given portfolio between the two dates as a graph)"
            + System.lineSeparator(), appendable);
    writeMessage("save-portfolio portName fileName (Creates a file in this folder "
            + "with the given portfolio)", appendable);
    writeMessage("download-portfolio fileName "
            + "(finds the file and adds the portfolio to the model)", appendable);
    writeMessage("menu (Print supported instruction list)" + System.lineSeparator(),
            appendable);
    writeMessage("q or quit (quit the program) " + System.lineSeparator(), appendable);
  }

  /**
   * Creates a date from the given string.
   *
   * @param str string to be made a date.
   * @return a date.
   */
  public Date toDate(String str) {
    return new Date(Integer.parseInt(str.substring(0, 2)), Integer.parseInt(str.substring(2, 4))
            , Integer.parseInt(str.substring(4)));
  }

}
