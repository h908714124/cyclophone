package com.github.cyclophone;

import static com.github.cyclophone.Permutation.define;

public class RandomPermutation {

  /**
   * Creates a random permutation of given length.
   *
   * @param length the length of arrays that the result can be applied to
   * @return a random permutation that can be applied to an array of length {@code length}
   */
  static Permutation random(int length) {
    return define(Rankings.random(length));
  }
}
