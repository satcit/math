package ru.satcit.math;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkImpl implements Network {
  protected List<Neuron> exit;
  protected List<Neuron> enter;

  public NetworkImpl() {
    exit = new ArrayList<Neuron>();
    enter = new ArrayList<Neuron>();
  }

  public abstract boolean study(double speed, double maxError,
      List<double[]> examples);

  public void addExit(Neuron newexit) {
    exit.add(newexit);
  }

  public void deleteExit(Neuron exit) {
    this.exit.remove(exit);
  }

  public void addSensor(Neuron newsensor) {
    enter.add(newsensor);
  }

  public void deleteSensor(Neuron sensor) {
    enter.remove(sensor);
  }

  public int countSensors() {
    return enter.size();
  }

  public void reset() {
    for (Neuron neuron : exit) {
      neuron.reset();
    }
  }

  public double[] calculate(double[] X) {
    for (int i = 0; i < exit.size(); i++) {
      exit.get(i).setState(X[i]);
    }
    double[] y = new double[exit.size()];
    for (int i = 0; i < exit.size(); i++) {
      y[i] = exit.get(i).calculate();
    }
    return y;
  }
}
