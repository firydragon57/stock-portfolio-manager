package stocks.viewer;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import javax.swing.UIManager;


/**
 * This class represents what the GUI should look like and what happens when buttons are clicked.
 */
public class MyView extends JFrame implements IView, ActionListener, KeyListener, MouseListener {
  private final JComboBox<String> dropdown;
  private final JTextArea textbox;
  private final JTextArea TITLE = new JTextArea("Stock Analyzer \n-Neil & Ethan \nFormat all " +
          "dates ddmmyyyy and include spaces between each input.");

  private final JTextArea instruction;

  private final List<IViewListener> myListeners;

  /**
   * Creates the window, and sets all values to their default proper values.
   */
  public MyView() {
    super();
    setSize(new Dimension(1000, 600));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    this.myListeners = new ArrayList<>();

    setLayout(new BorderLayout(50,50));
    getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.LIGHT_GRAY));
    this.TITLE.setWrapStyleWord(true);
    this.TITLE.setLineWrap(true);
    this.TITLE.setOpaque(false);
    this.TITLE.setEditable(false);
    this.TITLE.setFocusable(false);
    this.TITLE.setBackground(UIManager.getColor("Label.background"));
    this.TITLE.setFont(UIManager.getFont("Label.font"));
    this.TITLE.setBorder(UIManager.getBorder("Label.border"));
    this.instruction = new JTextArea("Select an operation from the drop down menu");
    this.instruction.setWrapStyleWord(true);
    this.instruction.setLineWrap(true);
    this.instruction.setOpaque(false);
    this.instruction.setEditable(false);
    this.instruction.setFocusable(false);
    this.instruction.setBackground(UIManager.getColor("Label.background"));
    this.instruction.setFont(UIManager.getFont("Label.font"));
    this.instruction.setBorder(UIManager.getBorder("Label.border"));
    JButton goButton = new JButton("GO!");
    this.textbox = new JTextArea("");
    this.dropdown = new JComboBox<String>();
    this.dropdown.addItem("Select");
    this.dropdown.addItem("stock-create-portfolio");
    this.dropdown.addItem("purchase-stock");
    this.dropdown.addItem("sell-stock");
    this.dropdown.addItem("find-value");
    this.dropdown.addItem("portfolio-composition");
    this.dropdown.addItem("save-portfolio");
    this.dropdown.addItem("download-portfolio");
    this.textbox.setText("Type Here");

    goButton.setActionCommand("start");
    goButton.addActionListener(this);
    this.dropdown.addActionListener(this);

    add(this.TITLE, BorderLayout.NORTH);
    TITLE.setFont(new Font("Courier", Font.BOLD, 20));
    add(goButton, BorderLayout.EAST);
    goButton.setPreferredSize(new Dimension(100,100));
    add(this.instruction, BorderLayout.CENTER);
    instruction.setFont(new Font("Courier", Font.PLAIN, 15));
    add(this.textbox, BorderLayout.SOUTH);
    textbox.setPreferredSize(new Dimension(500,50));
    textbox.setFont(new Font("Courier", Font.PLAIN, 18));
    add(this.dropdown, BorderLayout.WEST);

    setFocusable(true);
    requestFocus();
    pack();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof JComboBox) {
      JComboBox cb = (JComboBox)e.getSource();
      String instruction = (String)cb.getSelectedItem();
      this.setInstruction(Objects.requireNonNull(instruction));
    }
    else {
      if (e.getActionCommand().equals("start")) {
        if (((String) dropdown.getSelectedItem()).equals("Select")) {
          instruction.setText("Please select an operation and follow the insturctions.");
          return;
        }
        fireSetDataEvent((String) dropdown.getSelectedItem());
      }
    }
  }

  private void setInstruction(String message) {
    switch (message) {
      case "stock-create-portfolio":
        this.instruction.setText("Input name of portfolio");
        break;
      case "purchase-stock":
        this.instruction.setText("Input the portfolio name along with the stock ticker, numer of " +
                "shares, and date of purchase");
        break;
      case "sell-stock":
        this.instruction.setText("Input the portfolio name along with the stock ticker, numer of " +
                "shares, and date of sale");
        break;
      case "find-value":
      case "portfolio-composition":
        this.instruction.setText("Input the portfolio name and the date");
        break;
      case "save-portfolio":
        this.instruction.setText("Input the portfolio name and the name you want to " +
                "save the file as");
        break;
      case "download-portfolio":
        this.instruction.setText("Input the name of the file you want to download");
        break;
      default:
        this.instruction.setText("Select an operation from the drop down menu");
        break;
    }
  }

  private void fireSetDataEvent(String str) {
    for (IViewListener viewer : myListeners) {

      viewer.handelGetData(str, this.getData(), this);
      this.textbox.setText("");
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Does nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // Does nothing
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Does nothing
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // Does nothing
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Does nothing
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Does nothing
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Does nothing
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Does nothing
  }

  @Override
  public String getData() {
    return textbox.getText();
  }

  @Override
  public void setData(String data) {
    this.instruction.setText(Objects.requireNonNull(data));
  }

  @Override
  public void addViewListener(IViewListener viewListener) {
    this.myListeners.add(Objects.requireNonNull(viewListener));
  }

  @Override
  public void terminate() {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}
