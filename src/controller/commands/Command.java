package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModel;

/**
 * Represents a command interface. Every command has access to an execute method which is used to
 * execute the command.
 */
public interface Command {

  /**
   * Execute the command to the given model.
   *
   * @param model to execute the command.
   * @param sc scanner to take in user input.
   */
  public void execute(IModel model, Scanner sc);

  /**
   * Execute the commands to the GUI.
   *
   * @param model to execute the command.
   * @param inputs user inputs in the form of an array, split word by word.
   * @return a string to be displayed back to the user.
   */
  public String executeGUI(IModel model, String [] inputs);

}
