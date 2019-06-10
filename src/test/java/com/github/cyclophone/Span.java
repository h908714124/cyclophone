package com.github.cyclophone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.github.cyclophone.TestUtil.PERMUTATION_COMPARATOR;

final class Span {

  static Set<Permutation> span(Permutation... seed) {
    Set<Permutation> result = new TreeSet<>(PERMUTATION_COMPARATOR);
    Collections.addAll(result, seed);
    int added;
    do {
      added = generationRound(result);
    } while (added > 0);
    return result;
  }

  private static int generationRound(Set<Permutation> result) {
    List<Permutation> permutations = new ArrayList<>();
    for (Permutation p1 : result) {
      for (Permutation p2 : result) {
        permutations.add(p1.compose(p2));
      }
    }
    int count = 0;
    for (Permutation permutation : permutations) {
      boolean added = result.add(permutation);
      if (added) {
        count++;
      }
    }
    return count;
  }
}
