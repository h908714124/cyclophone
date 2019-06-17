package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.github.cyclophone.Apply.apply;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/* like PermutationFactoryTest, but use the Comparator versions of sorting and from */
class TestRankingsComparator {

  private static final int REPEAT = 1000;

  static MyInt[] randomMyInts(int length) {
    return MyInt.box(ArrayUtil.randomNumbers(100, length));
  }

  @Test
  void testSortRandom() {
    MyInt[] a;
    for (int __ = 0; __ < REPEAT; __ += 1) {
      a = MyInt.box(ArrayUtil.randomNumbers(100, 200));
      assertArrayEquals(ArrayUtil.sortedCopy(a, MyInt.COMP), apply(Sorting.sorting(a).using(MyInt.COMP), a));
    }
    for (int i = 0; i < REPEAT; i += 1) {
      a = MyInt.box(ArrayUtil.randomNumbers(100, 200));
      assertArrayEquals(ArrayUtil.sortedCopy(a, MyInt.COMP), apply(Sorting.sorting(a).using(MyInt.COMP), a));
    }
  }

  @Test
  void testSortStrict() {
    for (int __ = 0; __ < REPEAT; __ += 1) {
      String[] a = TestUtil.symbols(100);
      String[] shuffled = apply(RandomPermutation.random(a.length), a);
      assertArrayEquals(ArrayUtil.sortedCopy(a), apply(Sorting.sorting(shuffled), shuffled));
    }
  }

  @Test
  void testFromRandom() {
    for (int __ = 0; __ < REPEAT; __ += 1) {
      int[] a = ArrayUtil.randomNumbers(100, 200);
      MyInt[] b = apply(RandomPermutation.random(a.length), MyInt.box(a));
      Assertions.assertArrayEquals(b, apply(Taking.taking(MyInt.box(a)).to(b).using(MyInt.COMP), MyInt.box(a)));
    }
    for (int i = 0; i < REPEAT; i += 1) {
      MyInt[] a = randomMyInts(20);
      MyInt[] b = apply(RandomPermutation.random(a.length), a);
      assertArrayEquals(b, apply(Taking.taking(a).to(b).using(MyInt.COMP), a));
    }
  }

  @Test
  void testFromStrict() {
    for (int __ = 0; __ < REPEAT; __ += 1) {
      String[] a = TestUtil.symbols(100);
      String[] shuffled = apply(RandomPermutation.random(a.length), a);
      assertArrayEquals(a, apply(Taking.taking(shuffled).to(a), shuffled));
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  void testMismatch() {

    for (int __ = 0; __ < REPEAT; __ += 1) {
      MyInt[] a = randomMyInts(110);
      Object[] b = apply(RandomPermutation.random(a.length), a);

      int[] bdupes = TestUtil.duplicateIndexes(b, MyInt.COMP);
      int[] adupes = TestUtil.duplicateIndexes(a, MyInt.COMP);

      MyInt changed = null;
      // subtly mess things up by changing b,
      // so that all elements in a can still be found in b,
      // but b is not a reordering of a anymore
      if (Math.random() < 0.5) {
        for (int j = 0; j < b.length; j += 1) {
          if (!b[bdupes[0]].equals(b[j])) {
            b[bdupes[0]] = b[j];
            changed = (MyInt) b[j];
            break;
          }
        }
      } else {
        for (int j = 0; j < a.length; j += 1) {
          if (!a[adupes[0]].equals(a[j])) {
            a[adupes[0]] = a[j];
            changed = a[j];
            break;
          }
        }
      }
      int bc = TestUtil.count(b, changed);
      int ac = TestUtil.count(a, changed);
      assertNotEquals(bc, ac);
      assertTrue(ac > 0);
      assertTrue(bc > 0);

      // null because b is not a rearrangement of a
      assertNull(Rankings.from(a, b, (Comparator) MyInt.COMP));
    }
  }
}
