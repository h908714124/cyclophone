package com.github.cyclophone;

import java.util.Arrays;
import java.util.TreeSet;

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

  private final TreeSet<Integer> first;
  private final TreeSet<Integer> second;
  private final TreeSet<Integer> third;

  Syntheme(int i1, int i2, int i3, int i4, int i5, int i6) {
    this.first = new TreeSet<>(Arrays.asList(i1 - 1, i2 - 1));
    this.second = new TreeSet<>(Arrays.asList(i3 - 1, i4 - 1));
    this.third = new TreeSet<>(Arrays.asList(i5 - 1, i6 - 1));
  }

  String[] render(String alphabet) {
    char[][] result = new char[2][3];
    char alpha = alphabet.charAt(0);
    char beta = alphabet.charAt(1);
    char gamma = alphabet.charAt(2);
    for (int i : first) {
      int x = i % 3;
      int y = i / 3;
      result[y][x] = alpha;
    }
    for (int i : second) {
      int x = i % 3;
      int y = i / 3;
      result[y][x] = beta;
    }
    for (int i : third) {
      int x = i % 3;
      int y = i / 3;
      result[y][x] = gamma;
    }
    return new String[]
        {
            new String(result[0]),
            new String(result[1])
        };
  }
}
