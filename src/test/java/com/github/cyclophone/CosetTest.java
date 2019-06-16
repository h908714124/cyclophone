package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.github.cyclophone.ConjugationAutomorphism.conjugationBy;
import static com.github.cyclophone.Permutation.cycle;
import static com.github.cyclophone.Subgroup.isNormal;
import static com.github.cyclophone.Subgroup.isSubgroup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CosetTest {

  @Test
  void testSynthemes() {
    assertTrue(isSubgroup(Coset.COSET1.set()));
    List<Permutation> synthemes1 = Coset.COSET1.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    List<Permutation> synthemes2 = Coset.COSET2.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    List<Permutation> synthemes3 = Coset.COSET3.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    List<Permutation> synthemes4 = Coset.COSET4.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    List<Permutation> synthemes5 = Coset.COSET5.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    List<Permutation> synthemes6 = Coset.COSET6.set().stream()
        .filter(p -> p.toCycles().numCycles() == 3)
        .collect(Collectors.toList());
    assertEquals(10, synthemes1.size());
    assertEquals(1, synthemes2.size());
    assertEquals(1, synthemes3.size());
    assertEquals(1, synthemes4.size());
    assertEquals(1, synthemes5.size());
    assertEquals(1, synthemes6.size());
  }

  @Test
  void testNormal() {
    assertFalse(isNormal(6, Coset.COSET1.set()));
    assertTrue(isNormal(4, new TreeSet<>(Arrays.asList(
        cycle(1, 2).compose(cycle(3, 4)),
        cycle(1, 3).compose(cycle(2, 4)),
        cycle(1, 4).compose(cycle(2, 3))))));
    assertFalse(isNormal(4, new TreeSet<>(Arrays.asList(
        cycle(1, 2).compose(cycle(3, 4)),
        cycle(1, 2),
        cycle(3, 4)))));
  }

  @Test
  void testApplyOuter() {
    HashSet<TreeSet<Permutation>> groups = new HashSet<>();
    for (Permutation p : SymmetricGroup.symmetricGroup(6).collect(Collectors.toList())) {
      OuterAutomorphism m = OuterAutomorphism.getInstance();
      TreeSet<Permutation> set = new TreeSet<>();
      SymmetricGroup.symmetricGroup(5)
          .map(m.compose(conjugationBy(p)))
          .forEach(set::add);
      groups.add(set);
    }
    assertEquals(6, groups.size());
  }
}