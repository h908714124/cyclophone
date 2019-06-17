package com.github.cyclophone;

import java.util.Set;

final class InnerAutomorphism extends Automorphism {

  private Permutation p;
  private Permutation p_;

  private InnerAutomorphism(Permutation p) {
    this.p = p;
    this.p_ = p.invert();
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
    return p_.compose(h).compose(p);
  }
}
