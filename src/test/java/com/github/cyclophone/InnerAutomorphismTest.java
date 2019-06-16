package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.cyclophone.InnerAutomorphism.conjugationBy;
import static com.github.cyclophone.Morphisms.isMorphism;
import static com.github.cyclophone.Permutation.cycle;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InnerAutomorphismTest {

  @Test
  void testMorphism() {
    Automorphism m = conjugationBy(cycle(1, 2));
    Assertions.assertTrue(isMorphism(5, m));
  }

  @Test
  void testApply() {
    Automorphism m = conjugationBy(cycle(1, 2));
    assertEquals(cycle(1, 3, 2), m.apply(cycle(1, 2, 3)));
  }
}