package com.github.cyclophone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.cyclophone.ArrayUtil.checkLength;

/**
 * An operation that swaps two elements of an array or list.
 */
final class Transposition implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final TranspositionFactory NON_CACHING_FACTORY = new TranspositionFactory(0);

  private final int j;
  private final int k;

  /**
   * A simple caching factory that maintains a permanent cache of transpositions below the configured length.
   */
  static final class TranspositionFactory {

    private final Transposition[][] cache;

    /**
     * Create a new factory. If {@code maxCachedLength > 0}, the transpositions returned by the
     * {@code swap} method will be cached and reused if their {@code length} is {@code <= maxCachedLength}.
     * The cache will permanently store up to {@code maxCachedLength * (maxCachedLength - 1)} transpositions.
     *
     * @param maxCachedLength the maximum index that is moved by a cached transposition
     */
    TranspositionFactory(int maxCachedLength) {
      this.cache = new Transposition[maxCachedLength][];
      for (int j = 1; j < maxCachedLength; j++)
        cache[j] = new Transposition[j];
    }

    /**
     * Get a transposition operation that swaps the element at the given indexes.
     *
     * @param j a non-negative number
     * @param k a non-negative number that must not be the same as {@code j}
     * @return a transposition operation
     * @exception java.lang.IllegalArgumentException if the arguments are equal or negative
     */
    Transposition swap(int j, int k) {
      if (j < 0 || k < 0)
        ArrayUtil.negativeFailure();
      if (j == k)
        throw new IllegalArgumentException("arguments must not be equal");
      if (k > j) {
        // make sure that j is larger than k
        int temp = k;
        k = j;
        j = temp;
      }
      if (j < cache.length) {
        if (cache[j][k] == null)
          cache[j][k] = new Transposition(j, k);
        return cache[j][k];
      }
      return new Transposition(j, k);
    }

  }

  /**
   * Return a random transposition that can be applied to arrays of the specified length.
   *
   * @param length an integer not less than {@code 2}
   * @param factory a transposition factory
   * @return a random transposition of length {@code length} or less
   * @exception IllegalArgumentException if {@code length} is less than {@code 2}
   */
  static Transposition random(TranspositionFactory factory, int length) {
    if (length < 2)
      throw new IllegalArgumentException("minimum length of a transposition is 2");
    int j = (int) (length * Math.random());
    int k = (int) (length * Math.random());
    if (j == k)
      k = j == 0 ? 1 : j - 1;
    return factory.swap(j, k);
  }

  /**
   * Return a random transposition that can be applied to arrays of the specified length.
   *
   * @param length an integer not less than {@code 2}
   * @return a random transposition of length {@code length} or less
   * @exception IllegalArgumentException if {@code length} is less than {@code 2}
   */
  static Transposition random(int length) {
    return random(NON_CACHING_FACTORY, length);
  }

  /**
   * Return an operation that swaps the elements at the given indexes.
   *
   * @param j a non-negative number
   * @param k a non-negative number
   * @return the transposition that swaps the elements at {@code j} and {@code k}
   * @exception IllegalArgumentException if {@code j < 0}, {@code k < 0} or {@code j == k}
   */
  static Transposition swap(int j, int k) {
    return NON_CACHING_FACTORY.swap(j, k);
  }

  private Transposition(int j, int k) {
    assert j > k;
    this.j = j;
    this.k = k;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(int[] array) {
    checkLength(j, array.length);
    int temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(byte[] array) {
    byte temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(char[] array) {
    char temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(short[] array) {
    short temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(float[] array) {
    float temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(double[] array) {
    double temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(long[] array) {
    long temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception java.lang.IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(Object[] array) {
    Object temp = array[k];
    array[k] = array[j];
    array[j] = temp;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  int[] apply(int[] a) {
    int[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  byte[] apply(byte[] a) {
    byte[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  char[] apply(char[] a) {
    char[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  short[] apply(short[] a) {
    short[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  float[] apply(float[] a) {
    float[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  double[] apply(double[] a) {
    double[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  long[] apply(long[] a) {
    long[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  <E> E[] apply(E[] a) {
    E[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new list. This method does not modify the input.
   *
   * @param a an list of size not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.size() < this.length()}
   */
  <E> List<E> apply(List<E> a) {
    ArrayList<E> copy = new ArrayList<E>(a.size());
    for (int i = 0; i < a.size(); i++)
      copy.set(i, a.get(apply(i)));
    return copy;
  }

  /**
   * Move an index. This method will not fail if the input is negative, but just return it unchanged.
   *
   * @param i a number
   * @return the moved index
   */
  int apply(int i) {
    return i == j ? k : i == k ? j : i;
  }

  /**
   * Check if this transposition commutes with the other.
   *
   * @param other a transposition
   * @return true if {@code this.apply(other.apply(i)) == other.apply(this.apply(i))} for all integers {@code i}
   */
  boolean commutesWith(Transposition other) {
    return (this.j != other.j && this.k != other.k && this.j != other.k && this.k != other.j)
        || (this.j == other.j && this.k == other.k);
  }

  /**
   * Return the minimum number of elements that an array or list must have, in order for this operation to
   * be applicable.
   *
   * @return the length of this operation
   */
  int length() {
    return j + 1;
  }

  /**
   * Get a permutation version of this operation.
   *
   * @return a permutation
   */
  Permutation toPermutation() {
    return Permutation.cycle0(j, k);
  }

  /**
   * Take the product of the given transpositions.
   *
   * @param transpositions an array of transpositions
   * @return the product of the input
   */
  static Permutation product(Transposition... transpositions) {
    int maxIndex = 0;
    for (Transposition t : transpositions)
      maxIndex = Math.max(maxIndex, t.j);
    int[] ranking = ArrayUtil.range(maxIndex + 1);
    for (Transposition t : transpositions) {
      int temp = ranking[t.k];
      ranking[t.k] = ranking[t.j];
      ranking[t.j] = temp;
    }
    return Permutation.define0(ranking);
  }

  public String toString() {
    return String.format("(%d %d)", j, k);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transposition that = (Transposition) o;
    return (j == that.j && k == that.k);
  }

  @Override
  public int hashCode() {
    int result = j;
    result = 31 * result + k;
    return result;
  }
}
