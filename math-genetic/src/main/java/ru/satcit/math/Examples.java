package ru.satcit.math;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import ru.satcit.math.Function;

public class Examples {
  private List< double[] > valuesX = new ArrayList< double[] >();
  private List< Double > valuesY = new ArrayList< Double >();
  public static String EXAMPLES_SEPARATOR = ";";

  public static Examples initDouble( int n, double range, Function< Double > func ) throws Exception {
    Examples examples = new Examples();
//    double pos = range;
//    double step = range * 2 / ( n - 1 );
//    for( int i = 0; i < n; i++ ) {
//      List< Double > x = new ArrayList< Double >();
//      x.add( pos );
//      examples.valuesX.add( x );
//      examples.valuesY.add( func.calculate( pos ) );
//      pos += step;
//    }
    return examples;
  }

  public List< double[] > getX(){
    return valuesX;
  }

  public double[] getX( int exampleNum){
    return valuesX.get( exampleNum );
  }

  public double getX( int exampleNum, int n){
    return valuesX.get( exampleNum )[n];
  }

  public List< Double > getY() {
    return valuesY;
  }

  public double getY( int exampleNum ) {
    return valuesY.get( exampleNum );
  }

  public int size() {
    return valuesX.size();
  }

  public static Examples readFromFile( File file ) throws IOException {
    RandomAccessFile reader = new RandomAccessFile( file, "r" );
    Examples examples = new Examples();
    try {
      String exampleString = null;
      while ( ( exampleString = reader.readLine() ) != null ) {
        String[] tmp = exampleString.split( EXAMPLES_SEPARATOR );
        if( exampleString.indexOf( "//" ) != -1 ){
          tmp = exampleString.substring( 0, exampleString.indexOf( "//" ) ).split( EXAMPLES_SEPARATOR );
        }
        double[] exampleX = new double[ tmp.length - 1 ];
        for( int i = 0; i < tmp.length - 1; i++ ) {
          exampleX[ i ] = Double.valueOf( tmp[ i ] );
        }
        examples.valuesX.add( exampleX );
        examples.valuesY.add( Double.valueOf( tmp[ tmp.length - 1 ] ) );
      }
    } finally {
      if ( reader != null ) {
        reader.close();
      }
    }
    return examples;
  }
}
