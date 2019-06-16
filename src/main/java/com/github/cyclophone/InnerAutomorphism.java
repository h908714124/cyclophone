package com.github.cyclophone;

import java.util.Set;

final class InnerAutomorphism extends Automorphism {

  private Permutation p;

  private InnerAutomorphism(Permutation p) {
    this.p = p;
  }

  static Automorphism conjugationBy(Permutation p) {
    return new InnerAutomorphism(p);
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
