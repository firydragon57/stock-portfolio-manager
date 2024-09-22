package stocks.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import stocks.controller.commands.Composition;
import stocks.controller.commands.DisplayPerformance;
import stocks.controller.commands.DownloadPortfolio;
import stocks.controller.commands.FindDistribution;
import stocks.controller.commands.RebalancePortfolio;
import stocks.controller.commands.SavePortfolio;
import stocks.controller.commands.SellStock;
import stocks.viewer.IView;
import stocks.viewer.IViewListener;
import stocks.viewer.Viewer;
import stocks.controller.commands.PurchaseStock;
import stocks.controller.commands.Command;
import stocks.controller.commands.CreatePortfolio;
import stocks.controller.commands.FindValue;
import stocks.controller.commands.StockReturn;
import stocks.controller.commands.XDayAvg;
import stocks.controller.commands.XDayCross;
import stocks.model.IModel;

/**
 * Represents the stock controller which calls all commands and controls the program.
 */
public class StockController extends Viewer implements IController, IViewListener {
  private final Readable readable;
  private final Appendable appendable;
  private final IModel model;
  private final Map<String, Command> command;
  private final IView view;

  /**
   * Creates a stock controller object.
   *
   * @param readable   the readable.
   * @param appendable the appendable.
   * @param model      the model for the program.
   */
  public StockController(Readable readable, Appendable appendable, IModel model, IView view) {
    if (readable == null || appendable == null || model == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.readable = readable;
    this.appendable = appendable;
    this.model = model;
    this.command = new HashMap<String, Command>();
    this.command.put("stock-return", new StockReturn(System.out));
    this.command.put("x-day-average", new XDayAvg(System.out));
    this.command.put("x-day-crossover", new XDayCross(System.out));
    this.command.put("stock-create-portfolio", new CreatePortfolio(System.out));
    this.command.put("purchase-stock", new PurchaseStock(System.out));
    this.command.put("sell-stock", new SellStock(System.out));
    this.command.put("find-value", new FindValue(System.out));
    this.command.put("portfolio-composition", new Composition(System.out));
    this.command.put("value-distribution", new FindDistribution(System.out));
    this.command.put("rebalance-portfolio", new RebalancePortfolio(System.out));
    this.command.put("display-performance", new DisplayPerformance(System.out));
    this.command.put("save-portfolio", new SavePortfolio(System.out));
    this.command.put("download-portfolio", new DownloadPortfolio(System.out));
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }

  @Override
  public void start(boolean guiOn) {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    this.view.setVisible(guiOn);

    if (!guiOn) {
      this.welcomeMessage(this.appendable);

      while (!quit) {
        writeMessage("Type instruction: ", this.appendable);
        String instruction = sc.next();

        if (command.containsKey(instruction)) {
          this.command.get(instruction).execute(model, sc);
        } else if (instruction.equals("menu")) {
          welcomeMessage(this.appendable);
        } else if (instruction.equals("q") || instruction.equals("quit")) {
          quit = true;
        } else {
          writeMessage("Undefined instruction: " + instruction + System.lineSeparator(),
                  this.appendable);
        }
      }
      this.goodbyeMessage(this.appendable);
    }
  }

  @Override
  public void handelGetData(String str, String input, IView view) {
    String [] inputs = input.split(" ");


    if (command.containsKey(str)) {
      view.setData(command.get(str).executeGUI(model, inputs));
    } else {
      throw new IllegalArgumentException("Command not found");
    }

    this.view.requestFocus();
  }
}
