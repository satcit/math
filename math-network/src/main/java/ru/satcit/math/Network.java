package ru.satcit.math;

import java.util.List;

public interface Network {
  double[] calculate(double[] X);

  boolean study(double speed, double maxError, List<double[]> examples);

  void addExit(Neuron newExit);

  void deleteExit(Neuron newExit);

  void addSensor(Neuron newSensor);

  void deleteSensor(Neuron newSensor);

  int countSensors();

  void reset();
}
