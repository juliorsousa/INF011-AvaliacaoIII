package br.ifba.edu.inf011.strategy;

public abstract class AbstractNameGenerator implements NameGenerator {

  @Override
  public String toString() {
    return this.getStrategyName();
  }

}
