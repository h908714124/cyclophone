package com.github.cyclophone;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.github.cyclophone.ArrayUtil.negativeFailure;

final class Apply {

  static <T> T[] apply(Permutation p, T[] input) {
    int[] ranking = p.getRanking();
    return Rankings.apply(ranking, input);
  }

  static int[] apply(Permutation p, int[] input) {
    Integer[] integers = Arrays.stream(input).boxed().toArray(Integer[]::new);
    Integer[] result = apply(p, integers);
    return Arrays.stream(result).mapToInt(Integer::intValue).toArray();
  }

  static String apply(Permutation p, String s) {
    Character[] chars = IntStream.range(0, s.length())
        .mapToObj(s::charAt)
        .toArray(Character[]::new);
    Character[] result = apply(p, chars);
    StringBuilder sb = new StringBuilder(result.length);
    Arrays.stream(result).forEach(sb::append);
    return sb.toString();
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
  static int apply(Permutation p, int i) {
    if (i < 0)
      negativeFailure();
    if (i >= p.length())
      return i;
    return p.getRanking()[i];
  }
}
