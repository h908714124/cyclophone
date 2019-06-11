package com.github.cyclophone;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.github.cyclophone.Permutation.cycle;
import static com.github.cyclophone.Span.span;
import static com.github.cyclophone.TestUtil.PERMUTATION_COMPARATOR;
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

  @Test
  void testCosets() {
    Permutation b1 = cycle(2, 4, 6, 5);
    Permutation b2 = cycle(1, 2, 6).compose(cycle(3, 4, 5));
    List<Set<Permutation>> spans = new ArrayList<>();
    Set<Permutation> span0 = span(b1, b2);
    spans.add(span0);
    List<Permutation> sym = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    for (Permutation p : sym) {
      Set<Permutation> span1 = product(span0, p);
      if (!contains(spans, span1)) {
        spans.add(span1);
      }
    }
    for (int i = 0; i < spans.size(); i++) {
      Set<Permutation> permutation = spans.get(i);
      System.out.println("=====  " + (i + 1) + "  ======");
      for (Permutation p : permutation) {
        System.out.println(p.toCycles().print() + ", ");
      }
    }
  }


  private Set<Permutation> product(Set<Permutation> s, Permutation p) {
    Set<Permutation> result = new TreeSet<>(PERMUTATION_COMPARATOR);
    for (Permutation permutation : s) {
      result.add(p.compose(permutation));
    }
    return result;
  }

  private boolean contains(List<Set<Permutation>> spans, Set<Permutation> set) {
    for (Set<Permutation> span : spans) {
      if (setEquals(span, set)) {
        return true;
      }
    }
    return false;
  }

  private boolean setEquals(Set<Permutation> s1, Set<Permutation> s2) {
    for (Permutation p1 : s1) {
      if (!s2.contains(p1)) {
        return false;
      }
    }
    for (Permutation p2 : s2) {
      if (!s1.contains(p2)) {
        return false;
      }
    }
    return true;
  }
}