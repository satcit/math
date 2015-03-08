package ru.satcit.math;


public interface GeneticTask {
  double calculateFitness( Individual individ );
  int getGenotypeLength();

  public static final GeneticTask ACKLEYS_FUNCTION = new GeneticTask() {
    public double calculateFitness( Individual individ ) {
      double sum1 = 0, sum2 = 0;
      for( int i = 0; i < individ.size(); i++ ) {
        sum1 += Math.pow( individ.get( i ), 2 );
        sum2 += Math.cos( 2 * Math.PI * individ.get( i ) );
      }
      double q = 20
          * Math.exp( -0.2 * Math.sqrt( sum1 / individ.size() ) )
          + Math.exp( sum2 / individ.size() ) - 20 - Math.E;
      return q;
    }

    public int getGenotypeLength() {
      return 2; //TODO actually there is no determined dimension
    }

    @Override
    public String toString() {
      return "Ackleys function";
    };
  };
//
//  public static final Task GENERALISED_GRIEWANK_FUNCTION = new Task() {
//    @Override
//    public double run(Individual individ) {
//      double sum = 0, mult = 1;
//      for (int i = 0; i < individ.getGenotype().length; i++) {
//        sum += Math.pow(individ.getGenotype()[i], 2);
//        mult *= Math.cos(individ.getGenotype()[i] / Math.sqrt(i + 1));
//      }
//      double q = -sum / 4000 + mult - 1;
//      return q;
//    }
//  };
//
//  public static final Task GENERALISED_ROSENBROCKS_FUNCTION = new Task() {
//    @Override
//    public double run(Individual individ) {
//      double q = 0;
//      for (int i = 0; i < individ.getGenotype().length - 1; i++) {
//        q += -100
//            * Math.pow((individ.getGenotype()[i + 1] - Math.pow(
//                individ.getGenotype()[i], 2)), 2)
//            - Math.pow((individ.getGenotype()[i] - 1), 2);
//      }
//      return q;
//    }
//  };
//
//  public static final Task SPHERE_MODEL = new Task() {
//    @Override
//    public double run(Individual individ) {
//      double q = 0;
//      for (int i = 0; i < individ.getGenotype().length; i++) {
//        q -= Math.pow((1 - individ.getGenotype()[i]), 2);
//      }
//      return q;
//    }
//  };
//
//  public static final Task GENERALISED_SCHWEFELS_PROBLEM = new Task() {
//
//    @Override
//    public double run(Individual individ) {
//      double q = 0;
//      for (int i = 0; i < individ.getGenotype().length; i++) {
//        q += individ.getGenotype()[i]
//            * Math.sin(Math.sqrt(Math.abs(individ.getGenotype()[i])));
//      }
//      return q;
//    }
//  };
//
//  public static final Task GENERALISED_RASTRIGINS_FUNCTION = new Task() {
//    @Override
//    public double run(Individual individ) {
//      double q = 0;
//      for (int i = 0; i < individ.getGenotype().length; i++) {
//        q -= Math.pow(individ.getGenotype()[i], 2) - 10
//            * Math.cos(2 * Math.PI * individ.getGenotype()[i]) + 10;
//      }
//      return q;
//    }
//  };
//
//  public static final Task QUARTIC_FUNCTION_WITH_NOISE = new Task() {
//    private Random rnd = new Random(System.currentTimeMillis());
//
//    @Override
//    public double run(Individual individ) {
//      double q = 0;
//      for (int i = 0; i < individ.getGenotype().length; i++) {
//        q -= (i + 1) * Math.pow(individ.getGenotype()[i], 4);
//      }
//      q -= rnd.nextDouble();
//      return q;
//    }
//  };
}
