package ru.satcit.math;

public class TollerNeuron extends AbstractNeuron {

  public TollerNeuron( double a, double b, double w0 ) {
    this.parameters = new double[]{ a, b };
    this.w0 = w0;
  }

  @Override
  protected double doCalculate( double x ) {
    double realY = Math.exp(-Math.pow((x - parameters[0]) / parameters[1], 2));
    return realY > 0.0001 ? realY : 0;//TODO maybe make general precision?
  }
}
