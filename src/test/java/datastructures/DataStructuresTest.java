package datastructures;

import datastructures.array.CustomArray;
import datastructures.linkedlist.SinglyLinkedList;
import datastructures.stack.Stack;
import datastructures.queue.Queue;
import datastructures.hashmap.CustomHashMap;
import datastructures.tree.BinarySearchTree;
import datastructures.heap.MaxHeap;
import datastructures.graph.Graph;

/**
 * Manual smoke-tests for all data structure implementations.
 *
 * <p>Run with:
 * <pre>
 *   javac -sourcepath src/main/java -d out src/test/java/datastructures/DataStructuresTest.java
 *   java -cp out datastructures.DataStructuresTest
 * </pre>
 *
 * <p>For a real project, replace these with JUnit 5 tests.
 */
public class DataStructuresTest {

    public static void main(String[] args) {
        testCustomArray();
        testLinkedList();
        testStack();
        testQueue();
        testHashMap();
        testBST();
        testMaxHeap();
        testGraph();
        System.out.println("\n✅ All tests passed.");
    }

    // ── CustomArray ───────────────────────────────────────────────────────────

    static void testCustomArray() {
        System.out.println("--- CustomArray ---");
        CustomArray<Integer> arr = new CustomArray<>();
        assert arr.isEmpty()     : "Should be empty";

        for (int i = 1; i <= 5; i++) arr.add(i);
        assert arr.size() == 5   : "Size should be 5";
        assert arr.get(0) == 1   : "First element should be 1";
        assert arr.contains(3)   : "Should contain 3";
        assert !arr.contains(99) : "Should not contain 99";

        arr.remove(0);
        assert arr.get(0) == 2   : "After remove, first element should be 2";
        System.out.println("  PASS");
    }

    // ── SinglyLinkedList ──────────────────────────────────────────────────────

    static void testLinkedList() {
        System.out.println("--- SinglyLinkedList ---");
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(4);

        assert list.size() == 4  : "Size should be 4";
        assert list.get(0) == 1  : "Head should be 1";
        assert list.get(3) == 4  : "Tail should be 4";

        list.reverse();
        assert list.get(0) == 4  : "After reverse, head should be 4";
        System.out.println("  PASS");
    }

    // ── Stack ─────────────────────────────────────────────────────────────────

    static void testStack() {
        System.out.println("--- Stack ---");
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assert stack.peek().equals("C") : "Peek should return C";
        assert stack.pop().equals("C")  : "Pop should return C";
        assert stack.size() == 2        : "Size should be 2";
        System.out.println("  PASS");
    }

    // ── Queue ─────────────────────────────────────────────────────────────────

    static void testQueue() {
        System.out.println("--- Queue ---");
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assert queue.peek() == 1    : "Peek should return 1 (FIFO)";
        assert queue.dequeue() == 1 : "Dequeue should return 1";
        assert queue.size() == 2    : "Size should be 2";
        System.out.println("  PASS");
    }

    // ── CustomHashMap ─────────────────────────────────────────────────────────

    static void testHashMap() {
        System.out.println("--- CustomHashMap ---");
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Alice", 30);
        map.put("Bob",   25);

        assert map.get("Alice") == 30     : "Alice should be 30";
        assert map.containsKey("Bob")     : "Should contain Bob";
        assert !map.containsKey("Charlie"): "Should not contain Charlie";

        map.put("Alice", 31);
        assert map.get("Alice") == 31     : "Alice should be updated to 31";

        map.remove("Bob");
        assert !map.containsKey("Bob")    : "Bob should be removed";
        System.out.println("  PASS");
    }

    // ── BinarySearchTree ──────────────────────────────────────────────────────

    static void testBST() {
        System.out.println("--- BinarySearchTree ---");
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50); bst.insert(30); bst.insert(70);
        bst.insert(20); bst.insert(40);

        assert bst.search(30)  : "Should find 30";
        assert !bst.search(99) : "Should not find 99";
        assert bst.height() == 2 : "Height should be 2";

        bst.delete(30);
        assert !bst.search(30) : "30 should be deleted";
        System.out.println("  PASS");
    }

    // ── MaxHeap ───────────────────────────────────────────────────────────────

    static void testMaxHeap() {
        System.out.println("--- MaxHeap ---");
        MaxHeap heap = new MaxHeap(10);
        heap.insert(10); heap.insert(50); heap.insert(30);
        heap.insert(5);  heap.insert(80);

        assert heap.peek() == 80        : "Max should be 80";
        assert heap.extractMax() == 80  : "ExtractMax should return 80";
        assert heap.peek() == 50        : "New max should be 50";
        System.out.println("  PASS");
    }

    // ── Graph ─────────────────────────────────────────────────────────────────

    static void testGraph() {
        System.out.println("--- Graph ---");
        Graph g = new Graph(false);
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "D");

        assert g.hasPath("A", "D")  : "Should have path A→D";
        assert !g.hasPath("A", "X") : "Should not have path to unknown vertex X";

        java.util.List<String> bfs = g.bfs("A");
        assert bfs.get(0).equals("A") : "BFS should start at A";
        System.out.println("  PASS");
    }
}
