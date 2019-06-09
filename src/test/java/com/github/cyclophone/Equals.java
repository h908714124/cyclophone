package com.github.cyclophone;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class Equals {

  /**
   * Equality test. In order for permutations to be equal, they must have the same length, and their effects
   * on indexes and arrays must be identical.
   *
   * @param other another object
   * @return true if the other object is an equivalent permutation
   */
  static boolean equals(Permutation p, Permutation other) {
    if (p == other)
      return true;
    return Arrays.equals(p.getRanking(), other.getRanking());
  }

  static int hashCode(Permutation permutation) {
    return Arrays.hashCode(permutation.getRanking());
  }

  static void assertPermutationEquals(Permutation p1, Permutation p2, String message) {
    assertTrue(equals(p1, p2), message);
  }

  static void assertPermutationEquals(Permutation p1, Permutation p2) {
    assertTrue(equals(p1, p2));
  }
}
