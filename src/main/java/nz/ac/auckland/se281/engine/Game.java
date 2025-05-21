package nz.ac.auckland.se281.engine;

import java.util.HashMap;
import java.util.Map;
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
  public Map<Colour, Integer> colourCounts;
  private int aiLastpoints = 0;

  private int leastUsedCount = 0;
  private int avoidLastCount = 0;

  private String gameStart = null;
  private String gameEnd = null;

  public Game() {
    this.numRounds = 0;
    Game.currentRound = 1;
    this.playerName = "";
    this.difficultyLevel = Difficulty.EASY;
    this.colourCounts = new HashMap<>();
    colourCounts.put(Colour.RED, 0);
    colourCounts.put(Colour.GREEN, 0);
    colourCounts.put(Colour.BLUE, 0);
    colourCounts.put(Colour.YELLOW, 0);
  }

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.gameStart = "Game started";

    this.playerName = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    this.numRounds = numRounds;
    currentRound = 1;
    this.difficultyLevel = difficulty;
  }

  public void play() {
    if (gameStart == null) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }
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
      RandomStrategy random = new RandomStrategy();
      aiColour = random.aiColour(colourCounts, playerColour);

      // The AI will guess the player's colour
      aiGuess = random.aiColour(colourCounts, playerGuess);

    } else if (difficultyLevel == Difficulty.MEDIUM) {
      // Medium AI strategy: Randomly choose a colour excluding the player's colour
      AvoidLastStrategy avoidLast = new AvoidLastStrategy();
      RandomStrategy random = new RandomStrategy();
      // The AI will choose a colour randomly from the available colours
      aiColour = random.aiColour(colourCounts, playerColour);

      // The AI will guess the player's colour
      if (currentRound == 1) {
        aiGuess = random.aiColour(colourCounts, playerGuess);
      } else {
        aiGuess = avoidLast.aiColour(colourCounts, playerLastChose);
      }
    } else if (difficultyLevel == Difficulty.HARD) {
      // Hard AI strategy: Randomly choose a colour
      // The AI will guess the player's colour randomly from the available colours
      LeastUsedStrategy leastUsed = new LeastUsedStrategy();
      RandomStrategy random = new RandomStrategy();
      AvoidLastStrategy avoidLast = new AvoidLastStrategy();
      aiColour = random.aiColour(colourCounts, playerColour);

      // The AI will guess the player's colour
      if (currentRound == 1 || currentRound == 2) {
        aiGuess = random.aiColour(colourCounts, playerGuess);
      } else if (currentRound == 3) {
        aiGuess = leastUsed.aiColour(colourCounts, playerGuess);
        leastUsedCount++;
      } else {
        if (aiLastpoints == 0 && leastUsedCount > 0) {
          // The AI will change its strategy to avoid the last colour
          aiGuess = avoidLast.aiColour(colourCounts, playerLastChose);
          // Reset the least used count
          leastUsedCount = 0;
          avoidLastCount++;
        } else if (aiLastpoints == 0 && avoidLastCount > 0) {
          aiGuess = leastUsed.aiColour(colourCounts, playerLastChose);
          // Reset the avoid last count
          avoidLastCount = 0;
          leastUsedCount++;
        } else if (aiLastpoints > 0 && leastUsedCount > 0) {
          // The AI will keep using the least used strategy
          aiGuess = leastUsed.aiColour(colourCounts, playerLastChose);
          // Reset the least used count
          leastUsedCount = 0;
          leastUsedCount++;
        } else if (aiLastpoints > 0 && avoidLastCount > 0) {
          // The AI will keep using the avoid last strategy
          aiGuess = avoidLast.aiColour(colourCounts, playerLastChose);
          // Reset the avoid last count
          avoidLastCount = 0;
          avoidLastCount++;
        } else {
          aiGuess = random.aiColour(colourCounts, playerGuess);
        }
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

    // update the colour counts
    colourCounts.put(playerColour, colourCounts.get(playerColour) + 1);
    // set the last colour
    playerLastChose = playerColour;
    playerLastGuess = playerGuess;
    // set the last ai points
    aiLastpoints = aiPoints;
    if (currentRound > numRounds) {
      gameEnd = "Game ended";

      if (totalPlayerPoints > totalAiPoints) {
        MessageCli.PRINT_WINNER_GAME.printMessage(playerName);
      } else if (totalPlayerPoints < totalAiPoints) {
        MessageCli.PRINT_WINNER_GAME.printMessage(AI_NAME);
      } else {
        MessageCli.PRINT_TIE_GAME.printMessage();
      }
    }
  }

  public void showStats() {
    if (gameStart == null) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }
    MessageCli.PRINT_PLAYER_POINTS.printMessage(playerName, totalPlayerPoints);
    MessageCli.PRINT_PLAYER_POINTS.printMessage(AI_NAME, totalAiPoints);
  }
}
