package ru.satcit.math;

public class TruncatedSet implements FuzzySet {

  private FuzzySet fSet;
  private double bound;

  public TruncatedSet( FuzzySet fSet, double bound ) {
    this.bound = bound;
    this.fSet = fSet;
  }

  public double fuzzificate( double x ) {
    double tmpY = fSet.fuzzificate( x );
    return tmpY > bound ? bound : tmpY;
  }

  public double getPeak() {
    return fSet.getPeak();
  }

  @Override
  public String toString() {
    return fSet.toString();
  }

  public double getLeftBound() {
    return fSet.getLeftBound();
  }

  public double getA() {
    return fSet.getA();
  }

  public double setA( double a ) {
    return fSet.setA( a );
  }

  public double getB() {
    return fSet.getB();
  }

  public double setB( double b ) {
    return fSet.setB( b );
  }

  public void setName( String name ) {
    fSet.setName( name );
  }
}
