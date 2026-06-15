package algorithms.graphalgorithms;

import java.util.*;

/**
 * GraphAlgorithms - Graph algorithm implementations.
 *
 * <p>Includes Dijkstra's shortest path, Bellman-Ford (handles negative weights),
 * Kahn's topological sort (BFS-based), and DFS-based cycle detection.
 *
 * <p>All algorithms operate on an adjacency-list representation of the graph
 * provided via {@link WeightedGraph} or a plain {@code Map<String, List<String>>}.
 */
public final class GraphAlgorithms {

    private GraphAlgorithms() {}

    // ── Supporting types ──────────────────────────────────────────────────────

    /** A directed, weighted edge. */
    public static class Edge {
        public final String to;
        public final int weight;

        public Edge(String to, int weight) {
            this.to     = to;
            this.weight = weight;
        }

        @Override public String toString() { return to + "(" + weight + ")"; }
    }

    /** A weighted, undirected graph backed by an adjacency list. */
    public static class WeightedGraph {
        final Map<String, List<Edge>> adj = new HashMap<>();

        public void addVertex(String v) {
            adj.putIfAbsent(v, new ArrayList<>());
        }

        /** Adds an undirected edge between {@code from} and {@code to}. */
        public void addEdge(String from, String to, int weight) {
            addVertex(from);
            addVertex(to);
            adj.get(from).add(new Edge(to,   weight));
            adj.get(to).add(new Edge(from, weight));
        }

        public List<Edge> neighbours(String v) {
            return adj.getOrDefault(v, Collections.emptyList());
        }

        public Set<String> vertices() { return adj.keySet(); }
    }

    // ── 1. Dijkstra ───────────────────────────────────────────────────────────

    /**
     * Dijkstra's algorithm — finds the shortest distance from {@code start}
     * to every reachable vertex in a graph with non-negative edge weights.
     *
     * <p>Time: O((V + E) log V) with a binary-heap priority queue.
     *
     * @param graph the weighted graph
     * @param start the source vertex
     * @return map of vertex → shortest distance from {@code start}
     */
    public static Map<String, Integer> dijkstra(WeightedGraph graph, String start) {
        Map<String, Integer> dist = new HashMap<>();
        for (String v : graph.vertices()) dist.put(v, Integer.MAX_VALUE);
        dist.put(start, 0);

        // PQ entries: [distance, vertex]
        PriorityQueue<Object[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> (int) e[0]));
        pq.offer(new Object[]{0, start});

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Object[] entry     = pq.poll();
            int      currDist  = (int)    entry[0];
            String   curr      = (String) entry[1];

            if (!visited.add(curr)) continue;

            for (Edge edge : graph.neighbours(curr)) {
                int newDist = currDist + edge.weight;
                if (newDist < dist.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                    dist.put(edge.to, newDist);
                    pq.offer(new Object[]{newDist, edge.to});
                }
            }
        }
        return dist;
    }

    // ── 2. Bellman-Ford ───────────────────────────────────────────────────────

    /**
     * Bellman-Ford algorithm — finds shortest distances from {@code start},
     * handling graphs with negative edge weights.
     * Returns {@code null} if a negative-weight cycle is detected.
     *
     * <p>Time: O(V × E).
     *
     * @param graph       the weighted graph
     * @param start       source vertex
     * @param vertexCount total number of vertices (needed for V-1 relaxations)
     * @return shortest distances map, or {@code null} on a negative cycle
     */
    public static Map<String, Integer> bellmanFord(WeightedGraph graph, String start,
                                                   int vertexCount) {
        Map<String, Integer> dist = new HashMap<>();
        for (String v : graph.vertices()) dist.put(v, Integer.MAX_VALUE);
        dist.put(start, 0);

        // Relax all edges V-1 times
        for (int i = 0; i < vertexCount - 1; i++) {
            for (String u : graph.vertices()) {
                if (dist.get(u) == Integer.MAX_VALUE) continue;
                for (Edge edge : graph.neighbours(u)) {
                    int candidate = dist.get(u) + edge.weight;
                    if (candidate < dist.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                        dist.put(edge.to, candidate);
                    }
                }
            }
        }

        // Check for negative-weight cycles
        for (String u : graph.vertices()) {
            if (dist.get(u) == Integer.MAX_VALUE) continue;
            for (Edge edge : graph.neighbours(u)) {
                if (dist.get(u) + edge.weight < dist.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                    return null; // negative cycle detected
                }
            }
        }
        return dist;
    }

    // ── 3. Topological Sort (Kahn's BFS) ─────────────────────────────────────

    /**
     * Topological sort using Kahn's algorithm (BFS-based).
     * Valid only for directed acyclic graphs (DAGs).
     *
     * <p>The result is a linear ordering of vertices such that for every directed
     * edge u→v, vertex u appears before v in the ordering.
     *
     * <p>Time: O(V + E).
     *
     * @param graph adjacency list of a directed acyclic graph
     * @return vertices in topological order (may be incomplete if a cycle exists)
     */
    public static List<String> topologicalSort(Map<String, List<String>> graph) {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String u : graph.keySet()) {
            inDegree.putIfAbsent(u, 0);
            for (String v : graph.get(u)) {
                inDegree.merge(v, 1, Integer::sum);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0) queue.add(e.getKey());
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String u = queue.poll();
            result.add(u);
            for (String v : graph.getOrDefault(u, Collections.emptyList())) {
                if (inDegree.merge(v, -1, Integer::sum) == 0) queue.add(v);
            }
        }
        return result;
    }

    // ── 4. Cycle Detection (directed graph) ───────────────────────────────────

    /**
     * Returns {@code true} if the directed graph contains at least one cycle.
     * Uses DFS with three-colour marking (WHITE / GRAY / BLACK).
     *
     * <p>Time: O(V + E).
     *
     * @param graph adjacency list of a directed graph
     * @return {@code true} if a cycle exists
     */
    public static boolean hasCycle(Map<String, List<String>> graph) {
        Map<String, Integer> color = new HashMap<>();
        for (String v : graph.keySet()) color.put(v, 0); // WHITE = 0
        for (String v : graph.keySet()) {
            if (color.get(v) == 0 && dfsCycle(graph, v, color)) return true;
        }
        return false;
    }

    /** @return true if a back-edge (cycle) is found starting from {@code u}. */
    private static boolean dfsCycle(Map<String, List<String>> graph,
                                    String u, Map<String, Integer> color) {
        color.put(u, 1); // GRAY — currently in the DFS stack
        for (String v : graph.getOrDefault(u, Collections.emptyList())) {
            Integer c = color.getOrDefault(v, 0);
            if (c == 1) return true; // back edge → cycle
            if (c == 0 && dfsCycle(graph, v, color)) return true;
        }
        color.put(u, 2); // BLACK — fully processed
        return false;
    }
}
