package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private static int numRounds;
  private int currentRound;
  private String playerName;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.playerName = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    this.numRounds = numRounds;
    this.currentRound = 1;
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
  }

  public void play() {}

  public void showStats() {}
}
