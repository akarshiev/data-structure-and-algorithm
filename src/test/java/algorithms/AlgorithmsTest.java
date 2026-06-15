package algorithms;

import algorithms.sorting.SortingAlgorithms;
import algorithms.searching.SearchingAlgorithms;
import algorithms.recursion.RecursionExamples;
import algorithms.dynamicprogramming.DynamicProgrammingExamples;
import algorithms.greedy.GreedyExamples;
import algorithms.graphalgorithms.GraphAlgorithms;

import java.util.*;

/**
 * Manual smoke-tests for all algorithm implementations.
 *
 * <p>Run with:
 * <pre>
 *   javac -sourcepath src/main/java -d out src/test/java/algorithms/AlgorithmsTest.java
 *   java -cp out algorithms.AlgorithmsTest
 * </pre>
 */
public class AlgorithmsTest {

    public static void main(String[] args) {
        testSorting();
        testSearching();
        testRecursion();
        testDynamicProgramming();
        testGreedy();
        testGraphAlgorithms();
        System.out.println("\n✅ All tests passed.");
    }

    // ── Sorting ───────────────────────────────────────────────────────────────

    static void testSorting() {
        System.out.println("--- Sorting ---");
        int[] original = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};

        int[] arr;

        arr = original.clone(); SortingAlgorithms.bubbleSort(arr);
        assert Arrays.equals(arr, expected) : "Bubble sort failed";

        arr = original.clone(); SortingAlgorithms.selectionSort(arr);
        assert Arrays.equals(arr, expected) : "Selection sort failed";

        arr = original.clone(); SortingAlgorithms.insertionSort(arr);
        assert Arrays.equals(arr, expected) : "Insertion sort failed";

        arr = original.clone(); SortingAlgorithms.mergeSort(arr);
        assert Arrays.equals(arr, expected) : "Merge sort failed";

        arr = original.clone(); SortingAlgorithms.quickSort(arr, 0, arr.length - 1);
        assert Arrays.equals(arr, expected) : "Quick sort failed";

        System.out.println("  PASS");
    }

    // ── Searching ─────────────────────────────────────────────────────────────

    static void testSearching() {
        System.out.println("--- Searching ---");
        int[] sorted   = {2, 5, 8, 12, 16, 23, 38, 45, 67, 89};
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};

        assert SearchingAlgorithms.linearSearch(unsorted, 22) == 4  : "Linear search failed";
        assert SearchingAlgorithms.linearSearch(unsorted, 99) == -1 : "Linear search (miss) failed";

        assert SearchingAlgorithms.binarySearch(sorted, 23) == 5    : "Binary search failed";
        assert SearchingAlgorithms.binarySearch(sorted, 99) == -1   : "Binary search (miss) failed";

        assert SearchingAlgorithms.binarySearchRecursive(sorted, 67, 0, sorted.length - 1) == 8
            : "Recursive binary search failed";

        assert SearchingAlgorithms.jumpSearch(sorted, 38) == 6      : "Jump search failed";

        assert SearchingAlgorithms.interpolationSearch(sorted, 45) == 7
            : "Interpolation search failed";

        System.out.println("  PASS");
    }

    // ── Recursion ─────────────────────────────────────────────────────────────

    static void testRecursion() {
        System.out.println("--- Recursion ---");
        assert RecursionExamples.factorial(5)       == 120   : "Factorial failed";
        assert RecursionExamples.factorial(0)       == 1     : "Factorial(0) failed";
        assert RecursionExamples.fibonacci(10)      == 55    : "Fibonacci failed";
        assert RecursionExamples.fibonacciMemo(50)  == 12586269025L : "Fibonacci memo failed";
        assert RecursionExamples.power(2, 10)       == 1024  : "Power failed";
        assert RecursionExamples.fastPower(2, 10)   == 1024  : "Fast power failed";
        assert RecursionExamples.reverseString("hello").equals("olleh") : "Reverse string failed";
        System.out.println("  PASS");
    }

    // ── Dynamic Programming ───────────────────────────────────────────────────

    static void testDynamicProgramming() {
        System.out.println("--- Dynamic Programming ---");
        assert DynamicProgrammingExamples.fibonacciTabulation(10) == 55 : "Fibonacci DP failed";

        int[] coins  = {1, 5, 10, 25};
        assert DynamicProgrammingExamples.coinChange(coins, 30) == 2 : "Coin change failed";
        assert DynamicProgrammingExamples.coinChange(coins, 11) == 2 : "Coin change (11) failed";

        int[] weights = {1, 3, 4, 5};
        int[] values  = {1, 4, 5, 7};
        assert DynamicProgrammingExamples.knapsack(weights, values, 7) == 9 : "Knapsack failed";

        assert DynamicProgrammingExamples.longestCommonSubsequence("ABCBDAB", "BDCAB") == 4
            : "LCS failed";

        int[] lis = {10, 9, 2, 5, 3, 7, 101, 18};
        assert DynamicProgrammingExamples.longestIncreasingSubsequence(lis) == 4 : "LIS failed";

        assert DynamicProgrammingExamples.editDistance("kitten", "sitting") == 3
            : "Edit distance failed";

        System.out.println("  PASS");
    }

    // ── Greedy ────────────────────────────────────────────────────────────────

    static void testGreedy() {
        System.out.println("--- Greedy ---");
        int[] w = {10, 20, 30}, v = {60, 100, 120};
        double fk = GreedyExamples.fractionalKnapsack(w, v, 50);
        assert Math.abs(fk - 240.0) < 0.001 : "Fractional knapsack failed: " + fk;

        int[] start  = {1, 3, 0, 5, 8, 5};
        int[] finish = {2, 4, 6, 7, 9, 9};
        assert GreedyExamples.activitySelection(start, finish) == 4
            : "Activity selection failed";

        System.out.println("  PASS");
    }

    // ── Graph Algorithms ──────────────────────────────────────────────────────

    static void testGraphAlgorithms() {
        System.out.println("--- Graph Algorithms ---");

        GraphAlgorithms.WeightedGraph g = new GraphAlgorithms.WeightedGraph();
        g.addEdge("A", "B", 1);
        g.addEdge("A", "C", 4);
        g.addEdge("B", "D", 2);
        g.addEdge("C", "D", 3);

        Map<String, Integer> dijk = GraphAlgorithms.dijkstra(g, "A");
        assert dijk.get("A") == 0 : "Dijkstra A→A should be 0";
        assert dijk.get("B") == 1 : "Dijkstra A→B should be 1";
        assert dijk.get("D") == 3 : "Dijkstra A→D should be 3";

        Map<String, Integer> bf = GraphAlgorithms.bellmanFord(g, "A", 4);
        assert bf != null         : "Bellman-Ford returned null (false negative cycle)";
        assert bf.get("D") == 3  : "Bellman-Ford A→D should be 3";

        // Topological sort
        Map<String, List<String>> dag = new HashMap<>();
        dag.put("A", Arrays.asList("B", "C"));
        dag.put("B", Arrays.asList("D"));
        dag.put("C", Arrays.asList("D"));
        dag.put("D", new ArrayList<>());
        List<String> topo = GraphAlgorithms.topologicalSort(dag);
        assert topo.indexOf("A") < topo.indexOf("B") : "Topo: A should come before B";
        assert topo.indexOf("B") < topo.indexOf("D") : "Topo: B should come before D";

        // Cycle detection
        Map<String, List<String>> cyclic = new HashMap<>();
        cyclic.put("A", Arrays.asList("B"));
        cyclic.put("B", Arrays.asList("C"));
        cyclic.put("C", Arrays.asList("A"));
        assert GraphAlgorithms.hasCycle(cyclic) : "Should detect cycle";

        Map<String, List<String>> acyclic = new HashMap<>();
        acyclic.put("A", Arrays.asList("B"));
        acyclic.put("B", Arrays.asList("C"));
        acyclic.put("C", new ArrayList<>());
        assert !GraphAlgorithms.hasCycle(acyclic) : "Should not detect cycle in DAG";

        System.out.println("  PASS");
    }
}
