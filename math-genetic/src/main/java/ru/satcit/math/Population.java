package ru.satcit.math;

import java.awt.Point;
import java.util.Random;

import javax.swing.ProgressMonitor;

public class Population {
  private Random rnd = new Random( System.currentTimeMillis() );
  private static int generationNumber = 0;
  private static double expectedWorst;
  private static double searchSpace;
  private static int genotypeLength;
  private static GeneticTask task;

  private Individual[] parents;
  private Crossover crossover;
  private Mutation mutation = Mutation.NULL;
  private Selection selection;

  public static int getGenerationNumber() {
    return generationNumber;
  }

  public static void setGenerationNumber( int generationNumber ) {
    Population.generationNumber = generationNumber;
  }

  private static void resetGenerationNumber() {
    generationNumber = 0;
  }

  public static double getSearchSpace() {
    return searchSpace;
  }

  public static int getGenotypeLength() {
    return genotypeLength;
  }

  public static double getExpectedWorst() {
    return expectedWorst;
  }

  public static void setExpectedWorst( double expectedWorst ) {
    Population.expectedWorst = expectedWorst;
  }

  public static GeneticTask getTask() {
    return task;
  }

  public Population( int individualsQuantity,
      double searchSpace, GeneticTask task, Crossover crossover, Selection selection, Mutation mutation ) {
    Population.resetGenerationNumber();

    Population.task = task;

    this.crossover = crossover;
    this.selection = selection;
    this.mutation = mutation;

    Population.searchSpace = searchSpace;
    Population.genotypeLength = task.getGenotypeLength();

    // ������������� ������� ������
    parents = new Individual[ individualsQuantity ];
    for( int i = 0; i < individualsQuantity; i++ ) {
      parents[ i ] = new Individual( Population.genotypeLength);
      parents[ i ].calculateFitness( task );
    }
  }

  // ����������� � ����� ������ �� ���������
  public void select( int stepQuantity, int generationQuantity,
      double mutationProbability, double mutationRate, ProgressMonitor monitor ) {
    // ����������, �������� ��� ����������� ��������� � ��������
    // ��������� ������������� ���������
    int percentComplete = 0;
    int highestPercentageReached = 0;

    // ������������� ������� ��������
    Individual[] offsprings = new Individual[ generationQuantity ];
    for( int i = 0; i < offsprings.length; i++ ) {
      offsprings[ i ] = new Individual(Population.genotypeLength);
    }

    int father, mother;
    for( int i = 0; i < stepQuantity; i++, generationNumber++ ) {
      if ( monitor.isCanceled() ) {
        break;
      } else {
        father = rnd.nextInt( parents.length );
        do {
          mother = rnd.nextInt( parents.length );
        } while ( father == mother );

        // �����������
        crossover.fillOffsprings( offsprings, father, mother, parents );

        for( Individual offspring : offsprings ) {
          double random = rnd.nextDouble();
          if ( random < mutationProbability ) {
            mutation.mutate( offspring, mutationRate );
          }

          // ������� ������� �����������������
          offspring.calculateFitness( task );
        }
        for( Individual parent : parents ) {
          parent.calculateFitness( task );
        }

        // �����
        selection.select( parents[ father ], parents[ mother ], offsprings );

        // ��������� �������� ���������� ��������
        percentComplete = ( i + 1 ) * 100 / stepQuantity;
        if ( percentComplete > highestPercentageReached ) {
          highestPercentageReached = percentComplete;
          monitor.setProgress( percentComplete );
        }

      }
      System.out.println(bestIndividual().getFitness());//TODO delete it
    }
  }

  public Individual bestIndividual() {
    Individual bestInd = parents[ 0 ];
    for( int i = 1; i < parents.length; i++ ) {
      if ( parents[ i ].getFitness() > bestInd.getFitness() ) {
        bestInd = parents[ i ];
      }
    }
    return bestInd;
  }

  public Individual worstIndividual() {
    Individual worst = parents[ 0 ];
    for( int i = 1; i < parents.length; i++ ) {
      if ( parents[ i ].getFitness() < worst.getFitness() ) {
        worst = parents[ i ];
      }
    }
    return worst;
  }

  // ���������� ������ � ���������
  public Point[] PopulationToPoints( int x, int y ) {
    Point[] points = new Point[ parents.length ];
    for( int i = 0; i < parents.length; i++ ) {
      points[ i ] = parents[ i ].genotypeToPoint( x, y );
    }
    return points;
  }

  public void setSearchSpace( double searchSpace ) {
    Population.searchSpace = searchSpace;
  }
}
