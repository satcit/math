package ru.satcit.math;

/**
 * Simple generic objects pair
 */
public class Pair< A, B > {
  private A first;
  private B second;

  public Pair( A first, B second ) {
    this.first = first;
    this.second = second;
  }

  public A getFirst() {
    return this.first;
  }

  public A setFirst( A first ) {
    A oldFirst = this.first;
    this.first = first;
    return oldFirst;
  }

  public B getSecond() {
    return second;
  }

  public B setSecond( B second ) {
    B oldSecond = this.second;
    this.second = second;
    return oldSecond;
  }
}
