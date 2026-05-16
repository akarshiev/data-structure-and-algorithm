package algorithms.graphalgorithms;

import java.util.*;

/**
 * GraphAlgorithms - Graf algoritmlari to'plami.
 * 
 * GRAF ALGORITMLARI NIMA UCHUN MUHIM?
 * Graf algoritmlari ko'p real hayot muammolarini hal qiladi:
 * - Eng qisqa yo'l (GPS navigatsiya)
 * - Tarmoq analizi (internet)
 * - Social network (do'stlar topish)
 * - Ma'lumotlar markazi (serverlar orasidagi ulanish)
 * 
 * @author DSA Project
 */
public class GraphAlgorithms {

    // ==================== GRAF TUZILMASI ====================
    
    /**
     * Weighted Edge - Og'irlikli chiziq.
     * Har bir chiziq boshlang'ich, maqsad va og'irlikka ega.
     */
    static class Edge {
        String to;      // Maqsad tugun
        int weight;     // Og'irlik (masofa, narx)
        
        Edge(String to, int weight) {
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public String toString() {
            return to + "(" + weight + ")";
        }
    }

    /**
     * Weighted Graph - Og'irlikli graf.
     */
    static class WeightedGraph {
        Map<String, List<Edge>> adjacencyList;
        
        WeightedGraph() {
            adjacencyList = new HashMap<>();
        }
        
        void addVertex(String vertex) {
            adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        }
        
        void addEdge(String from, String to, int weight) {
            addVertex(from);
            addVertex(to);
            adjacencyList.get(from).add(new Edge(to, weight));
            // Yo'nalimsiz graf uchun:
            adjacencyList.get(to).add(new Edge(from, weight));
        }
        
        List<Edge> getNeighbors(String vertex) {
            return adjacencyList.getOrDefault(vertex, new ArrayList<>());
        }
    }

    // ==================== 1. DIJKSTRA ALGORITMI ====================
    
    /**
     * Dijkstra - Eng qisqa yo'l (barcha manzillar uchun).
     * 
     * MUAMMO: Bitta boshlang'ich tugundan barcha boshqa tugunlargacha
     * eng qisqa yo'lni topish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Boshlang'ich nuqtaning masofasini 0 ga, boshqalarini "cheksiz" ga qo'yamiz
     * 2. Eng yaqin tugunni tanlaymiz (priority queue)
     * 3. Uning qo'shnilarini tekshiramiz
     * 4. Agar yangi yo'l qisqaroq bo'lsa, yangilaymiz
     * 5. Barcha tugunlar ko'rilmaguncha takrorlaymiz
     * 
     * MISOL:
     *     A --1-- B
     *     |        |
     *     4        2
     *     |        |
     *     C --3-- D
     * 
     * A dan boshlanganda:
     * A: 0, B: 1, C: 4, D: 3
     * 
     * TEZLIK: O((V + E) log V) - Priority Queue bilan
     * 
     * @param graph - og'irlikli graf
     * @param start - boshlang'ich tugun
     * @return eng qisqa masofalar
     */
    public static Map<String, Integer> dijkstra(WeightedGraph graph, String start) {
        // Masofalar jadvali
        Map<String, Integer> distances = new HashMap<>();
        // Ko'rilgan tugunlar
        Set<String> visited = new HashSet<>();
        // Priority Queue - eng yaqin tugunni olish uchun
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        
        // Barcha masofalarni "cheksiz" ga qo'yamiz
        for (String vertex : graph.adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        // Boshlang'ich tugumni qo'shamiz
        pq.add(new int[]{0, 0}); // [index, distance]
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            String currentVertex = String.valueOf((char)('A' + current[0]));
            
            // Agar allaqachon ko'rilgan bo'lsa, o'tkazib yuboramiz
            if (visited.contains(currentVertex)) continue;
            visited.add(currentVertex);
            
            // Qo'shnilarini tekshiramiz
            for (Edge edge : graph.getNeighbors(currentVertex)) {
                if (!visited.contains(edge.to)) {
                    int newDistance = distances.get(currentVertex) + edge.weight;
                    if (newDistance < distances.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                        distances.put(edge.to, newDistance);
                        pq.add(new int[]{edge.to.charAt(0) - 'A', newDistance});
                    }
                }
            }
        }
        
        return distances;
    }

    // ==================== 2. BELLMAN-FORD ALGORITMI ====================
    
    /**
     * Bellman-Ford - Manfiy og'irliklar bilan eng qisqa yo'l.
     * 
     * AFZALLIGI: Dijkstra dan farqli o'laroq, manfiy og'irliklar bilan ishlaydi.
     * 
     * QANDAY ISHLAYDI?
     * 1. Barcha masofalarni "cheksiz" ga qo'yamiz
     * 2. V-1 marta barcha chiziqlarni relaksatsiya qilamiz
     * 3. Agar yana yangilanish bo'lsa, manfiy halqa bor
     * 
     * TEZLIK: O(V * E)
     * 
     * @param graph - og'irlikli graf
     * @param start - boshlang'ich tugun
     * @return eng qisqa masofalar yoki null (manfiy halqa bo'lsa)
     */
    public static Map<String, Integer> bellmanFord(WeightedGraph graph, String start, int vertexCount) {
        Map<String, Integer> distances = new HashMap<>();
        
        // Barcha masofalarni "cheksiz" ga qo'yamiz
        for (String vertex : graph.adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        // V-1 marta relaksatsiya
        for (int i = 0; i < vertexCount - 1; i++) {
            for (String vertex : graph.adjacencyList.keySet()) {
                if (distances.get(vertex) == Integer.MAX_VALUE) continue;
                
                for (Edge edge : graph.getNeighbors(vertex)) {
                    int newDist = distances.get(vertex) + edge.weight;
                    if (newDist < distances.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                        distances.put(edge.to, newDist);
                    }
                }
            }
        }
        
        // Manfiy halqani tekshiramiz
        for (String vertex : graph.adjacencyList.keySet()) {
            if (distances.get(vertex) == Integer.MAX_VALUE) continue;
            
            for (Edge edge : graph.getNeighbors(vertex)) {
                if (distances.get(vertex) + edge.weight < distances.getOrDefault(edge.to, Integer.MAX_VALUE)) {
                    System.out.println("Manfiy halqa topildi!");
                    return null;
                }
            }
        }
        
        return distances;
    }

    // ==================== 3. TOPOLOGICAL SORT ====================
    
    /**
     * Topologik saralash - DAG (Directed Acyclic Graph) uchun.
     * 
     * MUAMMO: Tugunlarni shunday tartibda joylashtirishki,
     * har bir chiziq oldingi tugundan keyingisiga qarab yo'naltirilgan bo'lsin.
     * 
     * FOYDALANISH:
     * - Vazifalarni bajarish tartibi
     * - Kurs rejalari (prerequisiteler)
     * - Build tizimlari
     * 
     * TEZLIK: O(V + E)
     * 
     * @param graph - yo'nali graf (adjacency list)
     * @return topologik tartib
     */
    public static List<String> topologicalSort(Map<String, List<String>> graph) {
        // Kirish darajalari (in-degree)
        Map<String, Integer> inDegree = new HashMap<>();
        for (String vertex : graph.keySet()) {
            inDegree.putIfAbsent(vertex, 0);
            for (String neighbor : graph.get(vertex)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }
        
        // Kirish darajasi 0 bo'lgan tugunlarni qo'shamiz
        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }
        
        List<String> result = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            
            for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }

    // ==================== 4. CYCLE DETECTION ====================
    
    /**
     * Grafda halqa (cycle) mavjudligini aniqlash.
     * 
     * YO'NALIMLI GRAF UCHUN: DFS bilan "coloring" usuli
     * - WHITE: hali ko'rilmagan
     * - GRAY: recursion stekda
     * - BLACK: to'liq ko'rilgan
     * 
     * Agar GRAY tugun qayta uchrasa, halqa bor!
     * 
     * TEZLIK: O(V + E)
     */
    static final int WHITE = 0, GRAY = 1, BLACK = 2;
    
    public static boolean hasCycle(Map<String, List<String>> graph) {
        Map<String, Integer> color = new HashMap<>();
        for (String vertex : graph.keySet()) {
            color.put(vertex, WHITE);
        }
        
        for (String vertex : graph.keySet()) {
            if (color.get(vertex) == WHITE) {
                if (hasCycleDFS(graph, vertex, color)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean hasCycleDFS(Map<String, List<String>> graph, String vertex, Map<String, Integer> color) {
        color.put(vertex, GRAY);
        
        for (String neighbor : graph.getOrDefault(vertex, new ArrayList<>())) {
            if (color.get(neighbor) == GRAY) return true; // Halqa topildi!
            if (color.get(neighbor) == WHITE && hasCycleDFS(graph, neighbor, color)) {
                return true;
            }
        }
        
        color.put(vertex, BLACK);
        return false;
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Graph Algorithms Sinov ===\n");
        
        // Dijkstra
        System.out.println("--- Dijkstra Algoritmi ---");
        WeightedGraph graph = new WeightedGraph();
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "D", 2);
        graph.addEdge("C", "D", 3);
        
        Map<String, Integer> distances = dijkstra(graph, "A");
        System.out.println("A dan barcha tugunlargacha eng qisqa masofalar:");
        for (Map.Entry<String, Integer> entry : distances.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        // Bellman-Ford
        System.out.println("\n--- Bellman-Ford Algoritmi ---");
        WeightedGraph graph2 = new WeightedGraph();
        graph2.addEdge("A", "B", 1);
        graph2.addEdge("A", "C", 4);
        graph2.addEdge("B", "D", 2);
        graph2.addEdge("C", "D", 3);
        
        Map<String, Integer> bfDistances = bellmanFord(graph2, "A", 4);
        if (bfDistances != null) {
            System.out.println("A dan barcha tugunlargacha eng qisqa masofalar:");
            for (Map.Entry<String, Integer> entry : bfDistances.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
        }
        
        // Topological Sort
        System.out.println("\n--- Topologik Saralash ---");
        Map<String, List<String>> dag = new HashMap<>();
        dag.put("A", Arrays.asList("B", "C"));
        dag.put("B", Arrays.asList("D"));
        dag.put("C", Arrays.asList("D"));
        dag.put("D", new ArrayList<>());
        
        List<String> topoOrder = topologicalSort(dag);
        System.out.println("Topologik tartib: " + topoOrder);
        
        // Cycle Detection
        System.out.println("\n--- Halqa Aniqlash ---");
        Map<String, List<String>> graphWithCycle = new HashMap<>();
        graphWithCycle.put("A", Arrays.asList("B"));
        graphWithCycle.put("B", Arrays.asList("C"));
        graphWithCycle.put("C", Arrays.asList("A")); // Halqa!
        
        System.out.println("Halqa mavjudmi: " + hasCycle(graphWithCycle));
    }
}
