package com.github.cyclophone;

import java.util.Set;

final class ConjugationAutomorphism extends Automorphism {

  private Permutation p;

  private ConjugationAutomorphism(Permutation p) {
    this.p = p;
  }

  static Automorphism conjugationBy(Permutation p) {
    return new ConjugationAutomorphism(p);
  }

  @Override
  Set<Permutation> getUniverse() {
    return OuterAutomorphism.getInstance().getUniverse();
  }

  @Override
  public Permutation apply(Permutation h) {
    return p.invert().compose(h).compose(p);
  }
}
