package com.github.cyclophone;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.github.cyclophone.ArrayUtil.INT_0;
import static com.github.cyclophone.ArrayUtil.checkLength;
import static com.github.cyclophone.ArrayUtil.lengthFailure;
import static com.github.cyclophone.ArrayUtil.negativeFailure;
import static com.github.cyclophone.ArrayUtil.withIndex;
import static java.lang.System.arraycopy;

/**
 * A collection of methods that return rankings, or operate on rankings.
 */
final class Rankings {

  private Rankings() {
  }

  private static final Comparator<int[]> COMPARE_FIRST = Comparator.comparingInt(a -> a[0]);

  /**
   * Check that the input ranking is valid. In order to be valid, each non-negative integer less than
   * {@code a.length} must appear exactly once.
   *
   * @param a an array
   * @return true if a is a ranking
   */
  static boolean isValid(int[] a) {
    boolean[] used = new boolean[a.length];
    for (int value : a) {
      if (value < 0 || value >= a.length) {
        return false;
      }
      if (used[value]) {
        return false;
      }
      used[value] = true;
    }
    return true;
  }

  /**
   * Find the length that this ranking can be safely trimmed to.
   *
   * @param ranking a ranking
   * @return the length that this ranking can be safely trimmed to
   */
  private static int trimmedLength(int[] ranking) {
    for (int i = ranking.length - 1; i >= 0; i--)
      if (ranking[i] != i) {
        return i + 1;
      }
    return 0;
  }

  /**
   * If possible, trim ranking to something shorter but equivalent.
   *
   * @param ranking a ranking
   * @return the trimmed ranking
   */
  static int[] trim(int[] ranking) {
    int length = trimmedLength(ranking);
    if (length == 0) {
      return INT_0;
    }
    if (length < ranking.length) {
      return Arrays.copyOf(ranking, length);
    }
    return ranking;
  }

  /**
   * Opposite of {@link #trim(int[])}
   * @param ranking
   * @param length
   * @return filled ranking
   */
  static int[] fill(int[] ranking, int length) {
    if (ranking.length == length) {
      return ranking;
    }
    if (ranking.length > length) {
      throw new IllegalArgumentException("ranking length: " + ranking.length);
    }
    int[] result = Arrays.copyOf(ranking, length);
    for (int i = ranking.length; i < result.length; i++) {
      result[i] = i;
    }
    return result;
  }

  /**
   * Return a ranking that acts similar to the input but operates on higher indexes,
   * leaving the lower {@code n} indexes unmoved.
   *
   * @param n a non-negative number
   * @param ranking a ranking
   * @return the shifted ranking
   * @exception java.lang.IllegalArgumentException if {@code n} is negative
   */
  static int[] shift(int n, int[] ranking) {
    if (n < 0) {
      negativeFailure();
    }
    if (n == 0) {
      return ranking;
    }
    int[] shifted = new int[n + ranking.length];
    for (int i = 1; i < n; i++)
      shifted[i] = i;
    for (int i = 0; i < ranking.length; i++)
      shifted[i + n] = ranking[i] + n;
    return shifted;
  }

  /**
   * Ensure that the input is a ranking.
   *
   * @param a an array
   * @return the input ranking
   * @exception java.lang.IllegalArgumentException if {@code a} is not a valid ranking
   * @see #isValid
   */
  static int[] checkRanking(int[] a) {
    if (!isValid(a)) {
      String msg = "argument is not a ranking";
      if (a.length < 20) {
        msg += ": " + Arrays.toString(a);
      }
      throw new IllegalArgumentException(msg);
    }
    return a;
  }

  /**
   * Calculate the inverse ranking.
   * This method does not check if the input is indeed a ranking and may have unexpected results otherwise.
   *
   * @param ranking a ranking
   * @return the inverse ranking
   */
  static int[] invert(int[] ranking) {
    int[][] rankingWithIndex = withIndex(ranking);
    Arrays.sort(rankingWithIndex, COMPARE_FIRST);
    int[] inverted = new int[ranking.length];
    for (int i = 0; i < ranking.length; i += 1)
      inverted[i] = rankingWithIndex[i][1];
    return inverted;
  }

  /**
   * Multiply two rankings.
   *
   * @param lhs a ranking
   * @param rhs another ranking
   * @return the product of the input rankings
   */
  static int[] comp(int[] lhs, int[] rhs) {
    if (lhs.length >= rhs.length) {
      if (rhs.length == 0) {
        return lhs;
      }
      int[] result = new int[lhs.length];
      for (int i = 0; i < rhs.length; i++)
        result[i] = lhs[rhs[i]];
      if (lhs.length > rhs.length) {
        arraycopy(lhs, rhs.length, result, rhs.length, lhs.length - rhs.length);
      }
      return result;
    }
    if (lhs.length == 0) {
      return rhs;
    }
    int[] result = new int[rhs.length];
    for (int i = 0; i < rhs.length; i++) {
      int n = rhs[i];
      result[i] = n >= lhs.length ? n : lhs[n];
    }
    return result;
  }

  /* ================= nextOffset ================= */

  /**
   * Find the next position, after {@code idx + offset}, of {@code sorted[idx]} in a sorted array.
   * For a given element {@code el}, iterating this method over the {@code offset} element, starting with
   * {@code offset = 0}, will enumerate all positions of {@code sorted[idx]} in the sorted array.
   *
   * @param idx the start index
   * @param offset the current offset from the start index
   * @param sorted a sorted array
   * @return the next offset or {@code 0} if there is no next offset
   */
  static int nextOffset(final int idx, int offset, final int[] sorted) {
    if (offset >= 0) {
      int next = idx + ++offset;
      if (next >= sorted.length || sorted[next] != sorted[idx]) {
        if (idx == 0 || sorted[idx - 1] != sorted[idx]) {
          return 0;
        } else {
          return -1;
        }
      }
    } else {
      int next = idx + --offset;
      if (next < 0 || sorted[next] != sorted[idx]) {
        return 0;
      }
    }
    return offset;
  }

  /**
   * Find the next position, after {@code idx + offset}, of {@code sorted[idx]} in a sorted array.
   * For a given element {@code el}, iterating this method over the {@code offset} element, starting with
   * {@code offset = 0}, will enumerate all positions of {@code sorted[idx]} in the sorted array.
   *
   * @param idx the start index
   * @param offset the current offset from the start index
   * @param sorted a sorted array
   * @return the next offset or {@code 0} if there is no next offset
   */
  static int nextOffset(final int idx, int offset, final long[] sorted) {
    if (offset >= 0) {
      int next = idx + ++offset;
      if (next >= sorted.length || sorted[next] != sorted[idx]) {
        if (idx == 0 || sorted[idx - 1] != sorted[idx]) {
          return 0;
        } else {
          return -1;
        }
      }
    } else {
      int next = idx + --offset;
      if (next < 0 || sorted[next] != sorted[idx]) {
        return 0;
      }
    }
    return offset;
  }

  /**
   * Find the next position, after {@code idx + offset}, of {@code sorted[idx]} in a sorted array.
   * For a given element {@code el}, iterating this method over the {@code offset} element, starting with
   * {@code offset = 0}, will enumerate all positions of {@code sorted[idx]} in the sorted array.
   *
   * @param idx the start index
   * @param offset the current offset from the start index
   * @param sorted a sorted array
   * @return the next offset or {@code 0} if there is no next offset
   */
  static int nextOffset(final int idx, int offset, final Object[] sorted) {
    if (offset >= 0) {
      int next = idx + ++offset;
      if (next >= sorted.length || !sorted[next].equals(sorted[idx])) {
        if (idx == 0 || !sorted[idx - 1].equals(sorted[idx])) {
          return 0;
        } else {
          return -1;
        }
      }
    } else {
      int next = idx + --offset;
      if (next < 0 || !sorted[next].equals(sorted[idx])) {
        return 0;
      }
    }
    return offset;
  }

  /* ================= shift ================= */

  /**
   * Encode an int as a non-zero int
   *
   * @param i an int
   * @return {@code i + 1} if {@code i} is non-negative, otherwise {@code i}
   * @see #unshift
   */
  private static int shift(int i) {
    return i >= 0 ? i + 1 : i;
  }

  /**
   * Undo the shift
   *
   * @param i a non-zero int
   * @return {@code i - 1} if {@code i} is positive, otherwise {@code i}
   * @see #shift
   */
  static int unshift(int i) {
    return i > 0 ? i - 1 : i;
  }

  static int nextOffsetShifting(final int idx, int shiftedOffset, final int[] sorted) {
    if (shiftedOffset == 0) {
      return 1;
    }
    int offset = nextOffset(idx, unshift(shiftedOffset), sorted);
    return offset == 0 ? 0 : shift(offset);
  }

  static int nextOffsetShifting(final int idx, int shiftedOffset, final long[] sorted) {
    if (shiftedOffset == 0) {
      return 1;
    }
    int offset = nextOffset(idx, unshift(shiftedOffset), sorted);
    return offset == 0 ? 0 : shift(offset);
  }

  static int nextOffsetShifting(final int idx, int shiftedOffset, final Object[] sorted) {
    if (shiftedOffset == 0) {
      return 1;
    }
    int offset = nextOffset(idx, unshift(shiftedOffset), sorted);
    return offset == 0 ? 0 : shift(offset);
  }

  /* ================= sorting ================= */

  /**
   * Check where the {@code ranking} moves the index {@code i}.
   * The following is true for all {@code j &lt; a.length}:
   * {@code
   * apply(ranking, a)[apply(ranking, j)] == a[j];
   * }
   * This method does not check whether the input ranking is valid.
   *
   * @param i a non negative number
   * @return the moved index
   * @exception java.lang.IllegalArgumentException if {@code i} is negative
   */
  static int apply(int[] ranking, int i) {
    if (i < 0) {
      negativeFailure();
    }
    if (i >= ranking.length) {
      return i;
    }
    return ranking[i];
  }

  /**
   * Apply the ranking to the input array. An element at {@code i} is moved to {@code ranking[i]}.
   * Indexes that are greater or equal to the length of the ranking are not moved.
   * This method does not check that the first argument is indeed a ranking.
   *
   * @param <T> type param
   * @param ranking a ranking
   * @param input an input array
   * @return the result of applying the ranking to the input
   * @exception java.lang.IllegalArgumentException if the length of {@code input} is less than the length of {@code ranking}
   */
  static <T> T[] apply(int[] ranking, T[] input) {
    checkLength(ranking.length, input.length);
    Class<?> type = input.getClass().getComponentType();
    @SuppressWarnings("unchecked")
    T[] result = (T[]) Array.newInstance(type, input.length);
    for (int i = 0; i < ranking.length; i += 1)
      result[ranking[i]] = input[i];
    if (input.length > ranking.length) {
      arraycopy(input, ranking.length, result, ranking.length, input.length - ranking.length);
    }
    return result;
  }

  /**
   * Apply the ranking to the input list. An element at {@code i} is moved to {@code ranking[i]}.
   * Indexes that are greater or equal to the length of the ranking are not moved.
   * This method does not check if the first argument is indeed a ranking, and will have unexpected results otherwise.
   *
   * @param ranking a ranking
   * @param <E> type param
   * @param input an input array
   * @return the result of applying the ranking to the input
   * @exception java.lang.IllegalArgumentException if the length of {@code input} is less than the length of {@code ranking}
   * @exception java.lang.ArrayIndexOutOfBoundsException can be thrown if the {@code ranking} argument is not a ranking
   */
  static <E> List<E> apply(int[] ranking, List<E> input) {
    if (ranking.length == 0) {
      return input;
    }
    int length = input.size();
    checkLength(ranking.length, length);
    ArrayList<E> result = new ArrayList<E>(length);
    for (int i = 0; i < length; i += 1)
      result.add(null);
    for (int i = 0; i < length; i += 1)
      result.set(apply(ranking, i), input.get(i));
    return result;
  }

  /* ================= sorts ================= */

  /**
   * Check if the input ranking will sort the input array when applied to it.
   * This method does not check if the first argument is indeed a valid ranking, and will have unexpected results otherwise.
   *
   * @param a an array
   * @param ranking a ranking
   * @return true if the return value of {@code apply(ranking, a)} is a sorted array
   * @see #isValid
   */
  static boolean sorts(int[] ranking, int[] a) {
    if (a.length < ranking.length) {
      lengthFailure();
    }
    if (a.length < 2) {
      return true;
    }
    int idx = apply(ranking, 0);
    int test = a[0];
    for (int i = 1; i < a.length; i++) {
      int idx2 = apply(ranking, i);
      int test2 = a[i];
      if (idx2 > idx) {
        if (test > test2) {
          return false;
        }
      } else if (test < test2) {
        return false;
      }
      idx = idx2;
      test = test2;
    }
    return true;
  }

  /**
   * <p>Returns all possible permutations of given length.</p>
   *
   * <h3>Note that this stream may contain modified versions of the same array,
   * so be sure to make array copies if immutability is needed.</h3>
   *
   * @param n length of permutations to generate
   * @return all possible permutations of length {@code n}; this will contain {@code n!}
   * different permutations
   */
  static Stream<int[]> symmetricGroup(int n) {
    Iterable<int[]> it = () -> new SymmetricGroupIterator(n);
    return StreamSupport.stream(it.spliterator(), false);
  }

  private static class SymmetricGroupIterator implements Iterator<int[]> {

    FrameStack stack; // should be faster than java.util.Stack
    int n;

    SymmetricGroupIterator(int n) {
      this.stack = new FrameStack(n);
      this.n = n;
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public int[] next() {
      while (stack.getLastLength() < n) {
        stack.expandLast();
      }
      return stack.removeLast();
    }
  }
}
