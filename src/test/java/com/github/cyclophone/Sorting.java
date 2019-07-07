package com.github.cyclophone;

import java.util.Arrays;
import java.util.Comparator;

import static com.github.cyclophone.Permutation.define0;
import static com.github.cyclophone.Rankings.checkRanking;
import static com.github.cyclophone.Rankings.nextOffsetShifting;
import static com.github.cyclophone.Rankings.unshift;
import static java.util.Arrays.binarySearch;

class Sorting {

  static final class SortingBuilder<E> {
    
    private final E[] a;

    SortingBuilder(E[] a) {
      this.a = a;
    }

    Permutation using(Comparator<E> comparator) {
      return define0(sorting(a, comparator));
    }
  }

  static <E> SortingBuilder<E> sorting(E[] input) {
    return new SortingBuilder<>(input);
  }

  static boolean sorts(Permutation p, int[] a) {
    return Rankings.sorts(p.getRanking(), a);
  }


  /**
   * Produce a particular ranking that sorts the input when applied to it.
   * For each index {@code i < a.length}, the return value
   * satisfies the following property.
   * Let
   * <pre><code>
   *   int[] sorting = sorting(a);
   *   int[] sorted = apply(sorting, a);
   *   int[] unsort = invert(sorting);
   *   int idx = Arrays.binarySearch(sorted, el);
   * </code></pre>
   * then for each index {@code i < a.length}, the following is true:
   * <pre><code>
   *   ArrayUtil.indexOf(a, el, 0) == unsort[idx]
   * </code></pre>
   *
   * @param a an array
   * @return a ranking that sorts the input
   * @see ArrayUtil#indexOf
   */
  static int[] sorting(int[] a) {
    int[] sorted = sortedCopy(a);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      int idx = binarySearch(sorted, a[i]);
      int offset = nextOffsetShifting(idx, offsets[idx], sorted);
      ranking[i] = idx + unshift(offset);
      offsets[idx] = offset;
    }
    return checkRanking(ranking);
  }

  /**
   * Produce a particular ranking that sorts the input when applied to it.
   * For each index {@code i < a.length}, the return value
   * satisfies the following property.
   * Let
   * <pre><code>
   *   int[] sorting = sorting(a);
   *   int[] sorted = apply(sorting, a);
   *   int[] unsort = invert(sorting);
   *   int idx = Arrays.binarySearch(sorted, el);
   * </code></pre>
   * then for each index {@code i < a.length}, the following is true:
   * <pre><code>
   *   ArrayUtil.indexOf(a, el, 0) == unsort[idx]
   * </code></pre>
   *
   * @param a an array
   * @return a ranking that sorts the input
   * @exception java.lang.NullPointerException if {@code a} is {@code null} or contains a {@code null} element
   * @see ArrayUtil#indexOf
   */
  static <E extends Comparable> int[] sorting(E[] a) {
    Comparable[] sorted = sortedCopy(a);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      int idx = binarySearch(sorted, a[i]);
      int offset = nextOffsetShifting(idx, offsets[idx], sorted);
      ranking[i] = idx + unshift(offset);
      offsets[idx] = offset;
    }
    return ranking;
  }


  /**
   * Produce a particular ranking that sorts the input when applied to it.
   * For each index {@code i < a.length}, the return value
   * satisfies the following property.
   * Let
   * <pre><code>
   *   int[] sorting = sorting(a);
   *   int[] sorted = apply(sorting, a);
   *   int[] unsort = invert(sorting);
   *   int idx = Arrays.binarySearch(sorted, el);
   * </code></pre>
   * then for each index {@code i < a.length}, the following is true:
   * <pre><code>
   *   ArrayUtil.indexOf(a, el, 0) == unsort[idx]
   * </code></pre>
   *
   * @param a an array
   * @param comp a comparator
   * @return a ranking that sorts the input
   * @exception java.lang.NullPointerException if {@code a} is {@code null} or contains a {@code null} element
   * @see ArrayUtil#indexOf
   */
  static <E> int[] sorting(Object[] a, Comparator<E> comp) {
    Object[] sorted = sortedCopy(a, comp);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      @SuppressWarnings("unchecked")
      int idx = binarySearch(sorted, a[i], (Comparator) comp);
      int offset = nextOffsetShifting(idx, offsets[idx], sorted);
      ranking[i] = idx + unshift(offset);
      offsets[idx] = offset;
    }
    return ranking;
  }


  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static char[] sortedCopy(char[] input) {
    char[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static short[] sortedCopy(short[] input) {
    short[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static double[] sortedCopy(double[] input) {
    double[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static float[] sortedCopy(float[] input) {
    float[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static long[] sortedCopy(long[] input) {
    long[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static byte[] sortedCopy(byte[] input) {
    byte[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static Comparable[] sortedCopy(Comparable[] input) {
    Comparable[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @param comp a comparator
   * @return a sorted copy of the input
   */
  @SuppressWarnings("unchecked")
  static Object[] sortedCopy(Object[] input, Comparator comp) {
    Object[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted, comp);
    return sorted;
  }

  /**
   * Returns a sorted copy of the input.
   * @param input an array
   * @return a sorted copy of the input
   */
  static int[] sortedCopy(int[] input) {
    int[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);
    return sorted;
  }

  /**
   * Test if the input contains duplicates.
   * @param a an array
   * @return true if the input contains no duplicate element
   */
  static boolean isUnique(int[] a) {
    if (a.length < 2)
      return true;
    if (!isSorted(a))
      a = sortedCopy(a);
    for (int i = 1; i < a.length; i++)
      if (a[i] == a[i - 1])
        return false;
    return true;
  }


  /**
   * Test if input is sorted
   * @param input an array
   * @return true if the {@code input} is sorted
   */
  static boolean isSorted(int[] input) {
    if (input.length < 2) {
      return true;
    }
    int test = input[0];
    for (int i : input) {
      if (i < test) {
        return false;
      }
      test = i;
    }
    return true;
  }
}
