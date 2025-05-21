package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public class LeastUsedStrategy implements AiStrategy {

  @Override
  public Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour) {
    int minCount = Integer.MAX_VALUE;

    for (int count : colourCounts.values()) {
      // Find the minimum count of colours
      if (count < minCount) {
        minCount = count;
      }
    }

    for (Colour colour : Colour.values()) {
      // Find the colour with the minimum count
      int count = colourCounts.getOrDefault(colour, 0);
      if (count == minCount) {
        return colour;
      }
    }
    return null;
  }
}
