package ru.satcit.math;

import java.io.IOException;
import java.io.ObjectOutput;

import com.alt.model.production.rool.Rool;
import com.alt.model.production.rool.SugenoRool;
import com.alt.utils.Utils;

public class SugenoRoolBase extends AbstractRoolBase< SugenoRool > {

  public SugenoRoolBase( String name ) {
    super( name );
  }

  @Override
  public double makeGeneralConclusion( double[] x ) throws Exception {
    double assuranceSum = 0;
    double result = 0;
    for( SugenoRool rool : rools ) {
      double localAssurance = rool.getAssurence( x );
      result += rool.makeConclusion( x ) * localAssurance;
      assuranceSum += localAssurance;
    }
    return result / assuranceSum;
  }

  public boolean study(double speed, double maxError) throws Exception {
    int n = 0;

    //�������� ������� �� ��� �����
    while ((n < 10000) && (calculateError( examples ) > maxError)) {
      //������ ��� - ���������� ���������� ���������� �������� ���� ������� ������
      double[][] c = studyY();
      for( int i = 0; i < rools.size(); i++ ) {
        rools.get( i ).setCParameters( c[i] );
      }
      //������ ��� - ���������� ���������� ������� �������������� � ������������
      //������� �������������� ������
      double[][] aParameters = new double[rools.size()][];
      double[][] bParameters = new double[rools.size()][];
      for (int i = 0; i < examples.size(); i++) {// �� ���� ��������

        double alfaSum = 0;
        double weightedAlfaSum = 0;//yi*alfai
        double ySum = 0;
        double[] y = new double[ rools.size() ];
        double[] alfa = new double[ rools.size() ];

        for( int j=0; j < rools.size(); j++ ) {
          alfa[ j ] = rools.get( j ).getAssurence( examples.getX( i ) );
          alfaSum += alfa[ j ];
          y[j] = rools.get( j ).makeConclusion( examples.getX( i ) );
          ySum += y[j];
          weightedAlfaSum += y[j] * alfa[ j ];
        }
        for( int j = 0; j < rools.size(); j++ ) { // ���������� ���������, ������ ����� ������� �� ����������
          aParameters[j] = new double[ rools.get( j ).getA().length ];
          bParameters[j] = new double[ rools.get( j ).getB().length ];
          double beta = y[j] * alfaSum - weightedAlfaSum;// beta i
          double t = 2 * alfa[j] * beta / (alfaSum * alfaSum);
          double[] aLocal = rools.get( j ).getA();
          double[] bLocal = rools.get( j ).getB();
          Utils.copyArray( aLocal, 0, aParameters[j], 0, aLocal.length );
          Utils.copyArray( bLocal, 0, bParameters[j], 0, bLocal.length );
          for( int k = 0; k < aParameters[j].length; k++ ){
            double l = ( examples.getX( i, k ) - aLocal[k] ) / bLocal[ k ];
            aParameters[j][k] -= speed * t * l/ bLocal[ k ];
            bParameters[j][k] -= speed * t * l*l/bLocal[ k ];
          }
        }

      }
      //��������� ���������� � � b
      for( int i=0;i<rools.size();i++ ) {
        rools.get( i ).setA( aParameters[i] );
        rools.get( i ).setB( bParameters[i] );
      }
      n++;
    }
    if (n == 10000) {
      return false;
    }
    return true;
  }

  private double[][] studyY() throws Exception {
    double[][] w = new double[examples.size()][];
    int xDimension = examples.getX( 0 ).length;
    for( int i = 0; i < examples.size(); i++ ) {
      double assuranceSum = 0;
      for( Rool rool : rools ){
        assuranceSum += rool.getAssurence( examples.getX().get( i ) );
      }
      w[i] = new double[rools.size() * ( xDimension + 1 )];// +1 ��� �0

      for( int j = 0; j < rools.size(); j++ ) {
        double wi = rools.get( j ).getAssurence( examples.getX().get( i ) ) / assuranceSum;
        w[i][j*( xDimension + 1)] = wi;// ��� �0
        for( int k = 1; k <= xDimension; k++ ) {
          w[i][j*( xDimension + 1) + k] = examples.getX(i,k - 1) * wi;
        }
      }
    }
    //���� �������� ����� ������ ��� ������*����������� �, �� ����������� ������ ���������� ��� ����
    double[] cTmp = Utils.findRootsGaussMethod( w,
        Utils.toArray( examples.getY() ) );
    double[][] c = new double[ rools.size() ][];
    for( int i = 0; i < rools.size(); i++ ) {
      c[ i ] = new double[ xDimension + 1 ];
      Utils.copyArray( cTmp, i * ( xDimension + 1 ), c[ i ], 0, c[ i ].length );
    }
//    // it is a solution of linear system W*c = y, we cast this expression to c = (WT*W)^(-1)*WT*y. this method is called as Moore�Penrose pseudoinverse
//    double[] cTmp = Utils.multiply( Utils.multiply( Utils.invert( Utils.multiply( Utils.transposition( w ), w ) ), Utils.transposition( w )), Utils.toArray( examples.getY() ) );
//    double[][] c = new double[ rools.size() ][];
//    for( int i = 0; i < rools.size(); i++ ) {
//     c[ i ] = new double[ xDimension + 1 ];
//     Utils.copyArray( cTmp, i * ( xDimension + 1 ), c[ i ], 0, c[ i ].length );
//    }
    return c;
  }

  @Override
  public void serialize( ObjectOutput out ) {
    try {
      out.writeObject( rools );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }
}
