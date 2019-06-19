package com.github.cyclophone;

import static java.lang.System.arraycopy;

// helper class for Rankings.symmetricGroup
final class FrameStack {

  private int[][] frames;
  private int[] frameLenghts;
  private int pos;

  // tmp buffer for calculations
  private int[] tmp;

  FrameStack(int n) {
    this.frames = new int[Math.max(n * n, 16)][n];  // stack height <= n * n (proof?)
    this.frameLenghts = new int[Math.max(n * n, 16)];
    this.tmp = new int[n];
  }

  boolean isEmpty() {
    return pos < 0;
  }

  int getLastLength() {
    return frameLenghts[pos];
  }

  int[] removeLast() {
    return frames[pos--];
  }

  void expandLast() {
    // save last frame into tmp, before it gets overwritten
    arraycopy(frames[pos], 0, tmp, 0, frameLenghts[pos]);
    int n = frameLenghts[pos];
    // build (n + 1) longer frames, by inserting n
    for (int i = 0; i <= n; i++) {
      frameLenghts[pos] = n + 1;
      int[] longerFrame = frames[pos];
      arraycopy(tmp, 0, longerFrame, 0, i);
      arraycopy(tmp, i, longerFrame, i + 1, n - i);
      longerFrame[i] = n;
      pos++;
    }
    // undo the last pos++ (note that the loop runs at least once)
    pos--;
  }
}
