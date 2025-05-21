package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public interface AiStrategy {
  // This method will be called to get the AI's colour choice
  // The AI will use the colourCounts map to determine which colour to choose
  Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour);
}
