package ru.satcit.math;

public class LinearNeuron extends AbstractNeuron {

  public LinearNeuron( double a, double b, double w0 ) {
    this.parameters = new double[]{ a, b };
    this.w0 = w0;
  }

  @Override
  protected double doCalculate( double x ) {
    return parameters[ 0 ] * x + parameters[ 1 ];
  }
}
