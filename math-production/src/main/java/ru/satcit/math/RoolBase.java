package ru.satcit.math;

import java.io.Serializable;
import java.util.List;

public interface RoolBase< T extends Rool > extends Data, Serializable {
  double makeGeneralConclusion(double[] x) throws Exception;
  List< T > getRools();
  void setExamples( Examples examples );
}
