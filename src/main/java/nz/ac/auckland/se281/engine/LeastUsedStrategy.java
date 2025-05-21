package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public class LeastUsedStrategy implements AiStrategy {

  @Override
  public Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour) {
    int minCount = Integer.MAX_VALUE;

    for (int count : colourCounts.values()) {
      if (count < minCount) {
        minCount = count;
      }
    }

    for (Colour colour : Colour.values()) {
      int count = colourCounts.getOrDefault(colour, 0);
      if (count == minCount) {
        return colour;
      }
    }
    return null;
  }
}
