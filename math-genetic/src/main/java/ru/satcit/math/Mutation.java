package ru.satcit.math;

import java.util.Random;

import ru.satcit.math.Pair;

public interface Mutation {
  void mutate( Individual individ, double mutationRate );

  public static final Mutation NULL = new Mutation() {
    public void mutate( Individual individ, double mutationRate ) {}

    @Override
    public String toString() {
      return "none";
    }
  };

  /***
   * ����������� �������
   */
  public static final Mutation UNIFORM = new Mutation() {
    public void mutate( Individual individ, double mutationRate ) {
      Individual tempIndividual = new Individual(individ.size());
      Random rnd = Utils.getRandom();
      boolean pass = true;
      do {
        pass = true;
        for( int i = 0; i < individ.size(); i++ ) {
          tempIndividual.set( i, individ.get( i )
              + rnd.nextDouble() * ( 2 * mutationRate ) - mutationRate );
          if ( Math.abs( tempIndividual.get( i ) ) > Population
              .getSearchSpace() ) {
            pass = false;
            break;
          }
        }
      } while ( pass == false );
      tempIndividual.copyTo( individ );
    }

    @Override
    public String toString() {
      return "Uniform";
    };
  };

  /***
   * �������� (����������) �������
   */
  public static final Mutation GAUSSIAN = new Mutation() {
    public void mutate( Individual individ, double mutationRate ) {
      Individual tempIndividual = new Individual(individ.size());
      Random rnd = Utils.getRandom();
      boolean pass;
      do {
        pass = true;
        Pair< Double, Double > rnd12 = Utils.boxMullerCast( 0, mutationRate );
        for( int i = 0; i < individ.size(); i++ ) {
          tempIndividual.set( i, individ.get( i )
              + ( rnd.nextDouble() > 0.5 ? rnd12.getFirst() : rnd12.getSecond() ) );
          if ( Math.abs( tempIndividual.get( i ) ) > Population
              .getSearchSpace() ) {
            pass = false;
            break;
          }
        }
      } while ( !pass );
      tempIndividual.copyTo( individ );
    }

    @Override
    public String toString() {
      return "Gaussian";
    }
  };
}
