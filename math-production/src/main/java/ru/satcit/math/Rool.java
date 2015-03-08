package ru.satcit.math;

import java.util.List;

public interface Rool extends Data {
  Object makeConclusion( double[] x ) throws Exception;
  double getAssurence( double[] x) throws Exception;
  double[] getParameters();
  void setParameters( double[] parameters );
  List< String > getAntecedentsNames();
  String getConsequentName();
}
