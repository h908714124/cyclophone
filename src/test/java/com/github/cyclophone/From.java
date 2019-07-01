package com.github.cyclophone;

import java.util.Comparator;

import static com.github.cyclophone.ArrayUtil.checkEqualLength;
import static com.github.cyclophone.Sorting.sorting;
import static java.util.Arrays.binarySearch;

class From {

  /**
   * Produce a particular ranking that produces {@code b} when applied to {@code a}.
   *
   * @param a an array
   * @param b an array
   * @return a ranking that produces {@code b} when applied to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code b} can not be obtained by rearranging {@code a}
   * @exception java.lang.NullPointerException if any argument is {@code null}
   */
  static <E extends Comparable> int[] from(E[] a, E[] b) {
    checkEqualLength(a, b);
    int[] sort = sorting(b);
    int[] unsort = Rankings.invert(sort);
    Comparable[] sorted = Rankings.apply(sort, b);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i += 1) {
      int idx = binarySearch(sorted, a[i]);
      if (idx < 0) {
        return null;
      }
      int offset = Rankings.nextOffsetShifting(idx, offsets[idx], sorted);
      if (offset == 0) {
        return null;
      }
      ranking[i] = unsort[idx + Rankings.unshift(offset)];
      offsets[idx] = offset;
    }
    return ranking;
  }

  /**
   * Produce a particular ranking that produces {@code b} when applied to {@code a}.
   *
   * @param a an array
   * @param b an array
   * @return a ranking that produces {@code b} when applied to {@code a}
   * @exception java.lang.NullPointerException if any argument is null, or if {@code a} or {@code b}
   * contain a {@code null} element
   * @exception java.lang.IllegalArgumentException if {@code b} can not be obtained by rearranging {@code a}
   * @see Rankings#apply(int[], java.lang.Object[])
   */
  static <E> int[] from(E[] a, E[] b, Comparator<E> comp) {
    checkEqualLength(a, b);
    int[] sort = sorting(b, comp);
    int[] unsort = Rankings.invert(sort);
    Object[] sorted = Rankings.apply(sort, b);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i += 1) {
      @SuppressWarnings("unchecked")
      int idx = binarySearch(sorted, a[i], (Comparator) comp);
      if (idx < 0) {
        return null;
      }
      int offset = Rankings.nextOffsetShifting(idx, offsets[idx], sorted);
      if (offset == 0) {
        return null;
      }
      ranking[i] = unsort[idx + Rankings.unshift(offset)];
      offsets[idx] = offset;
    }
    return ranking;
  }
}
