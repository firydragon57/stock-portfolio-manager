package stocks.controller;

/**
 * Interface for a controller which can run the program appropriately.
 */
public interface IController {

  /**
   * Starts and runs the program.
   *
   * @param guiOn represents if the GUI should be used or not.
   */
  void start(boolean guiOn);
}
