package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BinaryOperator;

/*
 *
 *            |         |         |         |
 *            |         |         |         |
 *       -#   |    -#   |    --   |    -    |     -
 *      - #   |   #-    |    ##   |   #-#   |   #-#
 *            |         |         |         |
 *            |         |         |         |
 *       -#   |    --   |    -#   |    -    |     -
 *      # -   |   ##    |    #-   |   -##   |   ##-
 *            |         |         |         |
 *            |         |         |         |
 *       --   |    -#   |    -#   |    -    |     -
 *      # #   |   -#    |    -#   |   ##-   |   -##
 *            |         |         |         |
 *
 */
class SynthemeTest {

  @Test
  void testPrintSynthemes() {
    String[] rows = Arrays.stream(Syntheme.values())
        .map(syntheme -> syntheme.render(" -#"))
        .map(a -> new String[]
            {
                "   ",
                a[0],
                a[1],
                "   "
            })
        .reduce(new String[4],
            ArrayOperator.create(this::join));
    for (String row : rows) {
      System.out.println(row);
    }
  }

  private String join(String a, String b) {
    if (Objects.toString(a, "").isEmpty()) {
      return b;
    }
    if (Objects.toString(b, "").isEmpty()) {
      return a;
    }
    return a + "   |   " + b;
  }

  private static class ArrayOperator implements BinaryOperator<String[]> {

    BinaryOperator<String> function;

    ArrayOperator(BinaryOperator<String> function) {
      this.function = function;
    }

    static BinaryOperator<String[]> create(BinaryOperator<String> function) {
      return new ArrayOperator(function);
    }

    @Override
    public String[] apply(String[] ts, String[] us) {
      String[] result = new String[ts.length];
      for (int i = 0; i < ts.length; i++) {
        result[i] = function.apply(ts[i], us[i]);
      }
      return result;
    }
  }
}
