package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

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
            (a, b) -> new String[]
                {
                    join(a[0], b[0]),
                    join(a[1], b[1]),
                    join(a[2], b[2]),
                    join(a[3], b[3])
                });
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
    return a + "  |  " + b;
  }
}
