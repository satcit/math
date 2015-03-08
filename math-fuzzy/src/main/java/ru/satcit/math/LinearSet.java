package ru.satcit.math;

public class LinearSet extends AbstractFuzzySet {
  public LinearSet(double a, double b, String name) {
    super(a, b, name);
  }

  public LinearSet() {
    super();
  }

  @Override
  public double fuzzificate(double x) {
    double y1 = 1 / getB() * (x + getB() - getA());
    double y2 = -1 / getB() * (x - getB() - getA());
    if ((y1 >= 0) && (y1 <= 1)) {
      return y1;
    }
    if ((y2 >= 0) && (y2 <= 1)) {
      return y2;
    }
    return 0;
  }

  @Override
  public double getLeftBound() {
    return getA() - getB();
  }
}
