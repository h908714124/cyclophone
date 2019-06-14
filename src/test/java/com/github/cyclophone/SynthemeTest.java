package com.github.cyclophone;

import org.junit.jupiter.api.Test;

/*
 *
 *    @@+    @+=    @++    @+@    @+=
 *    =+=    =+@    @==    ==+    +@=
 *
 *
 *    @+@    @+=    @+=    @++    @@+
 *    +==    =@+    @+=    ==@    ==+
 *
 *
 *    @++    @+=    @+=    @+@    @@+
 *    =@=    @=+    +=@    =+=    +==
 *
 */
class SynthemeTest {

  @Test
  void testPrintSynthemes() {
    for (Syntheme syntheme : Syntheme.values()) {
      printSyntheme(syntheme);
      System.out.println();
    }
  }

  private void printSyntheme(Syntheme syntheme) {
    char[] chars = syntheme.render();
    for (int i = 0; i < 3; i++) {
      System.out.print("" + chars[i]);
    }
    System.out.println();
    for (int i = 3; i < 6; i++) {
      System.out.print("" + chars[i]);
    }
    System.out.println();
  }
}