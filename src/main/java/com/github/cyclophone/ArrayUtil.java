package com.github.cyclophone;

/**
 * A collection of array related utilities
 */
final class ArrayUtil {

  private static final Integer[] BOX_INT_0 = new Integer[0];
  static final int[] INT_0 = new int[]{};

  /**
   * Creates an array of [element, index] pairs from given array.
   * @param a an array of length {@code n}
   * @return a two dimensional array of length {@code n} which contains the  [element, index] pairs in {@code a}
   */
  static int[][] withIndex(int[] a) {
    int[][] result = new int[a.length][];
    for (int i = 0; i < a.length; i += 1)
      result[i] = new int[]{a[i], i};
    return result;
  }

  /**
   * Creates an array of the numbers {@code start} to {@code end} in sequence.
   * If {@code start == end}, an empty array is returned. If {@code end} is negative, the range
   * will be descending.
   * @param start a number
   * @param end a number
   * @param inclusive whether or not {@code end} should be included in the result
   * @return the sequence from {@code start} to {@code end}
   */
  static int[] range(int start, int end, boolean inclusive) {
    if (!inclusive) {
      return range(start, end);
    }
    return range(start, end >= start ? ++end : --end);
  }


  /**
   * Creates an array of the numbers {@code 0} (included) to {@code end} (excluded) in sequence.
   * If {@code end == 0} an empty array is returned. If {@code end} is negative, the range
   * will be descending.
   * @param end a number
   * @return an array of length {@code | end | }
   */
  static int[] range(int end) {
    return range(0, end);
  }

  /**
   * Creates an array of the numbers {@code 0} (included) to {@code end} (excluded) in sequence.
   * If {@code start == end}, an empty array is returned. If {@code end} is negative, the range
   * will be descending.
   * @param start a non-negative number
   * @param end a non-negative number
   * @return an array of length {@code | start - end | }
   * @throws java.lang.IllegalArgumentException if {@code end} is negative
   */
  static int[] range(int start, int end) {
    if (start == end) {
      return INT_0;
    }
    int[] result = new int[Math.abs(start - end)];
    if (start < end) {
      for (int i = 0; i < result.length; i++)
        result[i] = start++;
    } else {
      for (int i = 0; i < result.length; i++)
        result[i] = start--;
    }
    return result;
  }

  /**
   * Find element in array by comparing each element in sequence, starting at index {@code 0}.
   * @param a an array
   * @param el a number
   * @param skip number of matches to skip; if {@code skip = 0} the index of the first match, if any, will be returned
   * @return the least non-negative number {@code i} so that {@code a[i] = el}, or {@code -1} if {@code el} is not
   * found in {@code a}, or if all occurences are skipped
   * @throws java.lang.IllegalArgumentException if {@code skip < 0}
   */
  static int indexOf(int[] a, int el, final int skip) {
    if (skip < 0) {
      negativeFailure();
    }
    int cnt = 0;
    for (int i = 0; i < a.length; i += 1)
      if (a[i] == el) {
        if (cnt++ >= skip) {
          return i;
        }
      }
    return -1;
  }

  static int indexOf(int[] a, int el) {
    return indexOf(a, el, 0);
  }

  /**
   * Add a fixed number to each element of given array.
   * @param a an array of numbers
   * @param k a number
   * @return the array {@code b} defined as {@code b[i] = a[i] + k}
   */
  static int[] add(int[] a, int k) {
    int[] result = new int[a.length];
    for (int i = 0; i < a.length; i += 1) {
      result[i] = a[i] + k;
    }
    return result;
  }

  /**
   * Calculate the maximum of the numbers in the input.
   * @param a an array
   * @return the maximum of all numbers in {@code a}
   */
  static int max(int[] a) {
    if (a.length == 0) {
      throw new IllegalArgumentException("argument must not be empty");
    }
    int maxIndex = a[0];
    for (int index : a)
      maxIndex = Math.max(maxIndex, index);
    return maxIndex;
  }


  /* ================= box ================= */

  /**
   * Box every value in the input. Return an array of boxed values.
   * @param a an array of primitives
   * @return an array of boxed primitives
   */
  static Integer[] box(int[] a) {
    if (a.length == 0) {
      return BOX_INT_0;
    }
    Integer[] result = new Integer[a.length];
    for (int i = 0; i < a.length; i += 1)
      result[i] = a[i];
    return result;
  }

  static void lengthFailure() {
    throw new IllegalArgumentException("length mismatch");
  }

  static void checkLength(int rankingLength, int inputLength) {
    if (inputLength < rankingLength) {
      throw new IllegalArgumentException("not enough input: minimum input length is " + rankingLength
          + ", but input length is " + inputLength);
    }
  }

  static void negativeFailure() {
    throw new IllegalArgumentException("negative number not allowed");
  }

  static void duplicateFailure() {
    throw new IllegalArgumentException("repeated values are not allowed");
  }

  static void checkEqualLength(int[] a, int[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(byte[] a, byte[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(short[] a, short[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(float[] a, float[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(double[] a, double[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(long[] a, long[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  static void checkEqualLength(Object[] a, Object[] b) {
    if (a.length != b.length) {
      lengthFailure();
    }
  }

  /**
   * Remove an element at index {@code i}.
   * @param a an array
   * @param i cut point, must be non negative and less than {@code a.length}
   * @return an array of length {@code a.length - 1}
   */
  static int[] cut(int[] a, int i) {
    if (i < 0 || i >= a.length) {
      throw new IllegalArgumentException("i must be non netative and less than " + a.length);
    }
    int[] result = new int[a.length - 1];
    System.arraycopy(a, 0, result, 0, i);
    System.arraycopy(a, i + 1, result, i, a.length - i - 1);
    return result;
  }

  /**
   * Insert an element at index {@code i}.
   * @param a an array
   * @param i insertion point, must be non negative and not greater than {@code a.length}
   * @param el new element to be inserted
   * @return an array of length {@code a.length + 1}, this will have {@code el} at position {@code i}
   */
  static int[] paste(int[] a, int i, int el) {
    if (i < 0 || i > a.length) {
      throw new IllegalArgumentException("i must be non negative and not greater than " + a.length);
    }
    int[] result = new int[a.length + 1];
    System.arraycopy(a, 0, result, 0, i);
    result[i] = el;
    System.arraycopy(a, i, result, i + 1, a.length - i);
    return result;
  }

}
