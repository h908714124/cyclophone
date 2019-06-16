package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.github.cyclophone.InnerAutomorphism.conjugationBy;
import static com.github.cyclophone.Morphisms.isMorphism;
import static com.github.cyclophone.Permutation.cycle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OuterAutomorphismTest {

  @Test
  void testConjugationClasses() {
    List<Permutation> s6 = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    TreeSet<Automorphism> morphisms = new TreeSet<>();
    OuterAutomorphism m = OuterAutomorphism.getInstance();
    for (Permutation p : s6) {
      Automorphism v = conjugationBy(p).compose(m);
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

  @Test
  void testMorphism() {
    Automorphism m = OuterAutomorphism.getInstance();
    assertTrue(isMorphism(6, m));
  }

  @Test
  void testSynthemes() {
    List<Permutation> transpositions = transpositions();
    assertEquals(15, transpositions.size());
  }

  private List<Permutation> transpositions() {
    List<Permutation> result = new ArrayList<>(15);
    for (int i = 1; i <= 6; i++) {
      for (int j = i + 1; j <= 6; j++) {
        result.add(cycle(i, j));
      }
    }
    return result;
  }
}
