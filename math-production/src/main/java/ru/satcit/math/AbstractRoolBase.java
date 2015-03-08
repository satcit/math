package ru.satcit.math;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRoolBase< T extends Rool > implements RoolBase< T >, GeneticTask {
  protected List< T > rools = new ArrayList< T >();
  protected Examples examples;
  private String name;

  public AbstractRoolBase( String name ) {
    this.name = name;
  }

  public abstract double makeGeneralConclusion( double[] x )
      throws Exception;

  public List< T > getRools(){
    return rools;
  }

  public double calculateError( Examples examples ) throws Exception {
    double sumError = 0;
    for( int i = 0; i < examples.size(); i++ ) {
      sumError += Math.pow(
          examples.getY( i ) - makeGeneralConclusion( examples.getX( i ) ), 2 );
    }
    return sumError;
  }

  @Override
  public String toString() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public void setExamples( Examples examples ) {
   this.examples = examples;
  }

  public double run( Individual individ ) {
    int roolParametersCount = rools.get( 0 ).getParameters().length;
    for( int i = 0; i < rools.size(); i++ ){
      double[] localParameters = new double[ roolParametersCount ];
      Utils.copyArray( individ.getGenotype(), i * roolParametersCount, localParameters, 0, roolParametersCount );
      rools.get( i ).setParameters( localParameters );
    }
    try {
      return -calculateError( examples );
    } catch ( Exception e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
  }

  public int getGenotypeLength() {
    return rools.isEmpty() ? 0 : rools.size()*rools.get( 0 ).getParameters().length;
  }

  public abstract void serialize( ObjectOutput out );

  public static AbstractRoolBase deserialize( ObjectInput in ) throws ClassNotFoundException, IOException{

    String name = (String)in.readObject();
    return null;
  }
}
