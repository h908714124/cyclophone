package com.github.cyclophone;

import static com.github.cyclophone.ArrayUtil.negativeFailure;
import static com.github.cyclophone.Permutation.define0;

final class Reverse {

  /**
   * Check if this permutation reverses its input.
   *
   * @param n a nonnegative number
   * @return true if this permutation reverses or "flips" an input of length {@code n}
   * @exception java.lang.IllegalArgumentException if {@code n} is negative
   */
  static boolean reverses(Permutation p, int n) {
    int[] ranking = p.getRanking();
    if (ranking.length < n)
      return false;
    if (n < 0)
      negativeFailure();
    for (int i = 0; i < n; i += 1)
      if (ranking[i] != ranking.length - i - 1)
        return false;
    return true;
  }

  /**
   * <p>Returns a permutation that reverses its input. Example:</p>
   * <pre><code>
   *   Permutation.reverse(5).apply("12345");
   *   =&gt; 54321
   * </code></pre>
   *
   * @param length a non negative number
   * @return a permutation that reverses an array of length {@code length}
   */
  static Permutation reverse(int length) {
    int[] result = new int[length];
    for (int i = 0; i < length; i += 1) {
      result[i] = length - i - 1;
    }
    return define0(result);
  }
}
