package com.github.cyclophone;

import static com.github.cyclophone.Permutation.define0;

final class Shift {

  /**
   * Returns a shifted permutation. The following is true for the shifted permutation:
   * <pre><code>
   *   p.shift(n).apply(j) = j, j &lt; n
   *   p.shift(n).apply(n + i) = n + p.apply(i)
   * </code></pre>
   *
   * @param n a non negative number
   * @return the shifted permutation
   * @exception java.lang.IllegalArgumentException if n is negative
   */
  static Permutation shift(Permutation p, int n) {
    int[] ranking = p.getRanking();
    if (ranking.length == 0 && n == 0)
      return p;
    return define0(Rankings.shift(n, ranking));
  }
}
