package stocks.viewer;


import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import stocks.controller.StockController;
import stocks.model.IModel;
import stocks.model.Model;

/**
 * Represents the main class.
 */
public class Main {
  /**
   * Main method to run the program.
   *
   * @param args to be passed.
   */
  public static void main(String[] args) {
    ExecutorService ex = Executors.newCachedThreadPool();
    final List<String> arguments = Arrays.asList(args);
    final int modeIndex = arguments.indexOf("-text");
    final String mode = modeIndex == -1 ? "gui" : arguments.get(modeIndex);
    IModel model = new Model();
    Readable streamReader = new InputStreamReader(System.in);
    Appendable out = System.out;
    MyView view = new MyView();
    StockController controller = new StockController(streamReader, out, model, view);
    controller.start(mode.equals("gui"));
    if (!mode.equals("gui")) {
      view.terminate();
    }

  }
}
