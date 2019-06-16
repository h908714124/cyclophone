package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.cyclophone.ConjugationAutomorphism.conjugationBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConjugationAutomorphismTest {

  @Test
  void testOrder() {
    List<Permutation> s6 = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    for (Permutation p : s6) {
      Automorphism m = conjugationBy(p);
      for (Permutation test : s6) {
        assertEquals(test.order(), m.apply(test).order());
      }
    }
  }
}