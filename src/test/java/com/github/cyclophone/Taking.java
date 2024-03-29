package com.github.cyclophone;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cyclophone.ArrayUtil.checkEqualLength;
import static com.github.cyclophone.Permutation.define0;
import static com.github.cyclophone.Rankings.invert;
import static com.github.cyclophone.Rankings.nextOffsetShifting;
import static com.github.cyclophone.Sorting.sorting;
import static com.github.cyclophone.Rankings.unshift;
import static java.util.Arrays.binarySearch;

final class Taking {

  static final class TakingBuilderInt {
    private final int[] from;

    private TakingBuilderInt(int[] from) {
      this.from = from;
    }

    Permutation to(int[] to) {
      return define0(from(from, to));
    }
  }

  static TakingBuilderInt taking(int[] a) {
    return new TakingBuilderInt(a);
  }

  /**
   * Produce a particular ranking that produces {@code b} when applied to {@code a}.
   *
   * @param a an array
   * @param b an array
   * @return a ranking that produces {@code b} when applied to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code b} can not be obtained by rearranging {@code a}
   * @exception java.lang.NullPointerException if any argument is {@code null}
   */
  static int[] from(int[] a, int[] b) {
    checkEqualLength(a, b);
    int[] sort = sorting(b);
    int[] unsort = invert(sort);
    int[] sorted = apply(sort, b);
    int[] ranking = new int[a.length];
    int[] offsets = new int[a.length];
    for (int i = 0; i < a.length; i += 1) {
      int idx = binarySearch(sorted, a[i]);
      if (idx < 0)
        return null;
      int offset = nextOffsetShifting(idx, offsets[idx], sorted);
      if (offset == 0)
        return null;
      ranking[i] = unsort[idx + unshift(offset)];
      offsets[idx] = offset;
    }
    return ranking;
  }

  static final class TakingBuilder<E extends Comparable> {
    private final E[] from;

    private TakingBuilder(E[] from) {
      this.from = from;
    }

    Permutation to(E[] to) {
      return define0(From.from(from, to));
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
      return define0(From.from(from, to, comp));
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

  static <E extends Comparable> TakingBuilder<E> taking(E[] a) {
    return new TakingBuilder<>(a);
  }

  static <E> TakingBuilderObj<E> taking(E[] a) {
    return new TakingBuilderObj<>(a);
  }

  static int[] apply(int[] ranking, int[] input) {
    List<Integer> integers = Arrays.stream(input).boxed().collect(Collectors.toList());
    List<Integer> result = Rankings.apply(ranking, integers);
    return result.stream().mapToInt(Integer::intValue).toArray();
  }
}
