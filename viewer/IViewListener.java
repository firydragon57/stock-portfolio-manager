package stocks.viewer;

/**
 * Interface for a listener to check for updates to objects.
 */
public interface IViewListener {

  /**
   * Handles getting the data when an object is updated.
   */
  void handelGetData(String str, String input, IView view);
}
