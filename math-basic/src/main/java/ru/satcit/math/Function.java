package ru.satcit.math;

public interface Function<T> extends Data {
  T calculate( T... args );

  public static final Function< Double > sin = new Function< Double >() {
    public Double calculate( Double... args ) {
      return Math.sin( args[ 0 ] );
    }

    @Override
    public String toString() {
      return "Sin(x)";
    }

    public void setName( String name ) {
    };
  };

  public static final Function< Double > xSin = new Function< Double >() {
    public Double calculate( Double... args ) {
      return args[ 0 ] * Math.sin( args[ 0 ] );
    }

    @Override
    public String toString() {
      return "x*Sin(x)";
    }

    public void setName( String name ) {
    }
  };

  public static final Function< Double > xCube = new Function< Double >() {
    public Double calculate( Double... args ) {
      return Math.pow( args[ 0 ], 3 );
    }

    @Override
    public String toString() {
      return "x^3";
    }

    public void setName( String name ) {
    }
  };

  public static final Function< Double > xSquare = new Function< Double >() {
    public Double calculate( Double... args ) {
      return Math.pow( args[ 0 ], 2 );
    }

    @Override
    public String toString() {
      return "x^2";
    }

    public void setName( String name ) {
    }
  };

  public static final Function< Double > aXb = new Function< Double >() {
    public Double calculate( Double... args ) {
      return args[ 0 ] / 2 + 1;
    }

    @Override
    public String toString() {
      return "a*x + b";
    }

    public void setName( String name ) {
    }
  };
}
