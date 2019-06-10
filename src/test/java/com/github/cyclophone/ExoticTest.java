package com.github.cyclophone;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.cyclophone.Permutation.cycle;
import static com.github.cyclophone.Span.span;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExoticTest {

  @Test
  void testGenerateKlein4Group() {
    Set<Permutation> span = span(
        cycle(1, 2).compose(cycle(3, 4)),
        cycle(1, 3).compose(cycle(2, 4)));
    assertEquals(4, span.size());
    assertTrue(span.contains(cycle(1, 4).compose(cycle(2, 3))));
    assertTrue(span.contains(Permutation.identity()));
  }

  @Disabled
  @Test
  void testRandom() {
    for (int i = 0; i < 100; i++) {
      Permutation p1 = RandomPermutation.random(6);
      Permutation p2 = RandomPermutation.random(6);
      Set<Permutation> span = span(p1, p2);
//      System.out.println(span.size());
      if (span.size() == 120) {
        System.out.println(p1);
        System.out.println(p2);
        break;
      }
    }
  }

  @Test
  void testExoticEmbedding() {
    Permutation b1 = cycle(2, 4, 6, 5);
    Permutation b2 = cycle(1, 2, 6).compose(cycle(3, 4, 5));
    Set<Permutation> span = span(b1, b2);
    assertEquals(120, span.size());
    for (Permutation permutation : span) {
      System.out.println(permutation);
    }
//    for (Permutation p1 : span) {
//      for (Permutation p2 : span) {
//        if (span(p1, p2).size() == 120) {
//          System.out.println(p1 + " | " + p2);
//        }
//      }
//    }
  }

  @Disabled
  @Test
  void testConjugate() {
    Permutation b1 = cycle(2, 4, 6, 5);
    Permutation b2 = cycle(1, 2, 6).compose(cycle(3, 4, 5));
    List<Set<Permutation>> spans = new ArrayList<>();
    spans.add(span(b1, b2));
    List<Permutation> sym = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    for (Permutation p : sym) {
      Permutation p1 = b1.conjugationBy(p);
      Permutation p2 = b2.conjugationBy(p);
      boolean add = true;
      for (Set<Permutation> span : spans) {
        if (span.contains(p1) || span.contains(p2)) {
          add = false;
          break;
        }
      }
      if (add) {
        spans.add(span(p1, p2));
      }
    }
    System.out.println(spans.size());
  }
}