package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class EasyAiStrategy implements AiStrategy {

  @Override
  public Colour aiChooseColour() {
    // Easy AI strategy: Randomly choose a colour
    // The AI will choose a colour randomly from the available colours
    Colour aiColour = Colour.getRandomColourForAi();
    return aiColour;
  }
}
