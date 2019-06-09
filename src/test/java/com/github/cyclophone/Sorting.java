package com.github.cyclophone;

import java.util.Comparator;

import static com.github.cyclophone.Permutation.define;

public class Sorting {

  static final class SortingBuilder<E> {
    
    private final E[] a;

    SortingBuilder(E[] a) {
      this.a = a;
    }

    Permutation using(Comparator<E> comparator) {
      return define(Rankings.sorting(a, comparator));
    }
  }

  static <E extends Comparable> Permutation sorting(E[] input) {
    return define(Rankings.sorting(input));
  }

  static <E> SortingBuilder<E> sorting(E[] input) {
    return new SortingBuilder<>(input);
  }

  static Permutation sorting(int[] input) {
    return define(Rankings.sorting(input));
  }

  static boolean sorts(Permutation p, int[] a) {
    return Rankings.sorts(p.getRanking(), a);
  }
}
