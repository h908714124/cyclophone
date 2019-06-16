package com.github.cyclophone;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Morphisms {

  static boolean isMorphism(
      int n,
      Function<Permutation, Permutation> m) {
    List<Permutation> universe = SymmetricGroup.symmetricGroup(n).collect(Collectors.toList());
    for (Permutation p1 : universe) {
      for (Permutation p2 : universe) {
        Permutation img1 = m.apply(p1.compose(p2));
        Permutation img2 = m.apply(p1).compose(m.apply(p2));
        if (!Objects.equals(img1, img2)) {
          return false;
        }
      }
    }
    return true;
  }
}
