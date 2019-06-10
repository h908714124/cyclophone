package com.github.cyclophone;

import static com.github.cyclophone.Permutation.cycle0;

final class Move {

  /**
   * Creates a cycle that acts as a delete followed by an insert. Examples:
   * <pre><code>
   *   Permutation.move(0, 2).apply("12345");
   *   =&gt; 23145
   *   </code></pre>
   * <pre><code>
   *   Permutation.move(3, 1).apply("12345");
   *   =&gt; 14235
   * </code></pre>
   * If {@code delete == insert}, the identity of length {@code delete + 1} is returned.
   *
   * @param delete a non-negative integer
   * @param insert a non-negative integer
   * @return a permutation of length {@code Math.max(delete, insert) + 1}
   */
  static Permutation move(int delete, int insert) {
    return cycle0(ArrayUtil.range(insert, delete, true));
  }
}
