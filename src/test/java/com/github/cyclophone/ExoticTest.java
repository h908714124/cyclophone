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
import static org.junit.jupiter.api.Assertions.assertEquals;

// steps to construct the outer automorphism of S6
@Disabled
class ExoticTest {

  @Test
  void testRandom() {
    // Step 1: Find a subgroup of S6 of size 120 (the size of S5)
    // This is an exotic embedding of S5 in S6.
    for (int i = 0; i < 100; i++) {
      Permutation p1 = RandomPermutation.random(6);
      Permutation p2 = RandomPermutation.random(6);
      Set<Permutation> span = span(p1, p2);
      if (span.size() == 120) {
        System.out.println("base1: " + p1);
        System.out.println("base2: " + p2);
        System.out.println("==========");
        for (Permutation permutation : span) {
          System.out.println(permutation);
        }
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
  }

  @Test
  void testCosets() {
    // Find all 6 cosets of the exotic embedding of S5.
    // The result is seen in Coset.java.
    //
    // Note: b1 and b2 are base for the exotic embedding.
    // They were found by testRandom above.
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
      System.out.println("=====  Coset " + (i + 1) + "  ======");
      for (Permutation p : permutation) {
        System.out.println(p.toCycles().print() + ", ");
      }
    }
  }

  @Test
  void testOuter() {
    // Construct the mapping table for the outer automorphism of S6.
    // The result is seen in OuterAutomorphism.java.
    List<Permutation> s6 = SymmetricGroup.symmetricGroup(6).collect(Collectors.toList());
    for (Permutation p : s6) {
      Coset[] values = Coset.values();
      int[] ranking = new int[6];
      for (int i = 0; i < values.length; i++) {
        Coset coset = values[i];
        // let p act on coset
        Coset actionResult = coset.act(p);
        // build a ranking from the result
        ranking[i] = actionResult.ordinal();
      }
      System.out.println("map.put(" + p.toCycles().print() + ", " + Permutation.define(ranking).toCycles().print() + ");");
    }
  }

  private Set<Permutation> product(Set<Permutation> s, Permutation p) {
    Set<Permutation> result = new TreeSet<>();
    for (Permutation permutation : s) {
      result.add(p.compose(permutation));
    }
    return result;
  }

  private boolean contains(List<Set<Permutation>> spans, Set<Permutation> set) {
    for (Set<Permutation> span : spans) {
      if (span.equals(set)) {
        return true;
      }
    }
    return false;
  }
}
