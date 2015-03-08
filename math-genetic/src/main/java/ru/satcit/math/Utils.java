package ru.satcit.math;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ru.satcit.math.Pair;

public class Utils {
  private static final Random rnd = new Random( System.currentTimeMillis() );

  /**
   *  use it for generating standard normal distribution
   */
  public static Pair< Double, Double > boxMullerCast( double alpha,
      double sigma) {
    double s, rnd1, rnd2;
    do {
      rnd1 = 1 - rnd.nextDouble();
      rnd2 = 1 - rnd.nextDouble();
      s = Math.pow( rnd1, 2 ) + Math.pow( rnd2, 2 );
    } while ( s > 1 || s == 0 );
    rnd1 = alpha + rnd1 * Math.sqrt( -2 * Math.log( s ) / s ) * sigma;
    rnd2 = alpha + rnd2 * Math.sqrt( -2 * Math.log( s ) / s ) * sigma;
    return new Pair< Double, Double >( rnd1, rnd2 );
  }

  public static double min( double[] array ) {
    double minimum = array[ 0 ];
    for( double element : array ) {
      if ( element < minimum ) {
        minimum = element;
      }
    }
    return minimum;
  }

  public static double max( double[] array ) {
    double maximum = array[ 0 ];
    for( double element : array ) {
      if ( element > maximum ) {
        maximum = element;
      }
    }
    return maximum;
  }

  public static double sum( double[] array ) {
    double sum = 0;
    for( double element : array ) {
      sum += element;
    }
    return sum;
  }

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

  public static double[] findRootsGaussMethod( double[][] a, double[] b ) {
    int n = a.length;//���������� �����
    int m = a[0].length;//���������� ��������

    double[][] aCopy = new double[ n ][];
    for( int i = 0; i < n; i++ ) {
      aCopy[ i ] = new double[ m ];
      for( int j = 0; j < m; j++ ) {
        aCopy[ i ][ j ] = a[ i ][ j ];
      }
    }

    double[] bCopy = new double[ n ];
    for( int i = 0; i < n; i++ ) {
      bCopy[ i ] = b[ i ];
    }

    int[] indices = new int[m];//������� ��������� ��������
    List< Integer > usedRows = new ArrayList<Integer>();
    for( int i = 0; i < m; i++ ) {//��������� �� ��������
      double max = 0;
      int maxJ = 0;//������ � ��������� ���������
      for( int j = 0; j < n; j++ ) {
        if( !usedRows.contains( j ) && Math.abs( aCopy[j][i] ) > max ){
          max = Math.abs( aCopy[j][i] );
          maxJ = j;
        }
      }
      indices[i] = maxJ;
      usedRows.add( maxJ );
      for( int j = 0; j < n; j++ ) {//������ ������
        if( j != maxJ ){
          double modifier = aCopy[ j ][ i ];
          for( int k = 0; k < m; k++ ) {//�������� � ������ �� ������� ��������
            if( aCopy[ maxJ ][ i ] != 0 ) {
              aCopy[ j ][ k ] -= aCopy[ maxJ ][ k ] *//��� �������
              modifier /  //���������� �������
              aCopy[ maxJ ][ i ];//������������
            }
            if( Math.abs( aCopy[ j ][ k ] ) < 0.00000001 ) {
              aCopy[ j ][ k ] = 0;
            }
          }
          bCopy[ j ] -= bCopy[ maxJ ] * modifier / aCopy[ maxJ ][ i ];
          if( Math.abs( bCopy[ j ] ) < 0.00000001 ) {
            bCopy[ j ] = 0;
          }
        }
      }
    }
    double[] result = new double[m];
    for( int i = 0; i < m; i++ ) {
      result[ i ] = bCopy[ indices[ i ] ] / aCopy[ indices[ i ] ][ i ];
    }
    return result;
  }

  public static final double[][] invert( double realA[][] ) {
    double[][] A = makeCopy( realA );
    int n = A.length;
    int row[] = new int[ n ];
    int col[] = new int[ n ];
    double temp[] = new double[ n ];
    int hold, I_pivot, J_pivot;
    double pivot, abs_pivot;

    if ( A[ 0 ].length != n ) {
      System.out.println( "Error in Matrix.invert, inconsistent array sizes." );
    }
    // set up row and column interchange vectors
    for( int k = 0; k < n; k++ ) {
      row[ k ] = k;
      col[ k ] = k;
    }
    // begin main reduction loop
    for( int k = 0; k < n; k++ ) {
      // find largest element for pivot
      pivot = A[ row[ k ] ][ col[ k ] ];
      I_pivot = k;
      J_pivot = k;
      for( int i = k; i < n; i++ ) {
        for( int j = k; j < n; j++ ) {
          abs_pivot = Math.abs( pivot );
          if ( Math.abs( A[ row[ i ] ][ col[ j ] ] ) > abs_pivot ) {
            I_pivot = i;
            J_pivot = j;
            pivot = A[ row[ i ] ][ col[ j ] ];
          }
        }
      }
      if ( Math.abs( pivot ) < 1.0E-30 ) {
        System.out.println( "Matrix is singular !" );
        return null;
      }
      hold = row[ k ];
      row[ k ] = row[ I_pivot ];
      row[ I_pivot ] = hold;
      hold = col[ k ];
      col[ k ] = col[ J_pivot ];
      col[ J_pivot ] = hold;
      // reduce about pivot
      A[ row[ k ] ][ col[ k ] ] = 1.0 / pivot;
      for( int j = 0; j < n; j++ ) {
        if ( j != k ) {
          A[ row[ k ] ][ col[ j ] ] = A[ row[ k ] ][ col[ j ] ]
              * A[ row[ k ] ][ col[ k ] ];
        }
      }
      // inner reduction loop
      for( int i = 0; i < n; i++ ) {
        if ( k != i ) {
          for( int j = 0; j < n; j++ ) {
            if ( k != j ) {
              A[ row[ i ] ][ col[ j ] ] = A[ row[ i ] ][ col[ j ] ]
                  - A[ row[ i ] ][ col[ k ] ] * A[ row[ k ] ][ col[ j ] ];
            }
          }
          A[ row[ i ] ][ col[ k ] ] = -A[ row[ i ] ][ col[ k ] ]
              * A[ row[ k ] ][ col[ k ] ];
        }
      }
    }
    // end main reduction loop

    // unscramble rows
    for( int j = 0; j < n; j++ ) {
      for( int i = 0; i < n; i++ ) {
        temp[ col[ i ] ] = A[ row[ i ] ][ j ];
      }
      for( int i = 0; i < n; i++ ) {
        A[ i ][ j ] = temp[ i ];
      }
    }
    // unscramble columns
    for( int i = 0; i < n; i++ ) {
      for( int j = 0; j < n; j++ ) {
        temp[ row[ j ] ] = A[ i ][ col[ j ] ];
      }
      for( int j = 0; j < n; j++ ) {
        A[ i ][ j ] = temp[ j ];
      }
    }
    return A;
  }

  public static final double[][] makeCopy( final double a[][] ) {
    int rows = a.length;
    int cols = rows > 0 ? a[ 0 ].length : 0;
    double[][] aCopy = new double[ rows ][ cols ];

    for( int i = 0; i < rows; i++ ) {
      for( int j = 0; j < cols; j++ ) {
        aCopy[ i ][ j ] = a[ i ][ j ];
      }
    }
    return aCopy;
  }

  public static final double[][] multiply( final double a[][], final double b[][] ) {
    int rowsA = a.length;
    int colsA = rowsA > 0 ? a[ 0 ].length : 0;
    int colsB = b[ 0 ].length;
    double c[][] = new double[rowsA][colsB];
    if ( b.length != colsA || c.length != rowsA || c[ 0 ].length != colsB ) {
      System.out.println( "Error in Matrix.multiply, incompatible sizes" );
    }
    for( int i = 0; i < rowsA; i++ ) {
      for( int j = 0; j < colsB; j++ ) {
        c[ i ][ j ] = 0.0;
        for( int k = 0; k < colsA; k++ ) {
          c[ i ][ j ] += a[ i ][ k ] * b[ k ][ j ];
        }
      }
    }
    return c;
  }

  public static final double[] multiply( double a[][], double b[] ) {
    int rows = a.length;
    int cols = rows > 0 ? a[ 0 ].length : 0;
    double[] c = new double[ rows ];
    for( int i = 0; i < rows; i++ ) {
      c[ i ] = 0.0;
      for( int j = 0; j < cols; j++ ) {
        c[ i ] += a[ i ][ j ] * b[ j ];
      }
    }
    return c;
  }

  public static final double[][] transposition( double[][] a ) {
    int rows = a.length;
    int cols = rows > 0 ? a[ 0 ].length : 0;
    double[][] transA = new double[ cols ][ rows ];
    for( int i = 0; i < rows; i++ ) {
      for( int j = 0; j < cols; j++ ) {
        transA[ j ][ i ] = a[ i ][ j ];
      }
    }
    return transA;
  }

  public static void print(PrintStream out, double[][] a) {
    String ln;
    for (int j = 0; j < a.length; j++) {
      ln = "";
      for (int i = 0; i < a[0].length; i++) {
        ln += a[i][j] + "\t";
      }
      out.println(ln);
    }
  }

  public static Random getRandom() {
    return rnd;
  }

}
