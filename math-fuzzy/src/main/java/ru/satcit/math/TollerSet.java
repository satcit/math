package ru.satcit.math;


public class TollerSet extends AbstractFuzzySet {

  public TollerSet(double a, double b, String name) {
    super(a, b, name);
  }

  public TollerSet() {
    super();
    this.setA( 8 * rnd.nextDouble() - 4 );
    this.setB( 2 );
  }

  @Override
  public double fuzzificate(double x) {
    double realY = Math.exp(- Math.pow((x - getA()) / getB(), 2));
    return realY > 0.0001 ? realY : 0;
  }

  @Override
  public double getLeftBound() {
    return getA() - 4.2919 * getB();// accuracy x is 0.0001
  }
}
