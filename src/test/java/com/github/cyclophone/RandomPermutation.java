package com.github.cyclophone;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.github.cyclophone.ArrayUtil.range;
import static com.github.cyclophone.Permutation.define;

class RandomPermutation {

  /**
   * Creates a random permutation of given length.
   *
   * @param length the length of arrays that the result can be applied to
   * @return a random permutation that can be applied to an array of length {@code length}
   */
  static Permutation randomPermutation(int length) {
    return define(randomRanking(length));
  }


  /**
   * Generate a random ranking of given length.
   *
   * @param length a non-negative integer
   * @return a random ranking
   * @exception IllegalArgumentException if {@code length} is negative
   */
  static int[] randomRanking(int length) {
    int[] a = range(length);
    shuffle(a);
    return a;
  }


  /**
   * Shuffle the input array in place, using a random permutation.
   * This method will modify the input array.
   * @param a an array
   */
  private static void shuffle(int[] a) {
    Random r = new Random();
    for (int i = a.length - 1; i > 0; i--) {
      int j = r.nextInt(i + 1);
      if (j != i) {
        int tmp = a[j];
        a[j] = a[i];
        a[i] = tmp;
      }
    }
  }

  /**
   * Produce {@code length} random numbers between {@code 0} and {@code maxNumber} (inclusive)
   * @param maxNumber upper bound of random numbers
   * @param length result length
   * @return an array of random numbers
   */
  static int[] randomNumbers(int maxNumber, int length) {
    return randomNumbers(0, maxNumber, length);
  }

  /**
   * Generate {@code length} random numbers between {@code minNumber} and {@code maxNumber} (inclusive)
   * @param minNumber lower bound of random numbers
   * @param maxNumber upper bound of random numbers
   * @param length result length
   * @return an array of random numbers
   */
  static int[] randomNumbers(int minNumber, int maxNumber, int length) {
    if (minNumber > maxNumber) {
      throw new IllegalArgumentException("minNumber must be less than or equal to maxNumber");
    }
    Random random = ThreadLocalRandom.current();
    if (maxNumber < Integer.MAX_VALUE) {
      IntStream ints = random.ints(length, minNumber, maxNumber + 1);
      return ints.toArray();
    } else {
      LongStream longs = random.longs(length, minNumber, (long) maxNumber + 1l);
      int[] result = new int[length];
      long[] longArray = longs.toArray();
      for (int i = 0; i < longArray.length; i++)
        result[i] = Math.toIntExact(longArray[i]);
      return result;
    }
  }

}
