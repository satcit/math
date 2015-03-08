package ru.satcit.math;

import java.util.Random;

public interface Selection {
  Random rnd = new Random( System.currentTimeMillis() );

  void select( Individual father, Individual mother, Individual[] offsprings );

  public static final Selection TOURNAMENT = new Selection() {
    public void select( Individual father, Individual mother,
        Individual[] offsprings ) {
      for( Individual offspring : offsprings ) {
        if ( ( offspring.getFitness() > father.getFitness() )
            || ( offspring.getFitness() > mother.getFitness() ) ) {
          if ( father.getFitness() <= mother.getFitness() ) {
            Individual.swap( father, offspring );
          } else {
            Individual.swap( mother, offspring );
          }
        }
      }
    }

    @Override
    public String toString() {
      return "Tournament";
    }
  };

  public static final Selection ROULETTE_WHEEL = new Selection() {
    public void select( Individual father, Individual mother,
        Individual[] offsprings ) {
      if ( father.getFitness() < mother.getFitness() ) {
        Individual.swap( father, mother );
      }
      for( Individual offspring : offsprings ) {
        if ( father.getFitness() < offspring.getFitness() ) {
          Individual.swap( father, offspring );
        }
      }

      double[] probability = new double[ offsprings.length + 1 ];
      for( int j = 0; j < ( probability.length - 1 ); j++ ) {
        probability[ j ] = offsprings[ j ].getFitness();
      }
      probability[ offsprings.length ] = mother.getFitness();

      double probmin = Utils.min( probability );
      if ( probmin <= 0 ) {
        probmin = Math.abs( probmin );
        for( int j = 0; j < probability.length; j++ ) {
          probability[ j ] += probmin + 1e-100;
        }
      }

      double probsum = Utils.sum( probability );
      for( int j = 0; j < probability.length; j++ ) {
        probability[ j ] /= probsum;
      }

      double random = rnd.nextDouble();
      int k = -1;
      double limit = 0;
      do {
        k++;
        limit += probability[ k ];
      } while ( random > limit );
      if ( k != offsprings.length ) {
        Individual.swap( mother, offsprings[ k ] );
      }
    }

    @Override
    public String toString() {
      return "Roulette wheel";
    }
  };

//  public static final Selection RANK = new Selection() {
//    @Override
//    public void select( Individual father, Individual mother,
//        Individual[] offsprings ) {
//      if (father.getFitness() < mother.getFitness())
//      {
//          Individual.swap( father, mother);
//      }
//      for (int j = 0; j < offsprings.length; j++)
//      {
//          if (father.getFitness() < offsprings[j].getFitness())
//          {
//              Individual.swap( father, offsprings[j]);
//          }
//      }
//
//      double[] sort = new double[offsprings.length + 1];
//      double[] rank = new double[offsprings.length + 1];
//      double[] fitnessArray = new double[offsprings.length + 1];
//      for (int j = 0; j < (fitnessArray.length - 1); j++)
//      {
//          rank[j] = sort[j] = j;
//          fitnessArray[j] = offsprings[j].getFitness();
//      }
//      rank[fitnessArray.length - 1] = sort[fitnessArray.length - 1] = fitnessArray.length - 1;
//      fitnessArray[offsprings.length] = mother.getFitness();
//
//      Arrays.sort
//
//      Array.Sort(fitnessArray, sort);
//      Array.Sort(sort, rank);
//
//      double[] probability = new double[rank.length];
//      for (int j = 0; j < rank.length; j++)
//      {
//          probability[j] = (ExpectedWorst + 2 * (1 - ExpectedWorst) * rank[j] / offsprings.length) / rank.length;
//      }
//
//      double random = rnd.nextDouble();
//      int k = -1;
//      double limit = 0;
//      do
//      {
//          k++;
//          limit += probability[k];
//      }
//      while (random > limit);
//      if (k != offsprings.length)
//      {
//          Individual.swap( mother, offsprings[k]);
//      }
//    }
//  };
}
