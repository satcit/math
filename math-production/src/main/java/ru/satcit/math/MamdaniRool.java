package ru.satcit.math;

import java.util.ArrayList;
import java.util.List;

public class MamdaniRool extends AbstractRool {
  private FuzzySet consequent;
  private List< FuzzySet > antecedent;

  public MamdaniRool( String name, List< FuzzySet > antecedent, FuzzySet consequent ) {
    super( name );
    this.consequent = consequent;
    this.antecedent = antecedent;
  }

  public MamdaniRool() {
    super( "noName" );
    consequent = new TollerSet();
    antecedent = new ArrayList< FuzzySet >();
    antecedent.add( new TollerSet() );
  }

  public FuzzySet makeConclusion( double[] x ) throws Exception {
    return new TruncatedSet( consequent, getAssurence( x ) );
  }

  public double getAssurence( double[] x ) throws Exception {
    if ( x.length != antecedent.size() ) {
      throw new Exception( "Different size" );
    }
    double assurence = 1;
    double localAssurence = 0;
    for( int i = 0; i < x.length; i++ ) {
      if ( ( localAssurence = antecedent.get( i ).fuzzificate( x[i] ) ) < assurence ) {
        assurence = localAssurence;
      }
    }
    return assurence;
  }

  private double[] getA() {
    double[] aParam = new double[ antecedent.size() ];
    for( int i = 0; i < aParam.length; i++ ) {
      aParam[ i ] = antecedent.get( i ).getA();
    }
    return aParam;
  }

  private void setA( double[] A ) {
    for( int i = 0; i < A.length; i++ ) {
      antecedent.get( i ).setA( A[ i ] );
    }
  }

  private double[] getB() {
    double[] bParam = new double[ antecedent.size() ];
    for( int i = 0; i < bParam.length; i++ ) {
      bParam[ i ] = antecedent.get( i ).getB();
    }
    return bParam;
  }

  private void setB( double[] B ) {
    for( int i = 0; i < B.length; i++ ) {
      antecedent.get( i ).setB( B[ i ] );
    }
  }

  private double[] getConsequentParameters() {
    return new double[]{ consequent.getA(), consequent.getB() };
  }

  public double[] getParameters() {
    double[] aParameters = getA();
    double[] bParameters = getB();
    double[] cParameters = getConsequentParameters();
    double[] parameters = new double[ aParameters.length + bParameters.length + cParameters.length ];
    Utils.copyArray( aParameters, 0, parameters, 0                 , aParameters.length );
    Utils.copyArray( bParameters, 0, parameters, aParameters.length, bParameters.length );
    Utils.copyArray( cParameters, 0, parameters, aParameters.length + bParameters.length, cParameters.length );
    return parameters;
  }

  public void setParameters( double[] parameters ) {
    double[] cParameters = new double[ getConsequentParameters().length ];
    double[] aParameters = new double[ ( parameters.length - cParameters.length ) / 2 ];
    double[] bParameters = new double[ aParameters.length ];
    Utils.copyArray( parameters, 0                 , aParameters, 0, aParameters.length );
    Utils.copyArray( parameters, aParameters.length, bParameters, 0, bParameters.length );
    Utils.copyArray( parameters, aParameters.length + bParameters.length, cParameters, 0, cParameters.length );
    setA( aParameters );
    setB( bParameters );
    setConsequentParameters( cParameters );
  }

  private void setConsequentParameters( double[] cParameters ) {
    consequent.setA( cParameters[0] );
    consequent.setB( cParameters[1] );
  }

  public List< String > getAntecedentsNames() {
    List< String > names = new ArrayList< String >();
    for( FuzzySet set : antecedent ) {
      names.add( set.toString() );
    }
    return names;
  }

  public String getConsequentName() {
    return consequent.toString();
  }
}
