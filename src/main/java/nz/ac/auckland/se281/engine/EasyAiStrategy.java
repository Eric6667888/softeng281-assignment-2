package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class EasyAiStrategy implements AiStrategy {

  @Override
  public Colour aiChooseColour(Colour playerColour) {
    // Easy AI strategy: Randomly choose a colour
    // The AI will choose a colour randomly from the available colours
    Colour aiColour = Colour.getRandomColourForAi();
    return aiColour;
  }

  @Override
  public Colour aiGuessColour(Colour playerColour) {
    // Easy AI strategy: Randomly choose a colour
    // The AI will guess the player's colour randomly from the available colours
    Colour aiGuess = Colour.getRandomColourForAi();
    return aiGuess;
  }
}
