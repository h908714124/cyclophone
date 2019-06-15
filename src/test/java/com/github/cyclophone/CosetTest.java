package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.cyclophone.Subgroup.isSubgroup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CosetTest {

  @Test
  void testSubgroup() {
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
    System.out.println(synthemes1);
    System.out.println(synthemes2);
    System.out.println(synthemes3);
    System.out.println(synthemes4);
    System.out.println(synthemes5);
    System.out.println(synthemes6);
  }
}