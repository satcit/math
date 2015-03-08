package ru.satcit.math;

import java.util.ArrayList;
import java.util.List;

public class TakagiSugenoNetwork {
  private List< Neuron > outputs = new ArrayList<Neuron>();
  private List< Neuron > inputs = new ArrayList<Neuron>();

  public double[] calculate( double[] x ) {
    resetState();
    for( int i = 0; i < inputs.size(); i++ ) {
      inputs.get( i ).setState( x[ i ] );
    }
    double[] result = new double[ outputs.size() ];
    for( int i = 0; i < outputs.size(); i++ ) {
      result[ i ] = outputs.get( i ).calculate();
    }
    return result;
  }

  public List< Neuron > getOutputs() {
    return outputs;
  }

  public List< Neuron > getInputs() {
    return inputs;
  }

  public void resetState() {
    for( Neuron out : outputs ) {
      out.reset();
    }
  }

  boolean study(double speed, double maxError, List<double[]> examples) {
    int n = 0;
    double[] Y;
    double error = 0;
    boolean endOfNet;
    List<Neuron> currentLauer;
    List<Neuron> nextLauer = new ArrayList<Neuron>();
    for (int i = 0; i < examples.size(); i++) { // �� ���� ��������
      this.reset();// ����� ��������� ��������
      Y = this.calculate(examples.get(i));
      error += Math.abs(Y[0] - examples.get(i)[examples.get(0).length - 1]);
    }
    while ((n < 10000) && (error > maxError)) {
      error = 0;
      for (int i = 0; i < examples.size(); i++) {// �� ���� ��������
        this.reset();// ����� ��������� ��������
        Y = this.calculate(examples.get(i));
        error += Math.abs(Y[0] - examples.get(0)[examples.get(0).length - 1]);
        endOfNet = false;
        currentLauer = exit;
        for (int j = 0; j < currentLauer.size(); j++) {
          currentLauer.get(j).setDelta(
              Y[j] * (1 - Y[j])
                  * (examples.get(0)[examples.get(0).length - 1] - Y[j]));
          // ������ ��� �� ���������, �.�. � ����� ������ �� ������ ������
          List<Double> weights = currentLauer.get(j).getWeights();
          for (int k = 0; k < exit.get(j).getPrevious().size(); k++) {
            weights.set(k, weights.get(k)
                + currentLauer.get(j).getPrevious().get(k).calculate()
                * currentLauer.get(j).getDelta() * speed);
            if (!nextLauer.contains(currentLauer.get(j).getPrevious().get(k))) {
              nextLauer.add(currentLauer.get(j).getPrevious().get(k));
            }
          }
          currentLauer.get(j).setWeights(weights);
        }// ���������� �� �������� ����

        currentLauer = nextLauer;
        nextLauer = new ArrayList<Neuron>();
        while (!endOfNet) {
          if (currentLauer.get(0).getWeights() == null) {
            endOfNet = true;
            break;
          }
          for (int j = 0; j < currentLauer.size(); j++) {
            currentLauer.get(j).setDelta(
                currentLauer.get(j).calculate()
                    * (1 - currentLauer.get(j).calculate())
                    * currentLauer.get(j).getDelta());
            List<Double> weights = currentLauer.get(j).getWeights();
            for (int k = 0; k < currentLauer.get(j).getPrevious().size(); k++) {
              weights.set(k, weights.get(k)
                  + currentLauer.get(j).getPrevious().get(k).calculate()
                  * currentLauer.get(j).getDelta() * speed);
              if (!nextLauer.contains(currentLauer.get(j).getPrevious().get(k))) {
                nextLauer.add(currentLauer.get(j).getPrevious().get(k));
              }
            }
          }
          currentLauer = nextLauer;
        }
      }
      n++;
    }
    if (n == 10000) {
      return false;
    }
    return true;
  }

  private boolean studyY( List<double[]> examples ) {
    //�������� ������� �� ��� �����
    //������ - ��������� ���������� �
    double[][] w = new double[examples.size()][];
    for( int i = 0; i< examples.size(); i++ ) {
      w[i] = new double[];
    }
    return false;
  }
}
