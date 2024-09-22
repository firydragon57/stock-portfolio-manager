package stocks.viewer;

/**
 * Interface for the GUI. Allows for the GUI to interact with the model and perform operations.
 */
public interface IView {

  /**
   * Gets the current data from the user.
   *
   * @return the data from user in the form of a string.
   */
  String getData();

  /**
   * Sets text on the GUI to display a message back to the user.
   *
   * @param data message to be displayed.
   */
  void setData(String data);

  /**
   * Adds a listener to the calling object.
   *
   * @param viewListener checks for updates to object.
   */
  void addViewListener(IViewListener viewListener);

  /**
   * Sets the focus back to the calling object.
   */
  void requestFocus();

  /**
   * If passed true, sets the frame to be visible, and if false invisible.
   *
   * @param value decides if the frame should be visible.
   */
  void setVisible(boolean value);

  /**
   * Terminates the program.
   */
  void terminate();
}
