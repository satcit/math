package ru.satcit.math;

import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import com.alt.model.fuzzy.set.FuzzySet;
import com.alt.model.production.rool.MamdaniRool;

public class MamdaniRoolBase extends AbstractRoolBase< MamdaniRool > {
  public MamdaniRoolBase( String name ) {
    super( name );
  }

  @Override
  public double makeGeneralConclusion( double[] x) throws Exception {
    List<FuzzySet> conclusions = new ArrayList<FuzzySet>();
    double leftBound = rools.get(0).makeConclusion(x).getLeftBound();
    double rigtBound = leftBound;
    for (int i = 0; i < rools.size(); i++) {
      conclusions.add(rools.get(i).makeConclusion(x));
      double tmpLeftBound = conclusions.get(i).getLeftBound();
      double tmpRightBound = tmpLeftBound + conclusions.get(i).getPeak();
      if (tmpLeftBound < leftBound) {
        leftBound = tmpLeftBound;
      }
      if (tmpRightBound > rigtBound) {
        rigtBound = tmpRightBound;
      }
    }
    double y = 0;
    double ySquare = 0;
    for (int i = 0; i < 100; i++) {
      double localMaxY = conclusions.get(0).fuzzificate(leftBound + i);
      for (int j = 0; j < conclusions.size(); j++) {
        double tmpY = conclusions.get(j).fuzzificate(leftBound + i);
        if (tmpY > localMaxY) {
          localMaxY = tmpY;
        }
      }
      y += localMaxY * (leftBound + i) * (rigtBound - leftBound) / 100;
      ySquare += localMaxY * (rigtBound - leftBound) / 100;
    }
    return y / ySquare;
  }

  @Override
  public void serialize( ObjectOutput out ) {
    // TODO Auto-generated method stub

  }
}
