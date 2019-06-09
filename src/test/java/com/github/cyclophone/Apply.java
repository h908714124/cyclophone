package com.github.cyclophone;

import java.util.Arrays;
import java.util.stream.IntStream;

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
}
