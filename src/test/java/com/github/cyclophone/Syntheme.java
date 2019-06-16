package com.github.cyclophone;

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

  private final Permutation p;

  Syntheme(int i1, int i2, int i3, int i4, int i5, int i6) {
    Permutation a = Permutation.cycle(i1, i2);
    Permutation b = Permutation.cycle(i3, i4);
    Permutation c = Permutation.cycle(i5, i6);
    this.p = a.compose(b).compose(c);
  }

  String[] render(String alphabet) {
    char[][] result = new char[2][3];
    int[][] cycles = p.toCycles().getCycles();
    for (int cycle_n = 0; cycle_n < cycles.length; cycle_n++) {
      int[] cycle = cycles[cycle_n];
      for (int i : cycle) {
        int x = i % 3;
        int y = i / 3;
        result[y][x] = alphabet.charAt(cycle_n);
      }
    }
    return new String[]
        {
            new String(result[0]),
            new String(result[1])
        };
  }
}
