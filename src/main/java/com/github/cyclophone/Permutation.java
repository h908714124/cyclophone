package com.github.cyclophone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.cyclophone.ArrayUtil.checkLength;

/**
 * A permutation operation that can be used to rearrange arrays and lists.
 */
public final class Permutation implements Comparable<Permutation> {

  /*
   *  An array of N integers where each of the integers between 0 and N-1 appear exactly once.
   *  This array is never modified, and no code outside of this class can have a reference to it.
   *  Because of this, Permutation instances are effectively immutable.
   */
  private final int[] ranking;

  /**
   * An efficient comparison for permutations,
   * which returns {@code 0} iff their rankings are equal.
   *
   * @param other a permutation
   * @return comparison result
   */
  @Override
  public int compareTo(Permutation other) {
    if (this == other) {
      return 0;
    }
    if (ranking.length != other.ranking.length) {
      return ranking.length - other.ranking.length;
    }
    for (int i = 0; i < ranking.length; i += 1) {
      int diff = ranking[i] - other.ranking[i];
      if (diff != 0) {
        return diff;
      }
    }
    return 0;
  }

  private static final Permutation IDENTITY = new Permutation(new int[0], false);

  private Permutation(int[] ranking) {
    this(ranking, false);
  }

  private Permutation(int[] ranking, boolean check) {
    if (check) {
      Rankings.checkRanking(ranking);
    }
    this.ranking = ranking;
  }

  static Permutation define(int... ranking) {
    int[] trimmed = Rankings.trim(ranking);
    if (trimmed.length == 0) {
      return IDENTITY;
    }
    if (trimmed == ranking) {
      trimmed = Arrays.copyOf(trimmed, trimmed.length); // no outside references
    }
    return new Permutation(trimmed, true);
  }

  static Permutation cycle0(int... cycle) {
    return define(CycleUtil.cyclic(cycle));
  }

  /**
   * Creates a new <a href="http://en.wikipedia.org/wiki/Cyclic_permutation">cycle</a>
   * (in 1-based notation).
   *
   * @param a first element of the cycle
   * @param b second element of the cycle
   * @param cycle1based remaining elements of the cycle
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
   * Return the identity permutation.
   *
   * @return the identity permutation
   * @see Permutation#isIdentity
   */
  public static Permutation identity() {
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
    if (isIdentity()) {
      return other;
    }
    if (other.isIdentity()) {
      return this;
    }
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
    if (n == 0) {
      return IDENTITY;
    }
    if (this.ranking.length == 0) {
      return this;
    }
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
  public Permutation invert() {
    if (this.ranking.length == 0) {
      return this;
    }
    return define(Rankings.invert(ranking));
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
    if (ranking.length == 0) {
      return Cycles.identity();
    }
    return Cycles.create(getCycles());
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

  @Override
  public String toString() {
    if (isIdentity()) {
      return "()";
    }
    return toCycles().toString();
  }

  /**
   * Get a copy of the ranking that represents of this permutation.
   *
   * @return a copy of the ranking
   */
  public int[] getRanking() {
    return Arrays.copyOf(ranking, ranking.length);
  }

  /**
   * Get a cycle based version of this operation.
   *
   * @return a cycle based version of this operation
   */
  public int[][] getCycles() {
    return CycleUtil.toOrbits(ranking);
  }

  /**
   * Rearrange a list. This method does not modify the input list.
   *
   * @param input a list that must have at least {@code this.length()} elements
   * @param <E> the lists element type
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input} has less than {@code this.length()} elements
   */
  public <E> List<E> apply(List<E> input) {
    if (ranking.length == 0) {
      return input;
    }
    int length = input.size();
    checkLength(ranking.length, length);
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange the input.
   *
   * @param input at least {@code this.length()} ints
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input} has less than {@code this.length()} elements
   */
  public List<Integer> apply(int... input) {
    List<Integer> list = Arrays.stream(input)
        .boxed()
        .collect(Collectors.toList());
    return apply(list);
  }

  /**
   * Conjugation with {@code h}.
   *
   * @param h a permutation
   * @return {@code h^-1 * this * h}
   */
  public Permutation conjugationBy(Permutation h) {
    return h.invert().compose(this).compose(h);
  }

  /**
   * Apply the outer automorphism of S6.
   *
   * @return map result
   */
  Permutation exoticSwap() {
    return OuterAutomorphism.getInstance().apply(this);
  }

  /**
   * Return all possible permutations of given length.
   *
   * @param n length of permutations to generate
   * @return all possible permutations of length {@code n}. This stream contains {@code n!}
   * different permutations.
   */
  public static Stream<Permutation> symmetricGroup(int n) {
    return Rankings.symmetricGroup(n).map(Permutation::new);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return Arrays.equals(ranking, ((Permutation) o).ranking);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(ranking);
  }
}
