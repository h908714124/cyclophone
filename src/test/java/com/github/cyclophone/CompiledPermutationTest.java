package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompiledPermutationTest {

  @Test
  void testUnclobber() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = ArrayUtil.range(100);
      int[] b = Arrays.copyOf(a, a.length);
      Cycles p = RandomPermutation.randomPermutation(a.length).toCycles();
      p.clobber(a);
      p.unclobber(a);
      assertArrayEquals(b, a);
    }
  }

  /* test defining property of apply */
  @Test
  void testApply() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = ArrayUtil.range(100);
      int[] b = Arrays.copyOf(a, a.length);
      Cycles p = RandomPermutation.randomPermutation(a.length - 10).toCycles();
      int[] c = p.apply(a);
      for (int i = 0; i < a.length; i += 1) {
        assertEquals(c[p.apply(i)], a[i]);
        if (i >= p.length())
          assertEquals(a[i], p.apply(a)[i]);
      }
      assertArrayEquals(b, a);
    }
  }

}
