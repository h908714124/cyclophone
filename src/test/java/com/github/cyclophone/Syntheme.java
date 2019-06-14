package com.github.cyclophone;

import java.util.HashSet;
import java.util.Set;

enum Syntheme {

  S_1(1, 2, 3, 5, 4, 6),
  S_2(1, 6, 2, 5, 3, 4),
  S_3(1, 4, 2, 3, 5, 6),
  S_4(1, 3, 2, 6, 4, 5),
  S_5(1, 5, 2, 4, 3, 6),
  S_6(1, 3, 2, 4, 5, 6),
  S_7(1, 5, 2, 6, 3, 4),
  S_8(1, 4, 2, 5, 3, 6),
  S_9(1, 6, 2, 3, 4, 5),
  S_10(1, 2, 3, 6, 4, 5),
  S_11(1, 5, 2, 3, 4, 6),
  S_12(1, 4, 2, 6, 3, 5),
  S_13(1, 6, 2, 4, 3, 5),
  S_14(1, 3, 2, 5, 4, 6),
  S_15(1, 2, 3, 4, 5, 6);

  private final Set<Integer> first;
  private final Set<Integer> second;
  private final Set<Integer> third;

  Syntheme(int i1, int i2, int i3, int i4, int i5, int i6) {
    this.first = new HashSet<>(i1, i2);
    this.second = new HashSet<>(i3, i4);
    this.third = new HashSet<>(i5, i6);
  }

  Set<Integer> getFirst() {
    return first;
  }

  Set<Integer> getSecond() {
    return second;
  }

  Set<Integer> getThird() {
    return third;
  }
}
