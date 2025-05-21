package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private int numRounds;
  public static int currentRound;
  private String playerName;
  public Difficulty difficultyLevel;
  private int totalPlayerPoints = 0;
  private int totalAiPoints = 0;
  public static Colour playerLastChose;
  public static Colour playerLastGuess;

  public Game() {
    this.numRounds = 0;
    Game.currentRound = 1;
    this.playerName = "";
    this.difficultyLevel = Difficulty.EASY;
  }

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {

    this.playerName = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    this.numRounds = numRounds;
    currentRound = 1;
    this.difficultyLevel = difficulty;
  }

  public void play() {
    Colour playerColour = null;
    Colour playerGuess = null;
    Colour aiColour = null;
    Colour aiGuess = null;
    Colour powerColour = null;
    int playerPoints = 0;
    int aiPoints = 0;
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

    if (difficultyLevel == Difficulty.EASY) {
      // Easy AI strategy: Randomly choose a colour
      // The AI will choose a colour randomly from the available colours
      RandomStrategy easyAi = new RandomStrategy();
      aiColour = easyAi.aiColour(playerColour);

      // The AI will guess the player's colour
      aiGuess = easyAi.aiColour(playerGuess);

    } else if (difficultyLevel == Difficulty.MEDIUM) {
      // Medium AI strategy: Randomly choose a colour excluding the player's colour
      AvoidLastStrategy mediumAi = new AvoidLastStrategy();
      RandomStrategy easyAi = new RandomStrategy();
      // The AI will choose a colour randomly from the available colours
      if (currentRound == 1) {
        aiColour = easyAi.aiColour(playerColour);
      } else {
        aiColour = easyAi.aiColour(playerLastGuess);
      }
      // The AI will guess the player's colour
      if (currentRound == 1) {
        aiGuess = easyAi.aiColour(playerGuess);
      } else {
        aiGuess = mediumAi.aiColour(playerLastChose);
      }
    }

    // Print the AI's choice and guess
    MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColour.name(), aiGuess.name());

    // produce a random colour every 3 rounds
    if (currentRound % 3 == 0) {
      powerColour = Colour.getRandomColourForPowerColour();
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour.name());
    }

    // calculate the points
    if (playerGuess == aiColour) {
      playerPoints++;
      if (currentRound % 3 == 0) {
        if (powerColour == playerGuess) {
          playerPoints += 2;
        }
      }
    }
    if (playerColour == aiGuess) {
      aiPoints++;
      if (currentRound % 3 == 0) {
        if (powerColour == aiGuess) {
          aiPoints += 2;
        }
      }
    }
    this.totalPlayerPoints += playerPoints;
    this.totalAiPoints += aiPoints;
    // print the points
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(playerName, playerPoints);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, aiPoints);
    // increment the round
    currentRound++;
    // set the last colour
    playerLastChose = playerColour;
    playerLastGuess = playerGuess;
  }

  public void showStats() {}
}
