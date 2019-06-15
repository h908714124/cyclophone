package com.github.cyclophone;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  static boolean isNormal(int n, Set<Permutation> permutations) {
    List<Permutation> universe = SymmetricGroup.symmetricGroup(n).collect(Collectors.toList());
    for (Permutation u : universe) {
      for (Permutation p : permutations) {
        if (!permutations.contains(p.conjugationBy(u))) {
          return false;
        }
      }
    }
    return true;
  }
}
