package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private int numRounds;
  private int currentRound;
  private String playerName;
  public Difficulty difficultyLevel;
  private int playerPoints;
  private int aiPoints;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {

    this.playerName = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    this.numRounds = numRounds;
    this.currentRound = 1;
    this.difficultyLevel = difficulty;
  }

  public void play() {
    if (currentRound > numRounds) {
      MessageCli.PRINT_END_GAME.printMessage();
      return;
    }
    MessageCli.START_ROUND.printMessage(currentRound, numRounds);
    currentRound++;
    MessageCli.ASK_HUMAN_INPUT.printMessage();
    // Get the input from the user
    String input = Utils.scanner.nextLine();
    String[] inputs = input.split(" ");
    if (inputs.length != 2) {
      MessageCli.INVALID_HUMAN_INPUT.printMessage();
      return;
    }
    if (Colour.fromInput(inputs[0]) == null || Colour.fromInput(inputs[1]) == null) {
      MessageCli.INVALID_HUMAN_INPUT.printMessage();
      return;
    }
    Colour playerColour = Colour.fromInput(inputs[0]);
    Colour playerGuess = Colour.fromInput(inputs[1]);
    MessageCli.PRINT_INFO_MOVE.printMessage(playerName, playerColour.name(), playerGuess.name());
  }

  public void showStats() {}
}
