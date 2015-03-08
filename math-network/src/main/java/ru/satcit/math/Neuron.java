package ru.satcit.math;

import java.util.List;
import java.util.Map;

public interface Neuron {
  double calculate();

  void reset();

  double[] getParametrs();

  void setParametrs( double[] parameters );

  Map< Neuron, Double > getPreviousNeurons();

  List< Neuron > getNextNeurons();

  double getConnectionWeight( Neuron connect );

  double getDelta();

  void setDelta( double delta );

  double getState();

  void setState( double state );

  Neuron SUMMATOR = new AbstractNeuron() {
    @Override
    protected double doCalculate( double x ) {
      return x;
    }
  };
}
