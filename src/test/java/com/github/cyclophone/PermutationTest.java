package com.github.cyclophone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cyclophone.Apply.apply;
import static com.github.cyclophone.Equals.assertPermutationEquals;
import static com.github.cyclophone.Move.move;
import static com.github.cyclophone.Permutation.cycle;
import static com.github.cyclophone.Permutation.cycle0;
import static com.github.cyclophone.Permutation.define0;
import static com.github.cyclophone.Permutation.identity;
import static com.github.cyclophone.Product.product;
import static com.github.cyclophone.RandomPermutation.randomNumbers;
import static com.github.cyclophone.Reverse.reverse;
import static com.github.cyclophone.Reverse.reverses;
import static com.github.cyclophone.Shift.shift;
import static com.github.cyclophone.SymmetricGroup.symmetricGroup;
import static com.github.cyclophone.TestRankingsComparator.sortingPermutation;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Legacy tests.
 * All tests in here use the strict factory.
 */
class PermutationTest {

  /* Check example from constructor javadoc */
  @Test
  void testAbc() {
    Permutation p = define0(1, 2, 0);
    assertEquals("cab", apply(p, "abc"));
  }

  @Test
  void testComp() {
    Permutation p = Permutation.define0(1, 2, 0);
    assertPermutationEquals(define0(1, 2, 0), p);
    assertArrayEquals(new String[]{"c", "a", "b"}, apply(p, TestUtil.symbols(3)));
    assertArrayEquals(new String[]{"b", "c", "a"}, apply(p.pow(2), TestUtil.symbols(3)));
  }

  /* check defining property of composition */
  @Test
  void testComp2() {
    Permutation p = Permutation.define0(1, 2, 0);
    Permutation p2 = sortingPermutation(new int[]{4, 6, 10, -5, 195, 33, 2});
    for (int i = 0; i < p.length(); i += 1) {
      assertEquals(apply(p2, apply(p, i)), apply(p2.compose(p), i));
    }
  }

  /* check defining property of apply */
  @Test
  void testApply() {
    int[] a = randomNumbers(100, 200);
    Permutation p = RandomPermutation.randomPermutation((int) (a.length * Math.random()));
    for (int i = 0; i < a.length; i += 1) {
      assertEquals(apply(p, a)[apply(p, i)], a[i]);
      if (i >= p.length()) {
        assertEquals(a[i], apply(p, a)[i]);
      }
    }
  }

  @Test
  void testIterable() {
    for (int __ = 0; __ < 100; __++) {
      MyInt[] a = MyInt.box(randomNumbers(100, 50 + (int) (Math.random() * 100)));
      Permutation p = RandomPermutation.randomPermutation((int) (Math.random() * a.length));
      Object[] applied = apply(p, a);
      List<MyInt> arrayList = new ArrayList<>(a.length);
      List<MyInt> linkedList = new LinkedList<>();
      Collections.addAll(arrayList, a);
      Collections.addAll(linkedList, a);
      List<MyInt> arrayListApplied, linkedListApplied;
      List<MyInt> arrayListApplied2, linkedListApplied2;

      // standard
      arrayListApplied = p.apply(arrayList);
      linkedListApplied = p.apply(linkedList);
      for (int i = 0; i < a.length; i += 1) {
        assertEquals(applied[i], arrayListApplied.get(i));
        assertEquals(applied[i], linkedListApplied.get(i));
      }

      // compiled
      Cycles compiled = p.toCycles();
      arrayListApplied2 = compiled.apply(arrayList);
      linkedListApplied2 = compiled.apply(linkedList);
      assertEquals(arrayListApplied, arrayListApplied2);
      assertEquals(linkedListApplied, linkedListApplied2);
    }
  }

  /* gaps in ranking */
  @Test
  void testInvalidGap() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        define0(1, 2, 0, 5));
  }

  /* missing zero in ranking */
  @Test
  void testInvalidMissingZero() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        define0(1, 2, 3));
  }

  /* duplicates in ranking */
  @Test
  void testInvalidDuplicate() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      int[] ranking = {1, 2, 0, 2, 3};
      assertFalse(Rankings.isValid(ranking));
      define0(ranking);
    });
  }

  /* negative number in ranking */
  @Test
  void testInvalidNegative() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        define0(-1, 0, 1));
  }

  @Test
  void testInvert() {
    Permutation p = Permutation.define0(1, 2, 0);
    assertTrue(product(p.invert(), p).isIdentity());
    assertTrue(product(p, p.invert()).isIdentity());
    assertTrue(product(p, p.pow(2)).isIdentity());
    assertTrue(product(p.pow(2), p).isIdentity());
    assertTrue(product().isIdentity());
    assertTrue(p.pow(0).isIdentity());
    assertFalse(p.pow(1).isIdentity());
    assertFalse(p.pow(2).isIdentity());
    assertEquals(p.pow(0), p.pow(3));
    assertPermutationEquals(p.pow(2), product(p, p));
    assertEquals(p.pow(1), p);
    assertPermutationEquals(p.pow(-1), product(p, p));
    assertPermutationEquals(p.pow(-1), p.invert());
    assertPermutationEquals(p.pow(2), p.compose(p));
    assertArrayEquals(new String[]{"a", "b", "c"},
        apply(product(p, p.invert()), TestUtil.symbols(3)));
  }

  @Test
  void testIdentity() {
    assertTrue(Permutation.identity().isIdentity());
    assertTrue(define0(0, 1, 2, 3, 4).isIdentity());
    assertTrue(define0(0, 1, 2, 3, 4).invert().isIdentity());
  }

  /* test defining property of identity */
  @Test
  void testIdentity2() {
    Permutation identity = Permutation.identity();
    for (int i = 0; i < 10; i += 1) {
      assertEquals(i, apply(identity, i));
    }
  }

  /* Check defining property of inverse */
  @Test
  void testInverse2() {
    Permutation p = sortingPermutation(new int[]{4, 6, 10, -5, 195, 33, 2});
    for (int i = 0; i < p.length(); i += 1) {
      assertEquals(i, apply(p.invert(), apply(p, i)));
    }
  }

  @Test
  void cycleEquality() {
    assertPermutationEquals(cycle(1, 5, 3, 2), cycle(5, 3, 2, 1));
    assertPermutationEquals(cycle(1, 5, 3, 2), cycle(2, 1, 5, 3));
    assertNotEquals(cycle(1, 5, 3, 2), cycle(1, 5, 2, 3));
  }

  @Test
  void cycleApply() {
    assertArrayEquals(new String[]{"b", "c", "e", "d", "a"},
        apply(cycle(1, 5, 3, 2), TestUtil.symbols(5)));
    assertArrayEquals(new String[]{"c", "b", "e", "d", "a"},
        apply(cycle(1, 5, 3), TestUtil.symbols(5)));
    assertArrayEquals(new String[]{"c", "a", "b"},
        apply(cycle(1, 2, 3), TestUtil.symbols(3)));
  }

  @Test
  void testCycleApply() {
    assertArrayEquals(new String[]{"c", "a", "b"},
        apply(product(cycle(1, 2), cycle(2, 3)), TestUtil.symbols(3)));
    assertArrayEquals(new String[]{"c", "a", "b"},
        apply(cycle(1, 2, 3), TestUtil.symbols(3)));
    assertArrayEquals(new String[]{"a", "c", "b"},
        apply(product(cycle(1, 2),
            product(cycle(1, 2), cycle(2, 3))), TestUtil.symbols(3)));
  }

  @Test
  void testCycleEquals() {
    assertTrue(product(cycle(1, 2), cycle(2, 1)).isIdentity());
    assertPermutationEquals(cycle(2, 3), product(cycle(1, 2),
        product(cycle(1, 2), cycle(2, 3))));
  }

  @Test
  void testCycleLaw() {
    Permutation longest = cycle(2, 4, 1, 11, 3);
    assertPermutationEquals(product(cycle(2, 4),
        cycle(4, 1, 11, 3)), longest);
  }

  @Test
  void testSort() {
    int[] x = new int[]{4, 6, 10, -5, 195, 33, 2};
    int[] y = Arrays.copyOf(x, x.length);
    Arrays.sort(y);
    Permutation p = sortingPermutation(x);
    for (int i = 0; i < x.length; i += 1) {
      assertEquals(x[i], y[apply(p, i)]);
    }
    assertArrayEquals(y, apply(p, x));
  }

  int indexOf(int[] x, int el) {
    for (int i = 0; i < x.length; i += 1) {
      if (x[i] == el) {
        return i;
      }
    }
    throw new IllegalArgumentException("not in x: " + el);
  }

  int indexOf(MyInt[] x, MyInt el) {
    for (int i = 0; i < x.length; i += 1) {
      if (x[i].n == el.n) {
        return i;
      }
    }
    throw new IllegalArgumentException("not in x: " + el);
  }

  /* check example from README */
  @Test
  void testSortInvert() {
    int[] x = new int[]{4, 6, 10, -5, 195, 33, 2};
    Permutation unsort = sortingPermutation(x).invert();
    int[] y = Arrays.copyOf(x, x.length);
    Arrays.sort(y);
    for (int k = 0; k < y.length; k += 1) {
      assertEquals(x[indexOf(x, y[k])], y[k]);
      assertEquals(indexOf(x, y[k]), apply(unsort, k));
    }
  }

  /* check defining property of sorting */
  @Test
  void testSortRandom() {
    int size = (int) (100 * Math.random());
    int[] distinct = RandomPermutation.randomRanking(size);
    int[] sorted = Arrays.copyOf(distinct, distinct.length);
    Arrays.sort(sorted);
    Permutation p = sortingPermutation(distinct);
    for (int i = 0; i < sorted.length; i += 1) {
      distinct[i] = sorted[apply(p, i)];
    }
  }

  @Test
  void testSortInvertComparator() {
    MyInt[] x = MyInt.box(new int[]{4, 6, 10, -5, 195, 33, 2});
    Permutation unsort = Sorting.sorting(x).using(MyInt.COMP).invert();
    MyInt[] y = Arrays.copyOf(x, x.length);
    Arrays.sort(y, MyInt.COMP);
    for (int k = 0; k < y.length; k += 1) {
      assertEquals(x[indexOf(x, y[k])], y[k]);
      assertEquals(indexOf(x, y[k]), apply(unsort, k));
    }
  }

  /* negative index */
  @Test
  void testApplyInvalid() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        apply(Permutation.identity(), -1));
  }

  /**
   * @param a Any array of integers
   * @return A sorted copy of {@code a}
   */
  private int[] classicSort(int[] a) {
    int[] result = Arrays.copyOf(a, a.length);
    Arrays.sort(result);
    return result;
  }

  /* Another way of checking that duplicateRejectingFactory().sorting(a).apply(a) sorts a, for distinct array a */
  @Test
  void testSort1024() {
    int[] a = RandomPermutation.randomRanking(1024);
    assertArrayEquals(classicSort(a), apply(sortingPermutation(a), a));
  }

  @Test
  void testCycleLength() {
    Permutation swap01 = Transposition.swap(0, 1).toPermutation();
    assertEquals(2, swap01.length());
  }

  @Test
  void testFromQuickly() {
    Permutation p = Taking.taking(new int[]{1, 2, 3}).to(new int[]{2, 3, 1});
    assertArrayEquals(new String[]{"b", "c", "a"}, apply(p, TestUtil.symbols(3)));
  }

  /* check defining property of from */
  private void testFromQuickly2() {
    int size = 2048;
    int[] a = RandomPermutation.randomRanking(size);
    Permutation random;
    do {
      random = RandomPermutation.randomPermutation((int) (Math.random() * size));
    } while (random.isIdentity());
    int[] b = apply(random, a);
    assertFalse(Arrays.equals(a, b));
    assertArrayEquals(apply(Taking.taking(a).to(b), a), b);
  }

  /* check defining property of from again, on non comparable objects, possibly with null */
  @Test
  void testFromALot() {
    for (int i = 0; i < 100; i += 1) {
      testFromQuickly2();
    }
  }

  @Test
  void testMove() {
    assertEquals(identity(), move(5, 5));
    assertEquals("213", apply(move(0, 1), "123"));
    assertEquals("23145", apply(move(0, 2), "12345"));
    assertEquals("14235", apply(move(3, 1), "12345"));
  }

  /* various assertions about Sym(5) */
  @Test
  void testCyclesAndTranspositions() {
    int sign = 0;
    for (Permutation p : symmetricGroup(5).collect(Collectors.toList())) {
      int order = p.order();
      sign += p.toCycles().signature();
      Cycles cycles = p.toCycles();
      assertPermutationEquals(p, p.toCycles().toPermutation());
      if (reverses(p, 5)) {
        assertEquals(2, order);
        assertEquals(1, p.toCycles().signature());
      }
      if (order > 5) {
        assertEquals(6, order);
        assertEquals(2, cycles.numCycles());
      } else if (order == 5) {
        assertEquals(1, cycles.numCycles());
      } else if (order == 4) {
        assertEquals(1, cycles.numCycles());
      } else if (order == 3) {
        assertEquals(1, cycles.numCycles());
      } else if (order == 2) {
        assertTrue(cycles.numCycles() <= 2);
      } else {
        assertTrue(p.isIdentity());
      }
    }
    assertEquals(0, sign);
  }

  /* check edge cases */
  @Test
  void testZero() {
    Permutation p = identity();
    assertEquals(define0(), p);
    assertEquals(p, cycle0());
    assertEquals(0, p.length());
    assertArrayEquals(new int[0], apply(p, new int[0]));
    assertEquals(0, p.toCycles().numCycles());
    assertEquals(identity(), cycle0(0));
    assertEquals(identity(), cycle0(1));
    assertEquals(identity(), cycle0(2));
  }

  /* example from README */
  @Test
  void testPprod() {
    Permutation c0 = cycle0(7, 9);
    Permutation c1 = cycle0(1, 4, 8, 10, 3, 6, 11);
    Permutation c2 = cycle0(0, 2, 5);
    assertEquals("Hello world!", apply(product(c0, c1, c2).invert(), " !Hdellloorw"));
    assertEquals("Hello world!", apply(product(Arrays.asList(c0, c1, c2)).invert(), " !Hdellloorw"));
  }

  /* making sure sorting does what we think it does */
  @Test
  void testDegenerate() {
    int[] a = new int[]{3, 3, 3, 3, 3, 3, 3};
    assertFalse(sortingPermutation(a).isIdentity());
  }

  @Test
  void testShift() {
    assertEquals("abccba", apply(shift(reverse(3), 3), "abcabc"));
  }

  @Test
  void testShift2() {
    for (int __ = 0; __ < 100; __++) {
      Permutation p = RandomPermutation.randomPermutation(40);
      for (int n = 0; n < 100; n++) {
        for (int j = 0; j < 100; j++) {
          if (j < n) {
            assertEquals(j, apply(shift(p, n), j));
          } else {
            assertEquals(apply(shift(p, n), j), n + apply(p, j - n));
          }
        }
      }
    }
  }

  @Test
  void testDestructive() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = randomNumbers(10, 5);
      int[] copy = Arrays.copyOf(a, a.length);
      List<Integer> listCopy = Arrays.asList(ArrayUtil.box(Arrays.copyOf(a, a.length)));
      Permutation p = RandomPermutation.randomPermutation(5);
      Cycles d = p.toCycles();
      d.clobber(copy);
      d.clobber(listCopy);
      int[] expected = apply(p, a);
      assertArrayEquals(expected, copy);
      assertArrayEquals(ArrayUtil.box(expected), listCopy.toArray(new Integer[0]));
      assertPermutationEquals(p, d.toPermutation());
    }
  }

  @Test
  void testNonDestructive() {
    int[] a = {0, 1, 2, 3, 4};
    Permutation p = define0(1, 2, 0, 3, 4).compose(define0(0, 1, 2, 4, 3));
    Cycles d = p.toCycles();
    assertArrayEquals(apply(p, a), d.apply(a));
  }


  @Test
  void testDestructive3() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = randomNumbers(100, 100);
      int[] copy = Arrays.copyOf(a, a.length);
      List<Integer> listCopy = Arrays.asList(ArrayUtil.box(Arrays.copyOf(a, a.length)));
      Permutation p = RandomPermutation.randomPermutation(100);
      Cycles d = p.toCycles();
      d.clobber(copy);
      d.clobber(listCopy);
      int[] expected = apply(p, a);
      assertArrayEquals(expected, copy);
      assertArrayEquals(ArrayUtil.box(expected), listCopy.toArray(new Integer[0]));
      assertPermutationEquals(p, d.toPermutation());
    }
  }

  @Test
  void testSorts() {
    for (int __ = 0; __ < 100; __++) {
      int[] a = randomNumbers(100, 50 + (int) (Math.random() * 100));
      Permutation p = sortingPermutation(a);
      assertTrue(Sorting.sorts(p, a));
    }
  }

  @Test
  void testCycle4() {
    Permutation permutation = cycle(1, 3).compose(cycle(2, 4)).compose(cycle(1, 2));
    assertEquals("(1 4 2 3)", permutation.toString());
  }

  @Test
  void testSymmetricGroup1() {
    int n = 1;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(1, count);
  }

  @Test
  void testSymmetricGroup2() {
    int n = 2;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(2, count);
  }

  @Test
  void testSymmetricGroup3() {
    int n = 3;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(6, count);
  }

  @Test
  void testSymmetricGroup4() {
    int n = 4;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(24, count);
  }

  @Test
  void testSymmetricGroup5() {
    int n = 5;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(120, count);
  }

  @Test
  void testSymmetricGroup6() {
    int n = 6;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(720, count);
  }

  @Test
  void testSymmetricGroup7() {
    int n = 7;
    List<Permutation> sym = symmetricGroup(n).collect(Collectors.toList());
    long count = (long) sym.size();
    assertEquals(count, sym.stream().distinct().count());
    assertTrue(sym.stream().allMatch(p -> p.length() <= n));
    assertEquals(5040, count);
  }

  @Test
  void testSymmetricGroup8() {
    int n = 8;
    assertTrue(symmetricGroup(n).allMatch(p -> p.length() <= n));
    assertEquals(40320, symmetricGroup(n).count());
  }

  @Test
  void testSymmetricGroup9() {
    int n = 9;
    assertTrue(symmetricGroup(n).allMatch(p -> p.length() <= n));
    assertEquals(362880, symmetricGroup(n).count());
  }
}

