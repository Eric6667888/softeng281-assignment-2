package nz.ac.auckland.se281.engine;

import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

public interface AiStrategy {

  Colour aiColour(Map<Colour, Integer> colourCounts, Colour playerColour);
}
