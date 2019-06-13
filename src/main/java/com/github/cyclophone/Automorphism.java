package com.github.cyclophone;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

abstract class Automorphism implements Function<Permutation, Permutation>, Comparable<Automorphism> {

  private TreeMap<Permutation, Permutation> mapping;

  Automorphism() {
  }

  private TreeMap<Permutation, Permutation> getMapping() {
    if (mapping == null) {
      mapping = new TreeMap<>();
      for (Permutation p : getUniverse()) {
        mapping.put(p, apply(p));
      }
    }
    return mapping;
  }

  abstract Set<Permutation> getUniverse();

  Automorphism compose(Automorphism other) {
    Set<Permutation> universe = getUniverse();
    return new Automorphism() {
      @Override
      Set<Permutation> getUniverse() {
        return universe;
      }

      @Override
      public Permutation apply(Permutation p) {
        return Automorphism.this.apply(other.apply(p));
      }
    };
  }

  @Override
  public int compareTo(Automorphism other) {
    TreeMap<Permutation, Permutation> thisMapping = getMapping();
    TreeMap<Permutation, Permutation> otherMapping = other.getMapping();
    if (thisMapping.size() != otherMapping.size()) {
      throw new IllegalArgumentException();
    }
    for (Map.Entry<Permutation, Permutation> entry : thisMapping.entrySet()) {
      Permutation k = entry.getKey();
      Permutation v1 = entry.getValue();
      Permutation v2 = otherMapping.get(k);
      if (v2 == null) {
        throw new IllegalArgumentException();
      }
      int i = v1.compareTo(v2);
      if (i != 0) {
        return i;
      }
    }
    return 0;
  }
}
