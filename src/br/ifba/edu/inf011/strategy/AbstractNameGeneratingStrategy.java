package br.ifba.edu.inf011.strategy;

public abstract class AbstractNameGeneratingStrategy implements NameGeneratingStrategy {

  @Override
  public String toString() {
    return this.getStrategyName();
  }
}
