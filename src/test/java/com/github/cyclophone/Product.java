package com.github.cyclophone;

import static com.github.cyclophone.Permutation.identity;

final class Product {

  /**
   * Take the product of the given permutations.
   *
   * @param permutations an array of permutations
   * @return the product of the input
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
   */
  static Permutation product(Iterable<Permutation> permutations) {
    Permutation result = identity();
    for (Permutation permutation : permutations)
      result = result.compose(permutation);
    return result;
  }
}
