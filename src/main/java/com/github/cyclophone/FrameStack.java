package com.github.cyclophone;

import static java.lang.System.arraycopy;

// helper class for Rankings.symmetricGroup
final class FrameStack {

  private int[][] frames;
  private int[] frameLenghts;

  // pointer to top of the stack
  private int ptr;

  // tmp buffer for calculations
  private int[] tmp;

  FrameStack(int n) {
    int maxHeight = (n * (n + 1)) / 2;
    this.frames = new int[Math.max(maxHeight, 16)][n];
    this.frameLenghts = new int[Math.max(maxHeight, 16)];
    this.tmp = new int[n];
  }

  boolean isEmpty() {
    return ptr < 0;
  }

  int getLastLength() {
    return frameLenghts[ptr];
  }

  int[] removeLast() {
    return frames[ptr--];
  }

  void expandLast() {
    // save last frame into tmp, before it gets overwritten
    arraycopy(frames[ptr], 0, tmp, 0, frameLenghts[ptr]);
    int n = frameLenghts[ptr];
    // build (n + 1) longer frames, by inserting n
    for (int i = 0; i <= n; i++) {
      frameLenghts[ptr] = n + 1;
      int[] longerFrame = frames[ptr];
      arraycopy(tmp, 0, longerFrame, 0, i);
      arraycopy(tmp, i, longerFrame, i + 1, n - i);
      longerFrame[i] = n;
      ptr++;
    }
    // undo the last ptr++ (note that the loop runs at least once)
    ptr--;
  }
}
