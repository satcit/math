package ru.satcit.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractNeuron implements Neuron {

  protected double[] parameters;
  protected Map< Neuron, Double > in;
  protected List< Neuron > out;
  protected double state;
  public double w0 = 0;
  protected double delta;
  private boolean calculated;

  public AbstractNeuron() {
    in = new HashMap< Neuron, Double >();
    out = new ArrayList< Neuron >();
    state = -1;
    delta = 0;
  }

  public void reset() {
    calculated = false;
    state = 0;
    delta = 0;
    for( Neuron previous : in.keySet() ) {
      previous.reset();
    }
  }

  public double[] getParametrs() {
    return parameters;
  }

  public void setParametrs( double[] parameters ) {
    this.parameters = parameters;
  }

  public Map< Neuron, Double > getPreviousNeurons() {
    return in;
  }

  public List< Neuron > getNextNeurons() {
    return out;
  }

  public double getConnectionWeight( Neuron neuron ) {
    return in.get( neuron );
  }

  public double getDelta() {
    if ( delta == 0 ) {
      for( Neuron nextNeuron : out ) {
        delta += nextNeuron.getDelta()
            * nextNeuron.getConnectionWeight( this );
      }
    }
    return delta;
  }

  public void setDelta( double delta ) {
    this.delta = delta;
  }

  public double getState() {
    return state;
  }

  public void setState( double state ) {
    this.state = state;
  }

  public double calculate() {
    if( !calculated ) {
      double weightedSum = 0;
      for( Entry< Neuron, Double > previous : in.entrySet() ) {
        weightedSum += previous.getKey().calculate() * previous.getValue();
      }
      state = doCalculate( weightedSum - w0 );
      calculated = true;
    }
    return state;
  }

  protected abstract double doCalculate( double x );
}
