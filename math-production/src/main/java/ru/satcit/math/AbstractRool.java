package ru.satcit.math;


public abstract class AbstractRool implements Rool {
  protected static int roolsCount = 0;
  private String name;

  public AbstractRool( String name ) {
    AbstractRool.roolsCount++;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }
}
