package data.structures.graph;

import java.util.*;

/**
 * Graph - Graf (tarmoq) tuzilmasi.
 * 
 * NIMA UCHUN GRAPH KERAK?
 * Graf nuqtalar (vertices) va ularni bog'laydigan chiziqlardan (edges) tashkil topgan.
 * Real hayotdagi ko'p muammolar graf sifatida modellashtiriladi:
 * - Ijtimoiy tarmoqlar (odamlar orasidagi do'stlik)
 * - Yo'l xaritasi (shaharlar orasidagi yo'llar)
 * - Internet tarmoqi (kompyuterlar orasidagi bog'lanishlar)
 * 
 * GRAPH TURLARI:
 * 1. Yo'nalimsiz (Undirected) - A->B bo'lsa, B->A ham bor
 * 2. Yo'nali (Directed) - A->B bo'lsa, B->A ham bo'lmasligi mumkin
 * 3. Og'irlikli (Weighted) - har bir chiziqning qiymati bor
 * 4. Og'irliksiz (Unweighted) - barcha chiziqlar bir xil
 * 
 * SAQLASH USULLARI:
 * 1. Adjacency Matrix - qo'shni matritsa
 * 2. Adjacency List - qo'shni ro'yxat (biz ishlatamiz)
 * 
 * MURAKKABLIK:
 * - BFS/DFS: O(V + E) - V = tugunlar, E = chiziqlar
 * - Qo'shish: O(1)
 * - Qidirish: O(V)
 * 
 * @author DSA Project
 */
public class Graph {

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Adjacency List - qo'shni ro'yxat.
     * Har bir tugun uchun, qaysi tugunlar bilan bog'langanini saqlaymiz.
     * 
     * NIMA UCHUN MAP?
     * 1. Noyob kalitlar - har bir tugun nomi noyob
     * 2. Tez qidirish - O(1) amortizatsiya
     * 3. Dinamik - yangi tugunlar qo'shish oson
     * 
     * MISOL:
     * "A" -> ["B", "C"]  (A B va C bilan bog'langan)
     * "B" -> ["A", "D"]  (B A va D bilan bog'langan)
     * "C" -> ["A"]       (C faqat A bilan bog'langan)
     */
    private Map<String, List<String>> adjacencyList;
    
    /**
     * Yo'nali graf ekanligini bildiradi.
     * Agar yo'nali bo'lsa, A->B bo'lsa, B->A avtomatik qo'shilmaydi.
     */
    private final boolean directed;

    // ==================== YARATISH ====================
    
    /**
     * Graf yaratish.
     * 
     * @param directed - yo'nali graf bo'lsa true
     */
    public Graph(boolean directed) {
        this.adjacencyList = new HashMap<>();
        this.directed = directed;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Yangi tugun (vertex) qo'shish.
     * 
     * QANDAY ISHLAYDI?
     * Agar tugun mavjud bo'lmasa, yangi ro'yxat yaratamiz.
     * 
     * TEZLIK: O(1) amortizatsiya
     * 
     * @param vertex - qo'shiladigan tugun nomi
     */
    public void addVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            // Yangi tugun uchun bo'sh ro'yxat yaratamiz
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * Ikki tugun orasiga chiziq (edge) qo'shish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Agar yo'nali bo'lsa, faqat bitta tomonga qo'shamiz
     * 2. Agar yo'nalimsiz bo'lsa, ikki tomonga qo'shamiz
     * 
     * MISOL:
     * addEdge("A", "B"):
     * - Yo'nali: A -> [B]
     * - Yo'nalimsiz: A -> [B], B -> [A]
     * 
     * TEZLIK: O(1)
     * 
     * @param source - boshlang'ich tugun
     * @param destination - maqsad tugun
     */
    public void addEdge(String source, String destination) {
        // Ikkala tugunni ham mavjudligini ta'minlaymiz
        addVertex(source);
        addVertex(destination);
        
        // Boshlang'ich tugunga maqsad tugunni qo'shamiz
        adjacencyList.get(source).add(destination);
        
        // Agar yo'nalimsiz bo'lsa, teskari tomonga ham qo'shamiz
        if (!directed) {
            adjacencyList.get(destination).add(source);
        }
    }

    /**
     * Tugunni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Tugunning o'zini o'chiramiz
     * 2. Boshqa tugunlardan ham bu tugunni o'chiramiz
     * 
     * TEZLIK: O(V + E)
     * 
     * @param vertex - o'chiriladigan tugun
     */
    public void removeVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) return;
        
        // Boshqa tugunlardan bu tugunni o'chiramiz
        for (String other : adjacencyList.keySet()) {
            adjacencyList.get(other).remove(vertex);
        }
        
        // Tugunning o'zini o'chiramiz
        adjacencyList.remove(vertex);
    }

    /**
     * Chiziqni o'chirish.
     * 
     * @param source - boshlang'ich tugun
     * @param destination - maqsad tugun
     */
    public void removeEdge(String source, String destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
        if (!directed && adjacencyList.containsKey(destination)) {
            adjacencyList.get(destination).remove(source);
        }
    }

    // ==================== QIDIRISH ALGORITMLARI ====================
    
    /**
     * BFS - Kenglikka qidirish (Breadth-First Search).
     * 
     * QANDAY ISHLAYDI?
     * 1. Boshlang'ich tugundan boshlaymiz
     * 2. Qo'shni tugunlarni navbatga (queue) qo'shamiz
     * 3. Har bir tugunni ziyorat qilamiz
     * 4. Ko'rilgan tugunlarni saqlab qoramiz
     * 
     * MISOL:
     *        A
     *       / \
     *      B   C
     *     /   / \
     *    D   E   F
     * 
     * BFS: A -> B -> C -> D -> E -> F
     * (Darajadan darajaga - yaqinlari birinchi)
     * 
     * TEZLIK: O(V + E)
     * 
     * @param start - boshlang'ich tugun
     * @return BFS tartibida tugunlar
     */
    public List<String> bfs(String start) {
        List<String> visited = new ArrayList<>();     // Ziyorat qilinganlar
        Queue<String> queue = new LinkedList<>();     // Navbat
        
        // 1-QADAM: Boshlang'ich tugunni navbatga qo'shamiz
        queue.add(start);
        visited.add(start);
        
        // 2-QADAM: Navbat bo'shmaguncha davom ettiramiz
        while (!queue.isEmpty()) {
            // Navbatdan birinchi tugunni olamiz
            String current = queue.poll();
            
            // Joriy tugunning barcha qo'shnilarini ko'rib chiqamiz
            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                // Agar qo'shni hali ko'rilmagan bo'lsa
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);      // Ziyorat qilinganlar ro'yxatiga qo'shamiz
                    queue.add(neighbor);        // Navbatga qo'shamiz
                }
            }
        }
        
        return visited;
    }

    /**
     * DFS - Chuqurlikka qidirish (Depth-First Search).
     * 
     * QANDAY ISHLAYDI?
     * 1. Boshlang'ich tugundan boshlaymiz
     * 2. Birinchi qo'shniga boramiz
     * 3. Uning qo'shnilariga boramiz (chuqurroq)
     * 4. Orqaga qaytib, boshqa yo'lni sinab ko'ramiz
     * 
     * MISOL:
     *        A
     *       / \
     *      B   C
     *     /   / \
     *    D   E   F
     * 
     * DFS: A -> B -> D -> C -> E -> F
     * (Chuqurroq - bir yo'lni to'liq bosib o'tish)
     * 
     * TEZLIK: O(V + E)
     * 
     * @param start - boshlang'ich tugun
     * @return DFS tartibida tugunlar
     */
    public List<String> dfs(String start) {
        List<String> visited = new ArrayList<>();
        dfsHelper(start, visited);
        return visited;
    }

    /**
     * DFS yordamchi funksiyasi (recursion).
     * 
     * @param current - joriy tugun
     * @param visited - ziyorat qilingan tugunlar
     */
    private void dfsHelper(String current, List<String> visited) {
        // 1-QADAM: Joriy tugunni ziyorat qilinganlar ro'yxatiga qo'shamiz
        visited.add(current);
        
        // 2-QADAM: Barcha qo'shnilarini ko'rib chiqamiz
        for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
            // Agar qo'shni hali ko'rilmagan bo'lsa
            if (!visited.contains(neighbor)) {
                // Recursion bilan chuqurroq boramiz
                dfsHelper(neighbor, visited);
            }
        }
    }

    /**
     * Ikki tugun orasida yo'l borligini tekshirish.
     * 
     * @param start - boshlang'ich tugun
     * @param end - maqsad tugun
     * @return true - yo'l bor, false - yo'l yo'q
     */
    public boolean hasPath(String start, String end) {
        return bfs(start).contains(end);
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Tugunning qo'shnilarini qaytarish.
     * 
     * @param vertex - tugun nomi
     * @return qo'shnilar ro'yxati
     */
    public List<String> getNeighbors(String vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Tugunlar sonini qaytarish.
     */
    public int vertexCount() {
        return adjacencyList.size();
    }

    /**
     * Chiziqlar sonini qaytarish.
     * 
     * @return chiziqlar soni
     */
    public int edgeCount() {
        int count = 0;
        for (List<String> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }
        return directed ? count : count / 2; // Yo'nalimsiz graf uchun 2 ga bo'lamiz
    }

    /**
     * Graf bo'shligini tekshirish.
     */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    /**
     * Grafni matn ko'rinishida qaytarish.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph (").append(directed ? "Yo'nali" : "Yo'nalimsiz").append("):\n");
        
        for (String vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(" -> ").append(adjacencyList.get(vertex)).append("\n");
        }
        
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Graph Sinov ===\n");
        
        // Yo'nalimsiz graf yaratamiz
        Graph graph = new Graph(false);
        
        // Tugunlar qo'shamiz
        System.out.println("--- Graf yaratish ---");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");
        graph.addEdge("C", "F");
        graph.addEdge("D", "F");
        
        System.out.println(graph);
        
        // BFS
        System.out.println("--- BFS (Kenglikka qidirish) ---");
        System.out.println("BFS A dan: " + graph.bfs("A"));
        
        // DFS
        System.out.println("\n--- DFS (Chuqurlikka qidirish) ---");
        System.out.println("DFS A dan: " + graph.dfs("A"));
        
        // Yo'l tekshirish
        System.out.println("\n--- Yo'l tekshirish ---");
        System.out.println("A dan F ga yo'l bormi: " + graph.hasPath("A", "F"));
        System.out.println("A dan X ga yo'l bormi: " + graph.hasPath("A", "X"));
        
        // Graf ma'lumotlari
        System.out.println("\n--- Graf ma'lumotlari ---");
        System.out.println("Tugunlar soni: " + graph.vertexCount());
        System.out.println("Chiziqlar soni: " + graph.edgeCount());
    }
}