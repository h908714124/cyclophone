package com.github.cyclophone;

import java.util.Set;

final class Subgroup {

  static boolean isSubgroup(Set<Permutation> permutations) {
    for (Permutation p1 : permutations) {
      for (Permutation p2 : permutations) {
        if (!permutations.contains(p1.compose(p2))) {
          return false;
        }
      }
    }
    return permutations.contains(Permutation.identity());
  }
}
