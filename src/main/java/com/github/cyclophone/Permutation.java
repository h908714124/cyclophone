package com.github.cyclophone;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

  private static final Permutation IDENTITY = new Permutation(new int[0], false);

  private Permutation(int[] ranking, boolean validate) {
    ranking = Rankings.trim(ranking);
    this.ranking = validate ? Rankings.checkRanking(ranking) : ranking;
  }

  static Permutation define() {
    return IDENTITY;
  }

  static Permutation define(int a0) {
    if (a0 == 0) {
      return IDENTITY;
    } else {
      throw new IllegalArgumentException("not a ranking");
    }
  }

  static Permutation define(int a0, int a1) {
    return define(new int[]{a0, a1}, true, false);
  }

  static Permutation define(int a0, int a1, int a2) {
    return define(new int[]{a0, a1, a2}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3) {
    return define(new int[]{a0, a1, a2, a3}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4) {
    return define(new int[]{a0, a1, a2, a3, a4}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5) {
    return define(new int[]{a0, a1, a2, a3, a4, a5}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6) {
    return define(new int[]{a0, a1, a2, a3, a4, a5, a6}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7) {
    return define(new int[]{a0, a1, a2, a3, a4, a5, a6, a7}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8) {
    return define(new int[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9) {
    return define(new int[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10) {
    return define(new int[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}, true, false);
  }

  static Permutation define(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int... a11) {
    int[] a00 = {a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10};
    int[] a = new int[a00.length + a11.length];
    System.arraycopy(a00, 0, a, 0, a00.length);
    System.arraycopy(a11, 0, a, a00.length, a11.length);
    return define(a, true, false);
  }

  static Permutation define(int[] ranking) {
    return define(ranking, true);
  }

  private static Permutation define(int[] ranking, boolean dirty) {
    return define(ranking, dirty, dirty);
  }

  private static Permutation define(int[] ranking, boolean validate, boolean copy) {
    int[] trimmed = Rankings.trim(ranking);
    if (trimmed.length == 0)
      return IDENTITY;
    if (copy && ranking == trimmed) {
      trimmed = Arrays.copyOf(trimmed, trimmed.length);
    }
    return new Permutation(trimmed, validate);
  }

  static Permutation cycle0(int... cycle) {
    return define(CycleUtil.cyclic(cycle), false);
  }


  /**
   * Creates a new <a href="http://en.wikipedia.org/wiki/Cyclic_permutation">cycle</a>.
   *
   * @param a first param
   * @param b second param
   * @param cycle1based a list of numbers that defines a permutation in 1-based cycle notation
   * @return the cyclic permutation defined by {@code cycle1based}
   * @see Permutation#cycle0
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
    return define(Rankings.random(length), false);
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
   * @see #product
   */
  public Permutation compose(Permutation other) {
    if (this.isIdentity())
      return other;
    if (other.ranking.length == 0)
      return this;
    return define(Rankings.comp(this.ranking, other.ranking), false);
  }

  /**
   * Take the product of the given permutations.
   *
   * @param permutations an array of permutations
   * @return the product of the input
   * @see #compose
   */
  static Permutation product(Permutation... permutations) {
    Permutation result = identity();
    for (Permutation permutation : permutations)
      result = result.compose(permutation);
    return result;
  }

  /**
   * Take the product of the given permutations. If the input is empty, a permutation of length {@code 0} is returned.
   *
   * @param permutations an iterable of permutations
   * @return the product of the input
   * @see #compose
   */
  static Permutation product(Iterable<Permutation> permutations) {
    Permutation result = identity();
    for (Permutation permutation : permutations)
      result = result.compose(permutation);
    return result;
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
    return define(Rankings.invert(ranking), false);
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
   * @see #cycle0
   */
  static Permutation move(int delete, int insert) {
    return cycle0(ArrayUtil.range(insert, delete, true));
  }

  /**
   * <p>Calculate the orbit of given index.
   * The orbit is an array of distinct integers that contains all indexes</p>
   * <pre><code>
   *   i, apply(i), apply(apply(i)), ...
   * </code></pre>
   * <p>The orbit always contains at least one element, that is the start index {@code i} itself.</p>
   *
   * @param i a non negative number which is less than {@code this.length()}
   * @return the orbit of {@code i}
   * @exception java.lang.IllegalArgumentException if {@code i < 0} or {@code i >= this.length}.
   */
  int[] orbit(int i) {
    return CycleUtil.orbit(ranking, i);
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
   * <p>Determine whether this permutation has at most one than one nontrivial orbit.</p>
   *
   * @return true if this permutation is a cycle
   * @see #cycle0
   * @see #orbit
   */
  boolean isCycle() {
    return CycleUtil.isCyclicRanking(ranking);
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
    return define(result, false);
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
   * Returns a shifted permutation. The following is true for the shifted permutation:
   * <pre><code>
   *   p.shift(n).apply(j) = j, j &lt; n
   *   p.shift(n).apply(n + i) = n + p.apply(i)
   * </code></pre>
   *
   * @param n a non negative number
   * @return the shifted permutation
   * @exception java.lang.IllegalArgumentException if n is negative
   */
  Permutation shift(int n) {
    if (ranking.length == 0 && n == 0)
      return this;
    return define(Rankings.shift(n, ranking), false);
  }

  /**
   * Find a cycle in this permutation or return {@code null} if this is the identity.
   *
   * @return a cycle in this permutation or {@code null} if there are no cycles because this is the identity
   */
  int[] findCycle() {
    if (ranking.length == 0)
      return null;
    for (int i = 0; i < ranking.length; i++)
      if (ranking[i] != i)
        return orbit(i);
    throw new IllegalStateException(); // we'll never get here
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

  /* ============== apply to arrays ============== */

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @param <T> type param
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see #apply(int)
   * @see #toCycles()
   * @see Cycles#apply(Object[])
   */
  <T> T[] apply(T[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(byte[])
   * @see #apply(int)
   */
  byte[] apply(byte[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(short[])
   * @see #apply(int)
   */
  short[] apply(short[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(int[])
   * @see #apply(int)
   */
  int[] apply(int[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(long[])
   * @see #apply(int)
   */
  long[] apply(long[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(float[])
   * @see #apply(int)
   */
  float[] apply(float[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see #apply(int)
   * @see Cycles#clobber(double[])
   */
  double[] apply(double[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see #apply(int)
   */
  boolean[] apply(boolean[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange an array. This method does not modify its input array.
   *
   * @param input an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input.length < this.length()}
   * @see Cycles#clobber(char[])
   * @see #apply(int)
   */
  char[] apply(char[] input) {
    if (this.ranking.length == 0)
      return input;
    return Rankings.apply(ranking, input);
  }

  /**
   * Rearrange the characters in the string.
   *
   * @param s a string of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code s}
   * @exception java.lang.IllegalArgumentException if {@code s.length() < this.length()}
   * @see #apply(int)
   */
  String apply(String s) {
    if (this.ranking.length == 0)
      return s;
    char[] dst = new char[s.length()];
    s.getChars(0, s.length(), dst, 0);
    return new String(apply(dst));
  }

  /**
   * Rearrange a list. This method does not modify the input list.
   *
   * @param input a list that must have at least {@code this.length()} elements
   * @param <E> type param
   * @return the result of applying this permutation to {@code input}
   * @exception java.lang.IllegalArgumentException if {@code input} has less than {@code this.length()} elements
   * @see Cycles#clobber(List)
   * @see #apply(int)
   */
  <E> List<E> apply(List<E> input) {
    if (ranking.length == 0)
      return input;
    int length = input.size();
    checkLength(ranking.length, length);
    return Rankings.apply(ranking, input);
  }

  static Permutation sorting(byte[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(short[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(long[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(float[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(double[] input) {
    return define(Rankings.sorting(input), false);
  }

  static final class SortingBuilder<E> {
    private final E[] a;

    SortingBuilder(E[] a) {
      this.a = a;
    }

    Permutation using(Comparator<E> comparator) {
      return define(Rankings.sorting(a, comparator), false);
    }
  }

  static <E extends Comparable> Permutation sorting(E[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(char[] input) {
    return define(Rankings.sorting(input), false);
  }


  static <E> SortingBuilder<E> sorting(E[] input) {
    return new SortingBuilder<>(input);
  }

  static Permutation sorting(int[] input) {
    return define(Rankings.sorting(input), false);
  }

  static Permutation sorting(String s) {
    char[] chars = new char[s.length()];
    s.getChars(0, chars.length, chars, 0);
    return sorting(chars);
  }

  static final class TakingBuilder<E extends Comparable> {
    private final E[] from;

    private TakingBuilder(E[] from) {
      this.from = from;
    }

    Permutation to(E[] to) {
      return define(Rankings.from(from, to), false, false);
    }
  }

  static final class TakingBuilderInt {
    private final int[] from;

    private TakingBuilderInt(int[] from) {
      this.from = from;
    }

    Permutation to(int[] to) {
      return define(Rankings.from(from, to), false, false);
    }
  }

  static final class TakingBuilderLong {
    private final long[] from;

    private TakingBuilderLong(long[] from) {
      this.from = from;
    }

    Permutation to(long[] to) {
      return define(Rankings.from(from, to), false, false);
    }
  }

  static final class TakingBuilderDouble {
    private final double[] from;

    private TakingBuilderDouble(double[] from) {
      this.from = from;
    }

    Permutation to(double[] to) {
      return define(Rankings.from(from, to), false, false);
    }
  }

  static final class TakingBuilderComp<E> {

    private final E[] from;
    private final E[] to;

    private TakingBuilderComp(E[] from, E[] to) {
      this.from = from;
      this.to = to;
    }

    Permutation using(Comparator<E> comp) {
      return define(Rankings.from(from, to, comp), false);
    }
  }

  static final class TakingBuilderObj<E> {

    private final E[] from;

    private TakingBuilderObj(E[] from) {
      this.from = from;
    }

    TakingBuilderComp<E> to(E[] to) {
      return new TakingBuilderComp<>(from, to);
    }

  }


  static TakingBuilderInt taking(int[] a) {
    return new TakingBuilderInt(a);
  }

  static <E extends Comparable> TakingBuilder<E> taking(E[] a) {
    return new TakingBuilder<>(a);
  }

  static TakingBuilderLong taking(long[] a) {
    return new TakingBuilderLong(a);
  }

  static TakingBuilderDouble taking(double[] a) {
    return new TakingBuilderDouble(a);
  }

  static <E> TakingBuilderObj<E> taking(E[] a) {
    return new TakingBuilderObj<>(a);
  }

  boolean sorts(int[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(byte[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(short[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(char[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(long[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(float[] a) {
    return Rankings.sorts(ranking, a);
  }

  boolean sorts(double[] a) {
    return Rankings.sorts(ranking, a);
  }

  <E extends Comparable<E>> boolean sorts(E[] a) {
    return Rankings.sorts(ranking, a);
  }

  <E extends Comparable<E>> boolean sorts(List<E> a) {
    return Rankings.sorts(ranking, a);
  }

  static final class SortsBuilder<E> {
    private final E[] a;
    private final int[] ranking;

    SortsBuilder(E[] a, int[] ranking) {
      this.a = a;
      this.ranking = ranking;
    }

    boolean using(Comparator<E> comparator) {
      return Rankings.sorts(ranking, a, comparator);
    }
  }

  <E> SortsBuilder<E> sorts(E[] a) {
    return new SortsBuilder<>(a, ranking);
  }

  static Stream<Permutation> symmetricGroup(int n) {
    return Rankings.symmetricGroup(n).map(a -> define(a, false));
  }
}
