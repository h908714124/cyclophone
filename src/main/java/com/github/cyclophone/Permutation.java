package com.github.cyclophone;

import java.util.Arrays;
import java.util.List;

import static com.github.cyclophone.ArrayUtil.checkLength;
import static com.github.cyclophone.ArrayUtil.negativeFailure;

/**
 * A permutation operation that can be used to rearrange arrays and lists.
 * Instances of this class are immutable, and none of the apply methods modify the input.
 * The toCycles method can be used to obtain the destructive version of an instance.
 *
 * @see #toCycles
 */
public final class Permutation {

  /*
   *  An array of N integers where each of the integers between 0 and N-1 appear exactly once.
   *  This array is never modified, and no code outside of this class can have a reference to it.
   *  Because of this, Permutation instances are effectively immutable.
   */
  private final int[] ranking;

  private static final Permutation IDENTITY = new Permutation(new int[0]);

  private Permutation(int[] ranking) {
    this.ranking = Rankings.checkRanking(ranking);
  }

  static Permutation define(int... ranking) {
    int[] trimmed = Rankings.trim(ranking);
    if (trimmed.length == 0)
      return IDENTITY;
    return new Permutation(trimmed);
  }

  static Permutation cycle0(int... cycle) {
    return define(CycleUtil.cyclic(cycle));
  }


  /**
   * Creates a new <a href="http://en.wikipedia.org/wiki/Cyclic_permutation">cycle</a>.
   *
   * @param a first param
   * @param b second param
   * @param cycle1based a list of numbers that defines a permutation in 1-based cycle notation
   * @return the cyclic permutation defined by {@code cycle1based}
   */
  public static Permutation cycle(int a, int b, int... cycle1based) {
    int[] ints = new int[cycle1based.length + 2];
    ints[0] = a;
    ints[1] = b;
    System.arraycopy(cycle1based, 0, ints, 2, cycle1based.length);
    return cycle0(ArrayUtil.add(ints, -1));
  }

  /**
   * Creates a random permutation of given length.
   *
   * @param length the length of arrays that the result can be applied to
   * @return a random permutation that can be applied to an array of length {@code length}
   */
  static Permutation random(int length) {
    return define(Rankings.random(length));
  }

  /**
   * Return the identity permutation. It is the only permutation that can be applied to arrays of any length.
   *
   * @return the identity permutation that can be applied to an array of length {@code length}
   * @see Permutation#isIdentity
   */
  static Permutation identity() {
    return IDENTITY;
  }

  /**
   * <p>Permutation composition. The following is true for all non-negative numbers
   * {@code i}:</p>
   * <pre><code>
   *   this.apply(other.apply(i)) == this.compose(other).apply(i)
   * </code></pre>
   *
   * @param other a permutation
   * @return the product of this instance and {@code other}
   */
  public Permutation compose(Permutation other) {
    if (this.isIdentity())
      return other;
    if (other.ranking.length == 0)
      return this;
    return define(Rankings.comp(this.ranking, other.ranking));
  }

  /**
   * Raise this permutation to the {@code n}th power.
   * If {@code n} is positive, the product
   * <pre><code>
   *   this.compose(this)[...]compose(this)
   * </code></pre>
   * ({@code n} times) is returned.
   * If {@code n} is negative, the product
   * <pre><code>
   *   this.invert().compose(this.invert()) [...] compose(this.invert());
   * </code></pre>
   * ({@code -n} times) is returned.
   * If {@code n} is zero, the identity permutation of length {@code this.length} is returned.
   *
   * @param n any integer
   * @return the {@code n}th power of this permutation
   */
  public Permutation pow(int n) {
    if (n == 0)
      return identity();
    if (this.ranking.length == 0)
      return this;
    Permutation seed = n < 0 ? invert() : this;
    Permutation result = seed;
    for (int i = 1; i < Math.abs(n); i += 1)
      result = result.compose(seed);
    return result;
  }

  /**
   * <p>Inverts this permutation. The following always returns true:</p>
   * <pre><code>
   *   this.compose(this.inverse).isIdentity();
   * </code></pre>
   *
   * @return the inverse of this permutation
   * @see #compose
   * @see #isIdentity
   */
  Permutation invert() {
    if (this.ranking.length == 0)
      return this;
    return define(Rankings.invert(ranking));
  }

  /**
   * Creates a cycle that acts as a delete followed by an insert. Examples:
   * <pre><code>
   *   Permutation.move(0, 2).apply("12345");
   *   =&gt; 23145
   *   </code></pre>
   * <pre><code>
   *   Permutation.move(3, 1).apply("12345");
   *   =&gt; 14235
   * </code></pre>
   * If {@code delete == insert}, the identity of length {@code delete + 1} is returned.
   *
   * @param delete a non-negative integer
   * @param insert a non-negative integer
   * @return a permutation of length {@code Math.max(delete, insert) + 1}
   */
  static Permutation move(int delete, int insert) {
    return cycle0(ArrayUtil.range(insert, delete, true));
  }

  /**
   * <p>Calculate the order of this permutation. The order is the smallest positive number {@code n}
   * such that</p>
   * <pre><code>
   *   this.pow(n).isIdentity();
   * </code></pre>
   *
   * @return the order of this permutation
   * @exception java.lang.IllegalArgumentException if {@code pos < 0} or {@code pos >= this.length}
   * @see #isIdentity
   * @see #pow
   */
  public int order() {
    int i = 1;
    Permutation p = this;
    while (p.ranking.length != 0) {
      i += 1;
      p = p.compose(this);
    }
    return i;
  }

  /**
   * Get a cycle based version of this operation, which can be used to change arrays in place.
   *
   * @return a cycle based version of this operation
   */
  Cycles toCycles() {
    if (this.ranking.length == 0)
      return Cycles.identity();
    return Cycles.create(CycleUtil.toOrbits(ranking));
  }


  /**
   * <p>Returns a permutation that reverses its input. Example:</p>
   * <pre><code>
   *   Permutation.reverse(5).apply("12345");
   *   =&gt; 54321
   * </code></pre>
   *
   * @param length a non negative number
   * @return a permutation that reverses an array of length {@code length}
   */
  static Permutation reverse(int length) {
    int[] result = new int[length];
    for (int i = 0; i < length; i += 1) {
      result[i] = length - i - 1;
    }
    return define(result);
  }

  /**
   * Check if this permutation reverses its input.
   *
   * @param n a nonnegative number
   * @return true if this permutation reverses or "flips" an input of length {@code n}
   * @exception java.lang.IllegalArgumentException if {@code n} is negative
   * @see #reverse
   */
  boolean reverses(int n) {
    if (ranking.length < n)
      return false;
    if (n < 0)
      negativeFailure();
    for (int i = 0; i < n; i += 1)
      if (ranking[i] != ranking.length - i - 1)
        return false;
    return true;
  }

  /**
   * <p>Determine whether this permutation moves any index.</p>
   *
   * @return true if this is the identity
   */
  boolean isIdentity() {
    return ranking.length == 0;
  }

  /**
   * Return the minimum number of elements that an array or list must have, in order for this operation to
   * be applicable.
   *
   * @return the length of this operation
   */

  int length() {
    return ranking.length;
  }

  /**
   * Convert this permutation to a human readable string. This representation may change in the future.
   *
   * @return a String representation of this permutation.
   */
  @Override
  public String toString() {
    return toCycles().toString();
  }

  /**
   * Equality test. In order for permutations to be equal, they must have the same length, and their effects
   * on indexes and arrays must be identical.
   *
   * @param other another object
   * @return true if the other object is an equivalent permutation
   */
  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (other == null || getClass() != other.getClass())
      return false;
    return Arrays.equals(ranking, ((Permutation) other).ranking);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(ranking);
  }

  /**
   * Get a copy of the ranking that represents of this permutation.
   *
   * @return a copy of the ranking
   */
  int[] getRanking() {
    return Arrays.copyOf(ranking, ranking.length);
  }

  /**
   * Move an index. The following is true for arrays {@code a} of any type and of length
   * {@code a.length &gt;= this.length}, and all indexes {@code 0 &lt;= i < a.length}:
   * {@code
   * apply(a)[apply(i)] == a[i];
   * }
   * If the input is greater than or equal to {@code this.length()}, then the same number is returned.
   *
   * @param i a non negative number
   * @return the moved index
   * @exception java.lang.IllegalArgumentException if the input is negative
   */
  int apply(int i) {
    if (i < 0)
      negativeFailure();
    if (i >= ranking.length)
      return i;
    return ranking[i];
  }

  /**
   * Rearrange a list. This method does not modify the input list.
   *
   * @param input a list that must have at least {@code this.length()} elements
   * @param <E> the lists element type
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input} has less than {@code this.length()} elements
   * @see Cycles#clobber(List)
   * @see #apply(int)
   */
  public <E> List<E> apply(List<E> input) {
    if (ranking.length == 0)
      return input;
    int length = input.size();
    checkLength(ranking.length, length);
    return Rankings.apply(ranking, input);
  }

  /**
   * Conjugation with {@code h}.
   *
   * @param h a permutation
   * @return {@code h^-1 * this * h}
   */
  public Permutation conj(Permutation h) {
    return h.invert().compose(this).compose(h);
  }
}
