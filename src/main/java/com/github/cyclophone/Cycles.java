package com.github.cyclophone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cyclophone.ArrayUtil.checkLength;

/**
 * <p>
 * An operation that shuffles arrays and lists.
 * </p>
 * <p>
 * This implementation is based on cyclic decomposition and will sometimes use less memory than an equivalent
 * {@link Permutation}. In some cases it can be faster when applied to a list or array, and it can optionally
 * be applied in a destructive manner, see the {@code clobber} and {@code unclobber} methods.
 * </p>
 * <p>
 * Applying it to a single index is {@code O(n)} whereas {@link Permutation} does this in constant time.
 * </p>
 *
 * @see Permutation#toCycles()
 */
final class Cycles implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final Cycles IDENTITY = new Cycles(new int[0][], 0);

  private final int length;
  private final int[][] cycles;

  private Cycles(int[][] cycles, int length) {
    this.length = length;
    this.cycles = cycles;
  }

  /**
   * Get the identity permutation.
   *
   * @return the identity permutation
   */
  static Cycles identity() {
    return IDENTITY;
  }

  /**
   * Define a new operation as a list of cycles.
   *
   * @param cycles a list of cycles
   * @return the operation defined by the input
   */
  static Cycles create(int[][] cycles) {
    if (cycles.length == 0)
      return IDENTITY;
    int maxIndex = 0;
    for (int[] orbit : cycles)
      for (int i : orbit)
        maxIndex = Math.max(maxIndex, i);
    return new Cycles(cycles, maxIndex + 1);
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(int[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        int temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(int[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        int temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(byte[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        byte temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(byte[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        byte temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(char[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        char temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(char[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        char temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(short[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        short temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(short[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        short temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(float[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        float temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(float[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        float temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(double[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        double temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(double[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        double temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(long[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        long temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(long[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        long temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void clobber(Object[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        Object temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input array.
   *
   * @param array an array
   * @exception IllegalArgumentException if {@code array.length < this.length()}
   */
  void unclobber(Object[] array) {
    checkLength(length, array.length);
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        Object temp = array[cycle[j + 1]];
        array[cycle[j + 1]] = array[cycle[j]];
        array[cycle[j]] = temp;
      }
    }
  }

  /**
   * Apply this operation by modifying the input list.
   * The input list must support {@link List#set(int, Object)}.
   *
   * @param list a list
   * @exception UnsupportedOperationException if the input list is not mutable
   * @exception IllegalArgumentException if {@code list.size() < this.length()}
   */
  <E> void clobber(List<E> list) {
    checkLength(length, list.size());
    for (int[] cycle : cycles) {
      for (int j = cycle.length - 2; j >= 0; j--) {
        E temp = list.get(cycle[j + 1]);
        list.set(cycle[j + 1], list.get(cycle[j]));
        list.set(cycle[j], temp);
      }
    }
  }

  /**
   * Undo the action of this operation by modifying the input list.
   * The input list must support {@link List#set(int, Object)}.
   *
   * @param list a list
   * @exception UnsupportedOperationException if the input list is not mutable
   * @exception IllegalArgumentException if {@code list.size() < this.length()}
   */
  <E> void unclobber(List<E> list) {
    checkLength(length, list.size());
    for (int[] cycle : cycles) {
      for (int j = 0; j < cycle.length - 1; j++) {
        E temp = list.get(cycle[j + 1]);
        list.set(cycle[j + 1], list.get(cycle[j]));
        list.set(cycle[j], temp);
      }
    }
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  int[] apply(int[] a) {
    int[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  byte[] apply(byte[] a) {
    byte[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  char[] apply(char[] a) {
    char[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  short[] apply(short[] a) {
    short[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  float[] apply(float[] a) {
    float[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  double[] apply(double[] a) {
    double[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  long[] apply(long[] a) {
    long[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new array. This method does not modify the input.
   *
   * @param a an array of length not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.length < this.length()}
   */
  <E> E[] apply(E[] a) {
    E[] copy = Arrays.copyOf(a, a.length);
    clobber(copy);
    return copy;
  }

  /**
   * Apply this operation to produce a new list. This method does not modify the input.
   *
   * @param a a list of size not less than {@code this.length()}
   * @return the result of applying this permutation to {@code a}
   * @exception java.lang.IllegalArgumentException if {@code a.size() < this.length()}
   */
  <E> List<E> apply(List<E> a) {
    ArrayList<E> copy = new ArrayList<E>(a);
    clobber(copy);
    return copy;
  }

  /**
   * Move an index. This method will not fail if the input is negative, but just return it unchanged.
   *
   * @param n a number
   * @return the moved index
   */
  int apply(int n) {
    for (int[] cycle : cycles)
      for (int j = cycle.length - 2; j >= 0; j--)
        n = n == cycle[j] ? cycle[j + 1] : n == cycle[j + 1] ? cycle[j] : n;
    return n;
  }

  /**
   * Move an index back. This method will not fail if the input is negative, but just return it unchanged.
   *
   * @param n a number
   * @return the moved index
   */
  int unApply(int n) {
    for (int[] cycle : cycles)
      for (int j = 0; j < cycle.length - 1; j++)
        n = n == cycle[j] ? cycle[j + 1] : n == cycle[j + 1] ? cycle[j] : n;
    return n;
  }

  /**
   * Uncompile this operation.
   *
   * @return a ranking-based version of this operation
   */
  Permutation toPermutation() {
    int[] ranking = ArrayUtil.range(length);
    unclobber(ranking);
    return Permutation.define(ranking);
  }

  /**
   * Composing with another permutation creates a new operation.
   *
   * @param other another permutation
   * @return the composition or product
   */
  Permutation comp(Cycles other) {
    if (length == 0)
      return other.toPermutation();
    if (other.length == 0)
      return this.toPermutation();
    return this.toPermutation().compose(other.toPermutation());
  }

  /**
   * Take the product of the input operations, in order.
   *
   * @param permutations an array of permutations
   * @return the composition or product
   */
  static Permutation product(Cycles... permutations) {
    Permutation result = Permutation.identity();
    for (Cycles permutation : permutations)
      result = result.compose(permutation.toPermutation());
    return result;
  }

  /**
   * Take the product of the input operations, in order.
   *
   * @param permutations a list of permutations
   * @return the composition or product
   */
  static Permutation product(List<Cycles> permutations) {
    Permutation result = Permutation.identity();
    for (Cycles permutation : permutations)
      result = result.compose(permutation.toPermutation());
    return result;
  }

  @Override
  public String toString() {
    if (cycles.length == 0) {
      return "()";
    }
    return Arrays.stream(cycles)
        .map(a -> Arrays.stream(a).map(i -> i + 1).mapToObj(Integer::toString).collect(Collectors.joining(" ")))
        .map(s -> '(' + s + ')')
        .collect(Collectors.joining(" "));
  }

  /**
   * Return the minimum number of elements that an array or list must have, in order for this operation to
   * be applicable.
   *
   * @return the length of this operation
   */
  int length() {
    return length;
  }

  /**
   * Get the number of cycles of this operation.
   *
   * @return the number of cycles
   */
  int numCycles() {
    return cycles.length;
  }

  /**
   * Get the length of the {@code n}th cycle.
   *
   * @return the length of the {@code n}th cycle
   * @exception java.lang.ArrayIndexOutOfBoundsException if the {@code n}th cycle does not exist
   */
  int cycleLength(int n) {
    return cycles[n].length;
  }

  /**
   * Get a copy of the {@code n}th cycle.
   *
   * @return the {@code n}th cycle
   * @exception java.lang.ArrayIndexOutOfBoundsException if the {@code n}th cycle does not exist
   */
  int[] getCycle(int n) {
    return Arrays.copyOf(cycles[n], cycles[n].length);
  }

  /**
   * Get the {@code m}th element of the {@code n}th cycle.
   *
   * @return the {@code m}th element of the {@code n}th cycle
   * @exception java.lang.ArrayIndexOutOfBoundsException if the {@code m}th element of the {@code n}th cycle does not exist
   */
  int getCycleElement(int n, int m) {
    return cycles[n][m];
  }

  /**
   * Calculate the <a href="http://en.wikipedia.org/wiki/Parity_of_a_permutation">signature</a> of this permutation.
   *
   * @return {@code 1} if this permutation can be written as an even number of transpositions, {@code -1} otherwise
   */
  int signature() {
    boolean even = true;
    for (int[] cycle : cycles)
      if (cycle.length % 2 == 0)
        even = !even;
    return even ? 1 : -1;
  }

  /**
   * Check if this is an even permutation.
   *
   * @return true if the signature of this permutation is {@code 1}
   * @see #signature()
   */
  boolean isEven() {
    return signature() == 1;
  }

  /**
   * Check if this is an odd permutation.
   *
   * @return true if the signature of this permutation is {@code -11}
   * @see #signature()
   */
  boolean isOdd() {
    return signature() == -1;
  }

}
