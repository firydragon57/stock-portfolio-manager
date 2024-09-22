import org.junit.Test;

import java.io.File;

import stocks.model.Date;
import stocks.model.Model;
import stocks.model.Portfolio;
import stocks.viewer.XMLHelper;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the XML parser to see if files can be converted to portfolios and vice
 * versa.
 */
public class XMLHelperTest {

  Date date = new Date(1, 6, 2024);

  @Test
  public void persistPortfolio() {
    Model model = new Model();
    XMLHelper xmlHelper = new XMLHelper();

    model.createPortfolio("test");
    model.purchaseStock("test", "GOOG", 0.75, date);
    model.purchaseStock("test", "AAPL", 50.0, date);

    try {
      Portfolio portTest = xmlHelper.downloadPortfolio(new File(""));
    } catch (IllegalArgumentException e) {
      assertEquals("Download portfolio failed", e.getMessage());
    }
    xmlHelper.savePortfolio("TEST.xml", model.getPort("test"));

    File file = new File("TEST.xml");
    Portfolio port2 = xmlHelper.downloadPortfolio(file);
    assertEquals(model.getPort("test").findComposition(date), port2.findComposition(date));
  }


}