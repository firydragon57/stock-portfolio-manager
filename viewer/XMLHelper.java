package stocks.viewer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import stocks.model.Date;
import stocks.model.Portfolio;
import stocks.model.Stock;

/**
 * Helper class to help convert between XML and portfolios.
 */
public class XMLHelper extends Viewer {

  /**
   * Saves a given portfolio to a file on the user's computer.
   *
   * @param fileName name of the file to be created.
   * @param portfolio to be converted into a file
   */
  public void savePortfolio(String fileName, Portfolio portfolio) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      Element root = doc.createElement(portfolio.getName());
      doc.appendChild(root);
      Element stocks = doc.createElement("STOCKS");
      for (int i = 0; i < portfolio.getStockSize(); i++) {
        Element s = doc.createElement("Stock");
        s.setAttribute("Ticker", portfolio.getStockName(i));
        Map<Date, Double> shareMap = portfolio.getShareMap(portfolio.getStockName(i));
        Element dates = doc.createElement("DATE");
        Element shares = doc.createElement("SHARE");
        Element closing = doc.createElement("CLOSING");
        for (Map.Entry<Date, Double> entry : shareMap.entrySet()) {
          dates.appendChild(doc.createTextNode(entry.getKey().toString() + "\n"));
          shares.appendChild(doc.createTextNode(entry.getValue().toString() + "\n"));
          String stockName = portfolio.getStockName(i);
          Stock st = portfolio.findStock(stockName);
          double close = st.getClose(entry.getKey());
          closing.appendChild(doc.createTextNode(close + "\n"));
        }
        s.appendChild(dates);
        s.appendChild(shares);
        s.appendChild(closing);
        stocks.appendChild(s);
      }
      root.appendChild(stocks);
      // Make a transformer factory to create the Transformer
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      // Make the transformer
      Transformer transformer = transformerFactory.newTransformer();
      // Mark the document as a DOM (XML) source
      DOMSource source = new DOMSource(doc);
      // Where the XML will go
      StreamResult result = new StreamResult(new File(fileName));
      // Write the XML to file
      transformer.transform(source, result);

    } catch (ParserConfigurationException e) {
      throw new IllegalArgumentException("Save portfolio failed");
    } catch (TransformerException e) {
      throw new IllegalArgumentException("Transformer failed");
    }
  }

  /**
   * Downloads a file from the users computer and converts it back into a portfolio.
   *
   * @param file to be downloaded and converted.
   * @return a portfolio which was previously saved to the computer.
   */
  public Portfolio downloadPortfolio(File file) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();
      String portName = doc.getDocumentElement().getNodeName();
      NodeList nList = doc.getElementsByTagName("Stock");
      String ticker;
      NodeList dates;
      NodeList shares;
      NodeList closing;
      Map<Date, Double> shareMap = new HashMap<>();
      Stock stock;
      Map<Stock, Map<Date, Double>> stockMap = new HashMap<>();
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element e = (Element) nNode;
          ticker = e.getAttribute("Ticker");
          dates = e.getElementsByTagName("DATE");
          shares = e.getElementsByTagName("SHARE");
          closing = e.getElementsByTagName("CLOSING");
          stock = new Stock(ticker, dateToList(dates), doubleToList(closing));
          for (int i = 0; i < dateToList(dates).size(); i++) {
            shareMap.put(dateToList(dates).get(i), doubleToList(shares).get(i));
          }
          stockMap.put(stock, shareMap);
        }
      }
      return new Portfolio(portName, stockMap);
    } catch (Exception e) {
      System.out.print(e.getMessage());
      throw new IllegalArgumentException("Download portfolio failed");
    }
  }

  private ArrayList<Date> dateToList(NodeList list) {
    ArrayList<Date> result = new ArrayList<Date>();
    String everything = list.item(0).getTextContent();
    String [] dates = everything.split("\n");
    for (String date : dates) {
      String flipedDate = date.substring(8) + date.substring(5, 7) + date.substring(0, 4);
      result.add(this.toDate(flipedDate));
    }
    return result;
  }

  private ArrayList<Double> doubleToList(NodeList list) {
    ArrayList<Double> result = new ArrayList<Double>();
    String everything = list.item(0).getTextContent();
    String [] closings = everything.split("\n");
    for (String closing : closings) {
      result.add(Double.parseDouble(closing));
    }
    return result;
  }
}
