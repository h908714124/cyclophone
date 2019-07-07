package com.github.cyclophone;

import java.util.stream.Stream;

class SymmetricGroup {

  static Stream<Permutation> symmetricGroup(int n) {
    return Rankings.symmetricGroup(n).map(Permutation::define0);
  }
}
