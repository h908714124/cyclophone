package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.cyclophone.Apply.apply;
import static com.github.cyclophone.Equals.assertPermutationEquals;
import static com.github.cyclophone.RandomPermutation.randomNumbers;
import static com.github.cyclophone.Sorting.isSorted;
import static com.github.cyclophone.Sorting.sortedCopy;
import static com.github.cyclophone.Sorting.sorting;
import static com.github.cyclophone.TestRankingsComparator.sortingPermutation;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRankings {

  @Test
  void testSortRandom() {
    for (int __ = 0; __ < 100; __ += 1) {
      int[] a = randomNumbers(100, 200);
      assertArrayEquals(sortedCopy(a), apply(sortingPermutation(a), a));
    }
    for (int __ = 0; __ < 100; __ += 1) {
      int[] a = randomNumbers(100, 20);
      assertArrayEquals(sortedCopy(a), apply(sortingPermutation(a), a));
    }
  }

  @Test
  void testSortStrict() {
    for (int __ = 0; __ < 100; __ += 1) {
      String[] a = TestUtil.symbols(100);
      String[] shuffled = apply(RandomPermutation.randomPermutation(a.length), a);
      assertArrayEquals(sortedCopy(a), apply(sortingPermutation(shuffled), shuffled));
    }
  }

  @Test
  void testFromRandom() {
    for (int __ = 0; __ < 100; __ += 1) {
      int[] a = randomNumbers(100, 200);
      int[] b = apply(RandomPermutation.randomPermutation(a.length), a);
      assertArrayEquals(b, apply(Taking.taking(a).to(b), a));
    }
    for (int __ = 0; __ < 100; __ += 1) {
      int[] a = randomNumbers(100, 20);
      int[] b = apply(RandomPermutation.randomPermutation(a.length), a);
      assertArrayEquals(b, apply(Taking.taking(a).to(b), a));
    }
  }

  @Test
  void testFromStrict() {
    for (int __ = 0; __ < 100; __ += 1) {
      String[] a = TestUtil.symbols(100);
      String[] shuffled = apply(RandomPermutation.randomPermutation(a.length), a);
      assertArrayEquals(a, apply(Taking.taking(shuffled).to(a), shuffled));
    }
  }


  @Test
  void testMismatch() {
    for (int __ = 0; __ < 1000; __ += 1) {
      int[] a = randomNumbers(100, 110);
      int[] b = Taking.apply(RandomPermutation.randomRanking(a.length), a);

      int[] bdupes = TestUtil.duplicateIndexes(b);
      int[] adupes = TestUtil.duplicateIndexes(a);

      int changed = -1;
      // subtly mess things up by changing b,
      // so that all elements in a can still be found in b,
      // but b is not a reordering of a anymore
      if (Math.random() < 0.5) {
        for (int j = 0; j < b.length; j += 1) {
          if (b[bdupes[0]] != b[j]) {
            b[bdupes[0]] = b[j];
            changed = b[j];
            break;
          }
        }
      } else {
        for (int j = 0; j < a.length; j += 1) {
          if (a[adupes[0]] != a[j]) {
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
      assertNull(Taking.from(a, b));
    }
  }

  @Test
  void testSort() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = randomNumbers(100, (int) (Math.random() * 1000));
      int[] sort = sorting(a);
      int[] sorted = Taking.apply(sort, a);
      int[] unsort = Rankings.invert(sort);
      int[] hopefullyIdentity = Rankings.comp(sort, unsort);
      assertTrue(isSorted(hopefullyIdentity));
      assertTrue(isSorted(sorted));
      for (int el : a) {
        assertEquals(ArrayUtil.indexOf(a, el, 0), unsort[Arrays.binarySearch(sorted, el)]);
      }
    }
  }

  @Test
  void testNextOffset() {
    int[] sorted = {0, 0, 1, 3, 3, 3, 4, 4};
    Assertions.assertEquals(1, Rankings.nextOffset(0, 0, sorted));
    Assertions.assertEquals(-1, Rankings.nextOffset(1, 0, sorted));
    Assertions.assertEquals(1, Rankings.nextOffset(3, 0, sorted));
    Assertions.assertEquals(2, Rankings.nextOffset(3, 1, sorted));
    Assertions.assertEquals(1, Rankings.nextOffset(4, 0, sorted));
    Assertions.assertEquals(-1, Rankings.nextOffset(4, 1, sorted));
    Assertions.assertEquals(-1, Rankings.nextOffset(5, 0, sorted));
    Assertions.assertEquals(-2, Rankings.nextOffset(5, -1, sorted));
    Assertions.assertEquals(1, Rankings.nextOffset(6, 0, sorted));
    Assertions.assertEquals(-1, Rankings.nextOffset(7, 0, sorted));
  }

  @Test
  void testDecompose() {
    for (int __ = 0; __ < 100; __++) {
      Permutation p = RandomPermutation.randomPermutation(100);
      assertPermutationEquals(p, p.toCycles().toPermutation());
    }
  }

  @Test
  void testSorts() {
    int[] ranking = {0, 3, 1, 4, 2};
    int[] a = {0, 4, 2, 4, 3};
    assertTrue(isSorted(Taking.apply(ranking, a)));
    assertTrue(Rankings.sorts(ranking, a));
  }

  @Test
  void testSorts2() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = randomNumbers(100, 100 + (int) (100 * (Math.random() - 0.8)));
      int[] ranking = sorting(a);
      assertTrue(isSorted(Taking.apply(ranking, a)));
      assertTrue(Rankings.sorts(ranking, a));
    }
  }
}
