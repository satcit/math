package ru.satcit.math;

import java.util.Collection;
import java.util.Iterator;

public class Utils {
  public static void copyArray(double[] source, int sourceIndex,
      double[] destination, int destinationIndex, int length) {
    for (int i = 0; i < length; i++) {
      destination[destinationIndex + i] = source[sourceIndex + i];
    }
  }

  public static double[] toArray( Collection< Double > collection ) {
    double[] array = new double[ collection.size() ];
    Iterator< Double > iterator = collection.iterator();
    for( int i = 0; iterator.hasNext(); i++ ) {
      array[ i ] = iterator.next();
    }
    return array;
  }
}
