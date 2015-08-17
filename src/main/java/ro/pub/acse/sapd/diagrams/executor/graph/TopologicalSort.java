package ro.pub.acse.sapd.diagrams.executor.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Topological sort</b> solves a problem of - finding a linear ordering
 * of the vertices of <i>V</i> such that for each edge <i>(i, j) ? E</i>,
 * vertex <i>i</i> is to the left of vertex <i>j</i>. (Skiena 2008, p. 481)
 */
public class TopologicalSort {
    /**
     * Method is derived from of <a
     * href="http://en.wikipedia.org/wiki/Topological_sort#Algorithms" > Kahn's
     * pseudo code</a> and traverses over vertices as they are returned by input
     * map. Leaf nodes can have null or empty values. This method assumes, that
     * input is valid DAG, so if cyclic dependency is detected, error is thrown.
     * tSortFix is a fix to remove self dependencies and add missing leaf nodes.
     * </p>
     * <p>
     * <pre>
     * // For input with elements:
     * { F1=[F2, F3, F4], F10=[F7, F4], F11=[F4], F2=[F3, F8, F4], F3=[F6],
     *   F4=null, F5=[F6, F4], F6=[F7, F8, F4], F7=[F4], F8=[F4], F9=[F4]}
     *
     * // Output based on input map type:
     * HashMap: [F4, F11, F8, F9, F7, F10, F6, F5, F3, F2, F1]
     * TreeMap: [F4, F11, F7, F8, F9, F10, F6, F3, F5, F2, F1]
     * </pre>
     *
     * @param g <a href="http://en.wikipedia.org/wiki/Directed_acyclic_graph"
     *          > Directed Acyclic Graph</a>, where vertices are stored as
     *          {@link java.util.HashMap HashMap} elements.
     * @return Linear ordering of input nodes.
     * @throws TopologicalSortException Thrown when cyclic dependency is detected, error message also
     *                                  contains elements in cycle.
     */
    public static <T> List<T> tSort(Map<T, List<T>> g) throws TopologicalSortException {
        /**
         * @param L
         *            Answer.
         * @param S
         *            Not visited leaf vertices.
         * @param V
         *            Visited vertices.
         * @param P
         *            Defined vertices.
         * @param n
         *            Current element.
         */
        List<T> L = new ArrayList<>(g.size());
        Queue<T> S = new java.util.concurrent.LinkedBlockingDeque<>();
        HashSet<T> V = new java.util.HashSet<>(),
                P = new java.util.HashSet<>();
        P.addAll(g.keySet());
        T n;

        // Find leaf nodes.
        S.addAll(P.stream().filter(t -> g.get(t) == null || g.get(t).isEmpty()).collect(Collectors.toList()));

        // Visit all leaf nodes. Build result from vertices, that are visited
        // for the first time. Add vertices to not visited leaf vertices S, if
        // it contains current element n an all of it's values are visited.
        while (!S.isEmpty()) {
            if (V.add(n = S.poll()))
                L.add(n);
            S.addAll(g.keySet().stream().filter(t -> g.get(t) != null && !g.get(t).isEmpty() && !V.contains(t)
                    && V.containsAll(g.get(t))).collect(Collectors.toList()));
        }

        // Return result.
        if (L.containsAll(P))
            return L;

        // Throw exception.
        StringBuilder sb = new StringBuilder(
                "\nInvalid DAG: a cyclic dependency detected :\n");
        P.stream().filter(t -> !L.contains(t)).forEach(t -> sb.append(t).append(" "));
        throw new TopologicalSortException(sb.append("\n").toString());
    }

    /**
     * Method removes self dependencies and adds missing leaf nodes.
     *
     * @param g <a href="http://en.wikipedia.org/wiki/Directed_acyclic_graph"
     *          > Directed Acyclic Graph</a>, where vertices are stored as
     *          {@link java.util.HashMap HashMap} elements.
     */
    public static <T> void tSortFix(Map<T, List<T>> g) {
        java.util.List<T> tmp;
        java.util.Set<T> P = new HashSet<>();
        P.addAll(g.keySet());

        for (T t : P)
            if (g.get(t) != null || !g.get(t).isEmpty()) {
                (tmp = g.get(t)).remove(t);
                tmp.stream().filter(m -> !P.contains(m)).forEach(m -> g.put(m, new ArrayList<>(0)));
            }
    }
}
