package ru.satcit.math;

public class Vector extends java.util.Vector<Double> {

  public Vector( int size ) {
    super(size);
    for( int i = 0; i < size; i++ ) {
      add( 0D );
    }
  }

  public Vector( double[] data ) {
    super( data.length );
    for( double d : data ) {
      add( d );
    }
  }

  public boolean isNull() {
    for (double component : this) {
      if (component != 0) {
        return false;
      }
    }
    return true;
  }

  public Vector plus(Vector e1) {
    if( size() != e1.size() ) {
      throw new UnsupportedOperationException( "vectors have different sizes" );
    }
    double[] data = new double[e1.size()];
    for (int i = 0; i < data.length; i++) {
      data[i] = get( i ) + e1.get( i );
    }
    return new Vector( data );
  }

  public Vector minus(Vector e1) {
    if( size() != e1.size() ) {
      throw new UnsupportedOperationException( "vectors have different sizes" );
    }
    double[] data = new double[e1.size()];
    for (int i = 0; i < data.length; i++) {
      data[i] = get( i ) - e1.get( i );
    }
    return new Vector( data );
  }

  public Vector multicipation(double a) {
    double[] data = new double[size()];
    for (int i = 0; i < data.length; i++) {
      data[i] = a * get( i );
    }
    return new Vector( data );
  }

  public double scalarMultiply(Vector v) {
    double result = 0;
    for (int i = 0; i < size(); i++) {
      result += get( i ) * v.get( i );
    }
    return result;
  }

  public double vectorLength() {
    return scalarMultiply(this);
  }

  // FIXME what a hell is this?
  public Vector[] orthonormalization() {
    Vector[] e = new Vector[size() - 1];
    if ( isEmpty() ) {
      for (int i = 0; i < e.length; i++) {
        e[i] = new Vector( size() );
      }
    } else {
      // ���������� ������
      boolean pass = false;
      int k = 0;
      for (int i = 0; i < size(); i++) {
        if ( !pass && (get( i ) != 0)) {
          pass = true;
        } else {
          e[k] = new Vector(size());
          e[k].set(i, 1D);
          k++;
        }
      }

      // ��������������� ������� ������-������
      Vector sum = new Vector(size());
      for (int i = 0; i < e.length; i++) {
        sum = multicipation( scalarMultiply( e[i] ) / vectorLength() );
        for (int j = 0; j < i; j++) {
          sum = sum.plus(e[j].multicipation((e[i].scalarMultiply(e[j]) / e[j]
              .vectorLength())));
        }
        e[i] = e[i].minus(sum);
      }

      // basis normalization
      for (int i = 0; i < e.length; i++) {
        e[i] = e[i].multicipation( 1 / Math.sqrt( e[i].vectorLength() ) );
      }
    }
    return e;
  }
}
