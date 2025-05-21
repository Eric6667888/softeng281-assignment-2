package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public class AvoidLastStrategy implements AiStrategy {

  @Override
  public Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour) {
    // Medium AI strategy: random in the list exclude the player's colour for the previous round
    Colour aiColour = Colour.getRandomColourExcluding(playerColour);

    return aiColour;
  }
}
