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
  private int playerPoints = 0;
  private int aiPoints = 0;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {

    this.playerName = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    this.numRounds = numRounds;
    this.currentRound = 1;
    this.difficultyLevel = difficulty;
  }

  public void play() {
    Colour playerColour = null;
    Colour playerGuess = null;
    Colour aiColour = null;
    Colour aiGuess = null;
    if (currentRound > numRounds) {
      MessageCli.PRINT_END_GAME.printMessage();
      return;
    }
    MessageCli.START_ROUND.printMessage(currentRound, numRounds);

    MessageCli.ASK_HUMAN_INPUT.printMessage();

    // Check if the input is valid
    // Ask for input in a loop until valid input is given

    while (true) {
      // Get the input from the user
      String input = Utils.scanner.nextLine();
      String[] inputs = input.trim().split("\\s+");

      // Check length
      if (inputs.length != 2) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }

      playerColour = Colour.fromInput(inputs[0]);
      playerGuess = Colour.fromInput(inputs[1]);

      // Check if both inputs are valid colours
      if (playerColour == null || playerGuess == null) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }

      // Input is valid, break the loop
      break;
    }

    // Print the input

    MessageCli.PRINT_INFO_MOVE.printMessage(playerName, playerColour.name(), playerGuess.name());

    // produce a random colour every 3 rounds
    if (currentRound % 3 == 0) {
      Colour powerColour = Colour.getRandomColourForPowerColour();
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour.name());
    }

    if (difficultyLevel == Difficulty.EASY) {
      // Easy AI strategy: Randomly choose a colour
      // The AI will choose a colour randomly from the available colours
      EasyAiStrategy easyAi = new EasyAiStrategy();
      aiColour = easyAi.aiChooseColour(difficultyLevel);

      // The AI will guess the player's colour
      aiGuess = easyAi.aiChooseColour(difficultyLevel);

      // Print the AI's choice and guess
      MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColour.name(), aiGuess.name());
    }
    // increment the round
    currentRound++;
  }

  public void showStats() {}
}
