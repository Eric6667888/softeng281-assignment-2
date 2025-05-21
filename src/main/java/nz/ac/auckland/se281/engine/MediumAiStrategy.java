package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class MediumAiStrategy implements AiStrategy {
  @Override
  public Colour aiChooseColour(Colour playerColour) {
    // Medium AI strategy: random in the list exclude the player's colour for the previous round

    Colour aiColour = Colour.getRandomColourExcluding(playerColour);

    return aiColour;
  }

  @Override
  public Colour aiGuessColour(Colour playerColour) {
    // Medium AI strategy: random in the list exclude the player's colour for the previous round
    Colour aiGuess = Colour.getRandomColourExcluding(playerColour);

    return aiGuess;
  }
}
