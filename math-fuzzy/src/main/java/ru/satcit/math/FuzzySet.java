package ru.satcit.math;

public interface FuzzySet extends Data{
  double fuzzificate(double x);

  double getPeak();

  double getLeftBound();

  double getA();

  double setA(double a);

  double getB();

  double setB(double b);
}
