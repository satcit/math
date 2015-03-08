package ru.satcit.math;

import java.util.Random;

import ru.satcit.math.Pair;
import ru.satcit.math.Vector;

public interface Crossover {

  public void fillOffsprings( Individual[] children, int father, int mother,
      Individual[] parents );

  /**
   * Blended crossover
   *
   */
  public static Crossover BLEND = new Crossover() {
    public void fillOffsprings( Individual[] children, int father, int mother,
        Individual[] parents ) {
      Individual fatherInd = parents[father];
      Individual motherInd = parents[mother];
      for( int k = 0; k < children.length; k++ ) {
        double j;
        boolean pass;
        do {
          pass = true;
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            j = Utils.getRandom().nextDouble() * 2 - 0.5;
            children[ k ].set( i, fatherInd.get( i )* j + ( 1 - j ) * motherInd.get( i ) );
            if ( Math.abs( children[ k ].get( i ) ) > ( Population
                .getSearchSpace() ) ) {
              pass = false;
              break;
            }
          }
        } while ( !pass );
        children[ k ].calculateFitness( Population.getTask() );
      }
    }

    @Override
    public String toString() {
      return "BLX";
    }
  };

  /**
   * Sphere crossover
   */
  public static Crossover SPHERE = new Crossover() {
    public void fillOffsprings( Individual[] offsprings, int father,
        int mother, Individual[] parents ) {
      for( int k = 0; k < offsprings.length; k++ ) {
        double j;
        boolean pass;
        do {
          pass = true;
          j = Utils.getRandom().nextDouble();
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            offsprings[ k ].set( i, Math.sqrt(
                Math.pow( parents[ father ].get(i), 2 )* j
                + Math.pow( parents[ mother ].get(i), 2 ) * ( 1 - j ) ) );
            if ( Math.abs( offsprings[ k ].get(i) ) > ( Population.getSearchSpace() ) ) {
              pass = false;
              break;
            }
          }
        } while ( !pass );
      }
    }

    @Override
    public String toString() {
      return "SphereX";
    }
  };

  /**
   * Simplex crossover
   */
  public static Crossover SIMPLEX = new Crossover() {
    public void fillOffsprings( Individual[] offsprings, int father,
        int mother, Individual[] parents ) {
      int genotypeLength = Population.getGenotypeLength();
      for( Individual offspring : offsprings ) {
        boolean pass;
        Individual[] group = new Individual[ genotypeLength + 1 ];
        group[ 0 ] = parents[ father ];
        group[ 1 ] = parents[ mother ];

        do {
          pass = true;
          for( int i = 2; i < group.length; i++ ) {
            group[ i ] = parents[ Utils.getRandom().nextInt( parents.length ) ];
          }
          int best = 0;
          int worst = 0;
          for( int i = 1; i < group.length; i++ ) {
            if ( group[ i ].getFitness() > group[ best ].getFitness() ) {
              best = i;
            }
            if ( group[ i ].getFitness() < group[ worst ].getFitness() ) {
              worst = i;
            }
          }

          Individual centroid = new Individual(genotypeLength);
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            for( int j = 0; j < group.length; j++ ) {
              if ( j != worst ) {
                centroid.set(i, centroid.get(i) + group[ j ].get(i) );
              }
            }
            centroid.set( i, centroid.get( i )/ (group.length - 1) );
          }

          Individual reflected = new Individual(genotypeLength);
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            reflected.set( i,centroid.get(i)
                + ( centroid.get(i) - parents[ worst ]
                    .get(i) ) );
          }

          if ( reflected.calculateFitness( Population.getTask() ) > group[ best ]
              .getFitness() ) {
            Individual expanded = new Individual(genotypeLength);
            for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
              expanded.set( i, reflected.get(i)
                  + ( reflected.get(i) - centroid.get(i) ) );
            }
            if ( expanded.calculateFitness( Population.getTask() ) > reflected
                .getFitness() ) {
              expanded.copyTo( offspring );
            } else {
              reflected.copyTo( offspring );
            }
          } else if ( reflected.getFitness() > group[ worst ].getFitness() ) {
            reflected.copyTo( offspring );
          } else {
            Individual contracted = new Individual(genotypeLength);
            for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
              contracted.set( i, ( group[ worst ].get(i) + centroid
                  .get(i) ) / 2 );
            }
            if ( contracted.calculateFitness( Population.getTask() ) > group[ worst ]
                .getFitness() ) {
              contracted.copyTo( offspring );
            } else {
              Individual intermediate = new Individual(genotypeLength);
              for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
                intermediate.set( i, ( group[ worst ]
                    .get(i) + group[ best ].get(i) ) / 2 );
              }
              intermediate.copyTo( offspring );
            }
          }
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            if ( Math.abs( offspring.get(i) ) > ( Population
                .getSearchSpace() ) ) {
              pass = false;
              break;
            }
          }
        } while ( !pass );
      }
    }

    @Override
    public String toString() {
      return "SimplexX";
    }
  };

  /***
   * ���������, ����������� �������� (Simulated Binary Crossover)
   */
  public static Crossover SBX = new Crossover() {
    public void fillOffsprings( Individual[] offsprings, int father,
        int mother, Individual[] parents ) {
      Individual fatherInd = parents[father];
      Individual motherInd = parents[mother];
      int genotypeLength = fatherInd.size();
      for( int k = 0; k < offsprings.length; k += 2 ) {
        offsprings[ k + 1 ] = new Individual( genotypeLength );
        double j;
        double coef = 0.5;
        boolean pass;

        do {
          pass = true;
          double random = Utils.getRandom().nextDouble();
          if ( random <= 0.5 ) {
            j = Math.pow( ( 2 * random ), ( 1 / coef + 1 ) );
          } else {
            j = Math.pow( ( 1 / ( 2 * ( 1 - random ) ) ), ( 1 / coef + 1 ) );
          }
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            offsprings[ k ].set( i, 0.5 * ( ( 1 + j )
                * fatherInd.get(i) + ( 1 - j )
                * motherInd.get(i) ) );
            offsprings[ k + 1 ].set( i, 0.5 * ( ( 1 - j )
                * fatherInd.get(i) + ( 1 + j )
                * motherInd.get(i) ) );
            if ( ( Math.abs( offsprings[ k ].get(i) ) > Population.getSearchSpace() ) ||
                 ( Math.abs( offsprings[ k + 1 ].get(i) ) > Population.getSearchSpace() ) ) {
              pass = false;
              break;
            }
          }
        } while ( !pass );
      }
    }

    @Override
    public String toString() {
      return "SBX";
    }
  };

  /***
   * ���������� ����������� ��������� (Gaussian Uniform Crossover)
   */
  public static Crossover GUX = new Crossover() {
    public void fillOffsprings( Individual[] offsprings, int father,
        int mother, Individual[] parents ) {
      double alpha = 0;
      double sigma = 1;
      Random rnd = Utils.getRandom();
      int genotypeLength = Population.getGenotypeLength();
      for( int k = 0; k < offsprings.length; k += 2 ) {
        offsprings[ k + 1 ] = new Individual(genotypeLength);
        boolean pass;
        do {
          pass = true;
          double rnd1, rnd2;
          // �������������� �����-�������
          double s;
          do {
            rnd1 = rnd.nextDouble() * 2 - 1;
            rnd2 = rnd.nextDouble() * 2 - 1;
            s = Math.pow( rnd1, 2 ) + Math.pow( rnd2, 2 );
          } while ( ( s > 1 ) || ( s == 0 ) );
          // end �������������� �����-�������
          rnd1 = alpha + rnd1 * Math.sqrt( -2 * Math.log( s ) / s ) * sigma;
          rnd2 = alpha + rnd2 * Math.sqrt( -2 * Math.log( s ) / s ) * sigma;
          for( int i = 0; i < genotypeLength; i++ ) {
            double distance = Math.abs( parents[ father ].get( i )
                - parents[ mother ].get( i ) );
            if ( rnd.nextDouble() <= 0.5 ) {
              offsprings[ k ].set( i, parents[ father ].get( i ) + rnd1 * distance / 3 );
              offsprings[ k + 1 ].set( i, parents[ mother ].get( i ) + rnd1 * distance / 3 );
            } else {
              offsprings[ k ].set( i, parents[ mother ].get(i) + rnd2 * distance / 3 );
              offsprings[ k + 1 ].set( i, parents[ father ].get( i ) + rnd2 * distance / 3 );
            }
            if ( ( Math.abs( offsprings[ k ].get( i ) ) > Population.getSearchSpace() ) ||
                 ( Math.abs( offsprings[ k + 1 ].get( i ) ) > Population.getSearchSpace() ) ) {
              pass = false;
              break;
            }
          }
        } while ( !pass );
      }
    }

    @Override
    public String toString() {
      return "GUX";
    }
  };

  /***
   * ������������ ��������� ����������� ������������� (Unimodal Normal
   * Distribution Crossover)
   */
  public static Crossover UNDX = new Crossover() {
    public void fillOffsprings( Individual[] offsprings, int father,
        int mother, Individual[] parents ) {
      int size = Population.getGenotypeLength();
      Vector diff = new Vector( size );
      for( int i = 0; i < size; i++ ) {
        diff.add( parents[ mother ].get(i) - parents[ father ].get(i) );
      }

      Vector[] e = new Vector[ size - 1 ];
      e = diff.orthonormalization();

      for( int k = 0; k < offsprings.length; k += 2 ) {
        int neighbour;
        do {
          neighbour = Utils.getRandom().nextInt( parents.length );
        } while ( ( neighbour == father ) || ( neighbour == mother ) );

        Individual midd = new Individual(size);

        double neighbour_father = 0;
        double mother_father = 0;
        double scalar_mult = 0;

        for( int i = 0; i < size; i++ ) {
          midd.set( i, 0.5 * ( parents[ father ].get(i) + parents[ mother ].get(i) ) );
          neighbour_father += Math.pow(
              ( parents[ neighbour ].get(i) - parents[ father ].get(i) ), 2 );
          mother_father += Math.pow( diff.get( i ), 2 );
          scalar_mult += diff.get( i )
              * ( parents[ neighbour ].get(i) - parents[ father ]
                  .get(i) );
        }

        // ���������� �� ������ �� ������, ���������� ����� ���������
        double distance = 0;
        if ( ( neighbour_father * mother_father ) != 0 ) {
          distance = Math.sqrt( neighbour_father )
              * Math.sqrt( 1 - Math.pow( scalar_mult, 2 )
                  / ( neighbour_father * mother_father ) );
        }

        boolean pass;
        do {
          pass = true;
          Pair< Double, Double > rnd12;
          rnd12 = Utils.boxMullerCast( 0, 0.5 );
          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            offsprings[ k ].set(i, midd.get(i)
                + rnd12.getFirst() * diff.get( i ) );
            offsprings[ k + 1 ].set(i, midd.get(i)
                - rnd12.getSecond() * diff.get( i ) );
          }

          if ( distance != 0 ) {
            for( int i = 0; i < Population.getGenotypeLength() - 1; i++ ) {
              rnd12 = Utils.boxMullerCast( 0, 0.35 / Population.getGenotypeLength() );
              for( int j = 0; j < Population.getGenotypeLength(); j++ ) {
                offsprings[ k ].set( j, offsprings[k].get( j ) + distance
                    * rnd12.getFirst() * e[ i ].get( j ) );
                offsprings[ k + 1 ].set( j, offsprings[k+1].get( j ) - distance
                    * rnd12.getSecond() * e[ i ].get( j ) );
              }
            }
          }

          for( int i = 0; i < Population.getGenotypeLength(); i++ ) {
            if ( ( Math.abs( offsprings[ k ].get(i) ) > ( Population
                .getSearchSpace() ) )
                || ( Math.abs( offsprings[ k + 1 ].get(i) ) > ( Population
                    .getSearchSpace() ) ) ) {
              pass = false;
              break;
            }
          }
        } while ( pass == false );
      }
    }

    @Override
    public String toString() {
      return "UNDX";
    }
  };
}
