package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import static com.github.cyclophone.Apply.apply;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranspositionTest {

  @Test
  void testProd() {
    Permutation p = Transposition.product(Transposition.swap(0, 1), Transposition.swap(1, 2));
    assertEquals("cab", apply(p, "abc"));
  }

  @Test
  void testCommute() {
    Transposition.TranspositionFactory factory = new Transposition.TranspositionFactory(10);
    for (int __ = 0; __ < 10; __++) {
      Transposition p = Transposition.random(factory, 10);
      Transposition q = Transposition.random(factory, 10);
      if (Equals.equals(Transposition.product(p, q), Transposition.product(q, p))) {
        assertTrue(p.commutesWith(q));
        assertTrue(q.commutesWith(p));
      } else {
        assertFalse(p.commutesWith(q), "p=" + p + ",q=" + q);
        assertFalse(q.commutesWith(p));
      }
    }
  }

}
