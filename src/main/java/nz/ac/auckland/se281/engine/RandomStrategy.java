package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public class RandomStrategy implements AiStrategy {

  @Override
  public Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour) {
    // Easy AI strategy: Randomly choose a colour
    // The AI will choose a colour randomly from the available colours
    Colour aiColour = Colour.getRandomColourForAi();
    return aiColour;
  }
}
