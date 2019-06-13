package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OuterAutomorphismTest {

  @Test
  void testConjugationClasses() {
    List<Permutation> s6 = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    TreeSet<Automorphism> morphisms = new TreeSet<>();
    for (Permutation p : s6) {
      ConjugationAutomorphism m = new ConjugationAutomorphism(p);
      Automorphism v = m.compose(OuterAutomorphism.getInstance());
      morphisms.add(v);
    }
    Assertions.assertEquals(720, morphisms.size());
  }

  @Test
  void testOrder() {
    List<Permutation> s6 = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    for (Permutation test : s6) {
      assertEquals(test.order(), OuterAutomorphism.getInstance().apply(test).order());
    }
  }
}