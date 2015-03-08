package ru.satcit.math;

import java.awt.Point;

import ru.satcit.math.Vector;

public class Individual extends Vector {
  private double fitness;

  public static void swap( Individual ind1, Individual ind2 ) {
    Object[] dataTmp = ind1.elementData;
    ind1.elementData = ind2.elementData;
    ind2.elementData = dataTmp;
  }

  public Individual( int genotypeSize ) {
    super( genotypeSize );
    for( int i = 0; i < genotypeSize; i++ ) {
      add( Utils.getRandom().nextDouble() * ( 2 * Population.getSearchSpace() ) - Population.getSearchSpace() );
    }
    fitness = 0;
  }

  public double getFitness() {
    return fitness;
  }

  public void copyTo( Individual destination ) {
    destination.fitness = fitness;
    destination.clear();
    for( double gen : this ){
      destination.add( gen );
    }
  }

  public double calculateFitness( GeneticTask task ) {
    fitness = task.calculateFitness( this );
    return fitness;
  }

  // ������������� ������� ����� � ���������� ����� �� ���������
  public Point genotypeToPoint( int x, int y ) {
    Point point = new Point();
    point.x = (int) ( get(x - 1) * 20 * 10 / Population.getSearchSpace() + 220 );
    point.y = (int) ( -get(y-1) * 20 * 10 / Population.getSearchSpace() + 220 );
    return point;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for( int i = 0; i < size(); i++ ) {
      stringBuilder
      .append( i < 9 ? "  " : "" )
      .append( "[" ).append( i + 1 )
      .append( "]  =  " ).append( get(i) )
      .append( "\n" );
    }
    // �������� ��������� ������ ('\n')
    stringBuilder.setLength( stringBuilder.capacity() - 1 );
    return stringBuilder.toString();
  }
}