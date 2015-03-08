package ru.satcit.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SugenoRool extends AbstractRool {
  private List< Double > consequent;// ��� ��������� �
  private String consequentName;
  private List< FuzzySet > antecedent;

  public SugenoRool( String name, List< FuzzySet > antecedent, List< Double > consequent, String consequentName ){
    super( name );
    this.consequent = consequent;
    this.consequentName = consequentName;
    this.antecedent = antecedent;
  }

  public SugenoRool() {
    super("noName" + AbstractRool.roolsCount);
    consequent = Arrays.asList(1.0);
    antecedent = new ArrayList< FuzzySet >();
    antecedent.add( new TollerSet() );
  }

  public Double makeConclusion( double[] x ) {//��� ���������� �� �������
    double conclusion = 0;
    for( int i = 1; i <= x.length; i++ ) {
      conclusion += x[i-1]*consequent.get( i );
    }
    return conclusion + consequent.get( 0 );
  }

  public double getAssurence( double[] x ) throws Exception {
    if ( x.length != antecedent.size() || x.length != consequent.size() - 1 ) {
      throw new Exception( "Different size" );
    }
    double assurence = 1;
    for( int i = 0; i < x.length; i++ ) {
      assurence *= antecedent.get( i ).fuzzificate( x[i] );
    }
    return assurence;
  }

  public double[] getA() {
    double[] aParam = new double[ antecedent.size() ];
    for( int i = 0; i < aParam.length; i++ ) {
      aParam[ i ] = antecedent.get( i ).getA();
    }
    return aParam;
  }

  public void setA( double[] A ) {
    for( int i = 0; i < A.length; i++ ) {
      antecedent.get( i ).setA( A[ i ] );
    }

  }

  public double[] getB() {
    double[] bParam = new double[ antecedent.size() ];
    for( int i = 0; i < bParam.length; i++ ) {
      bParam[ i ] = antecedent.get( i ).getB();
    }
    return bParam;
  }

  public void setB( double[] B ) {
    for( int i = 0; i < B.length; i++ ) {
      antecedent.get( i ).setB( B[ i ] );
    }
  }

  public double[] getParameters() {
    double[] aParameters = getA();
    double[] bParameters = getB();
    double[] cParameters = getCParameters();
    double[] parameters = new double[ aParameters.length + bParameters.length + cParameters.length ];
    Utils.copyArray( aParameters, 0, parameters, 0                 , aParameters.length );
    Utils.copyArray( bParameters, 0, parameters, aParameters.length, bParameters.length );
    Utils.copyArray( cParameters, 0, parameters, aParameters.length + bParameters.length, cParameters.length );
    return parameters;
  }

  public void setParameters( double[] parameters ) {
    int cCount = consequent.size();
    double[] aParameters = new double[ ( parameters.length - cCount) / 2 ];
    double[] bParameters = new double[ ( parameters.length - cCount) / 2 ];
    double[] cParameters = new double[ cCount ];
    Utils.copyArray( parameters, 0                 , aParameters, 0, aParameters.length );
    Utils.copyArray( parameters, aParameters.length, bParameters, 0, bParameters.length );
    Utils.copyArray( parameters, aParameters.length + bParameters.length, cParameters, 0, cParameters.length );
    setA( aParameters );
    setB( bParameters );
    setCParameters( cParameters );
  }

  public void setCParameters( double[] parameters ) {
    consequent.clear();
    for( double parameter : parameters) {
      consequent.add( parameter );
    }
  }

  public double[] getCParameters(){
    return Utils.toArray( consequent );
  }

  public List< String > getAntecedentsNames() {
    List< String > names = new ArrayList< String >();
    for( FuzzySet set : antecedent ) {
      names.add( set.toString() );
    }
    return names;
  }

  public String getConsequentName() {
    return consequentName;
  }
}
