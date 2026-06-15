package datastructures.graph;

import java.util.*;

/**
 * Graph - An undirected or directed graph using an adjacency-list representation.
 *
 * <p>Vertices are identified by {@code String} names.  Edges are stored as
 * a {@code HashMap<String, List<String>>}.  For undirected graphs, adding
 * edge (u, v) automatically adds edge (v, u).
 *
 * <p><b>Real-world uses:</b> social networks, road maps, internet routing,
 * dependency resolution.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>addVertex / addEdge : O(1) amortized</li>
 *   <li>BFS / DFS           : O(V + E)</li>
 *   <li>hasPath             : O(V + E)</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(abstract_data_type)">Graph (Wikipedia)</a>
 */
public class Graph {

    // ── Fields ────────────────────────────────────────────────────────────────

    /** Adjacency list: vertex → list of neighbours. */
    private final Map<String, List<String>> adjacencyList = new HashMap<>();

    /** {@code true} for a directed graph; {@code false} for undirected. */
    private final boolean directed;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Creates an empty graph.
     *
     * @param directed {@code true} for directed, {@code false} for undirected
     */
    public Graph(boolean directed) {
        this.directed = directed;
    }

    // ── Mutation ──────────────────────────────────────────────────────────────

    /**
     * Adds a vertex with no edges.  No-op if the vertex already exists.
     *
     * @param vertex vertex label
     */
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Adds an edge from {@code source} to {@code destination}.
     * Both vertices are created automatically if they do not exist.
     * For undirected graphs, the reverse edge is also added.
     *
     * @param source      origin vertex
     * @param destination target vertex
     */
    public void addEdge(String source, String destination) {
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(destination);
        if (!directed) {
            adjacencyList.get(destination).add(source);
        }
    }

    /**
     * Removes a vertex and all edges incident to it. O(V + E).
     *
     * @param vertex vertex to remove
     */
    public void removeVertex(String vertex) {
        adjacencyList.remove(vertex);
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(vertex);
        }
    }

    /**
     * Removes the edge from {@code source} to {@code destination}.
     * For undirected graphs, the reverse edge is also removed.
     *
     * @param source      origin vertex
     * @param destination target vertex
     */
    public void removeEdge(String source, String destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
        if (!directed && adjacencyList.containsKey(destination)) {
            adjacencyList.get(destination).remove(source);
        }
    }

    // ── Traversal ─────────────────────────────────────────────────────────────

    /**
     * Breadth-First Search starting at {@code start}.
     *
     * <p>Explores vertices level-by-level (nearest neighbours first).
     * Useful for finding the shortest path in an unweighted graph.
     *
     * @param start the starting vertex
     * @return vertices visited in BFS order
     */
    public List<String> bfs(String start) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            for (String neighbour : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (visited.add(neighbour)) { // add() returns false if already present
                    queue.add(neighbour);
                }
            }
        }
        return result;
    }

    /**
     * Depth-First Search starting at {@code start}.
     *
     * <p>Explores as far as possible along each branch before backtracking.
     * Useful for cycle detection, topological sort, and connected-component analysis.
     *
     * @param start the starting vertex
     * @return vertices visited in DFS order
     */
    public List<String> dfs(String start) {
        List<String> result = new ArrayList<>();
        dfsRecursive(start, new HashSet<>(), result);
        return result;
    }

    private void dfsRecursive(String current, Set<String> visited, List<String> result) {
        visited.add(current);
        result.add(current);
        for (String neighbour : adjacencyList.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbour)) {
                dfsRecursive(neighbour, visited, result);
            }
        }
    }

    // ── Query ─────────────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if there is a path from {@code start} to {@code end}.
     *
     * @param start origin vertex
     * @param end   target vertex
     * @return {@code true} if reachable
     */
    public boolean hasPath(String start, String end) {
        return bfs(start).contains(end);
    }

    /** Returns the neighbours of {@code vertex}, or an empty list if not found. */
    public List<String> getNeighbours(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    /** Returns the number of vertices. */
    public int vertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the number of edges.
     * For undirected graphs each edge is counted once.
     */
    public int edgeCount() {
        int total = adjacencyList.values().stream().mapToInt(List::size).sum();
        return directed ? total : total / 2;
    }

    /** Returns {@code true} if this graph has no vertices. */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph (").append(directed ? "directed" : "undirected").append("):\n");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }
}
