package com.github.cyclophone;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.cyclophone.Permutation.cycle;
import static com.github.cyclophone.Span.span;

class CayleyTest {

  private final Permutation g = cycle(1, 2, 3);
  private final Permutation h = cycle(1, 3, 4);

  private List<Permutation> a4() {
    List<Permutation> result = new ArrayList<>(span(g, h));
    Collections.shuffle(result);
    return result;
  }

  @Test
  void genA4dot() {
    List<Permutation> a4 = a4();
    System.out.println("digraph {");
    for (Permutation p : a4) {
      System.out.println("  p" + p.print(4) + "[label=\"" + p.print(4) + "\"];");
    }
    for (Permutation a1 : a4) {
      for (Permutation a2 : a4) {
        if (g.compose(a1).equals(a2)) {
          System.out.println("  p" + a1.print(4) + " -> " + "p" + a2.print(4) + "[color=red,penwidth=1.0];");
        } else if (h.compose(a1).equals(a2)) {
          System.out.println("  p" + a1.print(4) + " -> " + "p" + a2.print(4) + "[color=blue,penwidth=2.0];");
        }
      }
    }
    System.out.println("}");
  }
}
