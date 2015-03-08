package ru.satcit.math;

import java.util.Random;

public abstract class AbstractFuzzySet implements FuzzySet {

  private double a, b;
  private String name = "noname";
  protected static Random rnd = new Random(System.currentTimeMillis());

  public AbstractFuzzySet( double a, double b, String name ) {
    this.a = a;// center
    this.b = b;// tilt
    this.name = name;
  }

  public AbstractFuzzySet() {
    this.a = 0;
    this.b = 1;
  }

  public double getPeak() {
    return b;
  }

  public abstract double fuzzificate( double x );

  @Override
  public String toString() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public abstract double getLeftBound();

  public double getB() {
    return b;
  }

  public double setB( double b ) {
    double prevB = b;
    this.b = b;
    return prevB;
  }

  public double getA() {
    return a;
  }

  public double setA( double a ) {
    double prevA = a;
    this.a = a;
    return prevA;
  }
}
