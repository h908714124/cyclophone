package com.github.cyclophone;

import java.util.Set;

final class ConjugationAutomorphism extends Automorphism {

  private Permutation p;

  ConjugationAutomorphism(Permutation p) {
    this.p = p;
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
