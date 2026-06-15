# Data Structures & Algorithms

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)

A clean, well-documented implementation of fundamental data structures and algorithms in Java, built from scratch without any external libraries.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Data Structures](#data-structures)
- [Algorithms](#algorithms)
- [Complexity Summary](#complexity-summary)
- [Running the Tests](#running-the-tests)
- [Contributing](#contributing)
- [Resources](#resources)

---

## Project Structure

```
data-structure-and-algorithm/
│
├── src/
│   ├── main/java/
│   │   ├── datastructures/
│   │   │   ├── array/          CustomArray.java
│   │   │   ├── linkedlist/     SinglyLinkedList.java
│   │   │   ├── stack/          Stack.java
│   │   │   ├── queue/          Queue.java
│   │   │   ├── hashmap/        CustomHashMap.java
│   │   │   ├── tree/           BinarySearchTree.java
│   │   │   ├── heap/           MaxHeap.java
│   │   │   └── graph/          Graph.java
│   │   │
│   │   └── algorithms/
│   │       ├── sorting/        SortingAlgorithms.java
│   │       ├── searching/      SearchingAlgorithms.java
│   │       ├── recursion/      RecursionExamples.java
│   │       ├── dynamicprogramming/  DynamicProgrammingExamples.java
│   │       ├── greedy/         GreedyExamples.java
│   │       └── graphalgorithms/ GraphAlgorithms.java
│   │
│   └── test/java/
│       ├── datastructures/     DataStructuresTest.java
│       └── algorithms/         AlgorithmsTest.java
│
├── docs/
│   ├── complexity-analysis.md
│   ├── big-o-notation.md
│   └── interview-notes.md
│
└── README.md
```

---

## Getting Started

**Compile:**
```bash
javac -sourcepath src/main/java -d out $(find src/main/java -name "*.java")
```

**Run tests:**
```bash
javac -ea -sourcepath src/main/java:src/test/java -d out $(find src -name "*.java")
java -ea -cp out datastructures.DataStructuresTest
java -ea -cp out algorithms.AlgorithmsTest
```

---

## Data Structures

### CustomArray `datastructures.array`
A generic dynamic array (similar to `ArrayList`) that doubles capacity when full.

```java
CustomArray<Integer> arr = new CustomArray<>();
arr.add(10);
arr.add(20);
int value = arr.get(0); // 10
arr.remove(0);
```

| Operation | Complexity |
|-----------|-----------|
| `get` / `set` | O(1) |
| `add` (end) | O(1) amortized |
| `remove` | O(n) |
| `indexOf` | O(n) |

---

### SinglyLinkedList `datastructures.linkedlist`
A generic singly-linked list with head insertion in O(1).

```java
SinglyLinkedList<String> list = new SinglyLinkedList<>();
list.addFirst("B");
list.addFirst("A");
list.addLast("C");
list.reverse();
```

| Operation | Complexity |
|-----------|-----------|
| `addFirst` / `removeFirst` | O(1) |
| `addLast` | O(n) |
| `get` / `indexOf` | O(n) |

---

### Stack `datastructures.stack`
A generic LIFO stack backed by a linked list. All operations are O(1).

```java
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.push(2);
int top = stack.pop();  // 2
int peek = stack.peek(); // 1
```

**Uses:** expression evaluation, undo/redo, DFS, backtracking.

---

### Queue `datastructures.queue`
A generic FIFO queue backed by a linked list with O(1) enqueue and dequeue.

```java
Queue<String> queue = new Queue<>();
queue.enqueue("first");
queue.enqueue("second");
String front = queue.dequeue(); // "first"
```

**Uses:** BFS, task scheduling, message queues.

---

### CustomHashMap `datastructures.hashmap`
A generic hash map using separate chaining. Resizes when the load factor exceeds 0.75.

```java
CustomHashMap<String, Integer> map = new CustomHashMap<>();
map.put("Alice", 30);
int age = map.get("Alice"); // 30
map.remove("Alice");
```

| Operation | Average | Worst |
|-----------|---------|-------|
| `put` / `get` / `remove` | O(1) | O(n) |

---

### BinarySearchTree `datastructures.tree`
An integer BST with recursive insert, search, delete, and three traversal orders.

```java
BinarySearchTree bst = new BinarySearchTree();
bst.insert(50); bst.insert(30); bst.insert(70);
boolean found = bst.search(30); // true
bst.delete(30);
bst.inOrder(); // prints sorted order
```

| Operation | Average | Worst |
|-----------|---------|-------|
| `insert` / `search` / `delete` | O(log n) | O(n) |

---

### MaxHeap `datastructures.heap`
An integer max-heap stored in a resizable array. The maximum element is always at the root.

```java
MaxHeap heap = new MaxHeap();
heap.insert(10); heap.insert(50); heap.insert(30);
int max = heap.extractMax(); // 50
```

| Operation | Complexity |
|-----------|-----------|
| `peek` | O(1) |
| `insert` / `extractMax` | O(log n) |

**Uses:** priority queues, heap sort, top-K problems.

---

### Graph `datastructures.graph`
An undirected or directed graph using an adjacency-list representation, with BFS and DFS.

```java
Graph g = new Graph(false); // undirected
g.addEdge("A", "B");
g.addEdge("B", "C");

List<String> bfsOrder = g.bfs("A"); // [A, B, C]
boolean connected = g.hasPath("A", "C"); // true
```

---

## Algorithms

### Sorting `algorithms.sorting`

All methods sort in ascending order and operate in place.

```java
int[] arr = {64, 34, 25, 12, 22, 11, 90};

SortingAlgorithms.bubbleSort(arr);
SortingAlgorithms.mergeSort(arr);
SortingAlgorithms.quickSort(arr, 0, arr.length - 1);
```

| Algorithm | Best | Average | Worst | Space | Stable |
|-----------|------|---------|-------|-------|--------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | No |

---

### Searching `algorithms.searching`

```java
int[] sorted = {2, 5, 8, 12, 16, 23, 38};
int idx = SearchingAlgorithms.binarySearch(sorted, 12); // 3
```

| Algorithm | Time | Precondition |
|-----------|------|--------------|
| Linear Search | O(n) | None |
| Binary Search | O(log n) | Sorted |
| Jump Search | O(√n) | Sorted |
| Interpolation Search | O(log log n)* | Sorted, uniform |

---

### Recursion `algorithms.recursion`

Classic recursive problems with memoization variants.

```java
long fact  = RecursionExamples.factorial(10);       // 3628800
long fib   = RecursionExamples.fibonacciMemo(50);   // 12586269025
long pow   = RecursionExamples.fastPower(2, 30);    // 1073741824
String rev = RecursionExamples.reverseString("hello"); // "olleh"
RecursionExamples.towerOfHanoi(3, 'A', 'C', 'B');
```

---

### Dynamic Programming `algorithms.dynamicprogramming`

| Problem | Method | Complexity |
|---------|--------|-----------|
| Fibonacci | `fibonacciTabulation` | O(n) time, O(n) space |
| Coin Change | `coinChange` | O(amount × coins) |
| 0/1 Knapsack | `knapsack` | O(n × capacity) |
| LCS | `longestCommonSubsequence` | O(m × n) |
| LIS | `longestIncreasingSubsequence` | O(n²) |
| Edit Distance | `editDistance` | O(m × n) |

---

### Greedy `algorithms.greedy`

```java
// Fractional Knapsack
double maxValue = GreedyExamples.fractionalKnapsack(weights, values, capacity);

// Activity Selection (maximum non-overlapping activities)
int count = GreedyExamples.activitySelection(startTimes, finishTimes);

// Job Scheduling (maximum profit)
int profit = GreedyExamples.jobScheduling(profits, deadlines);
```

---

### Graph Algorithms `algorithms.graphalgorithms`

```java
GraphAlgorithms.WeightedGraph g = new GraphAlgorithms.WeightedGraph();
g.addEdge("A", "B", 4);
g.addEdge("A", "C", 2);
g.addEdge("C", "B", 1);

// Dijkstra (non-negative weights)
Map<String, Integer> dist = GraphAlgorithms.dijkstra(g, "A");

// Bellman-Ford (handles negative weights)
Map<String, Integer> dist2 = GraphAlgorithms.bellmanFord(g, "A", 3);

// Topological Sort (DAG only)
List<String> order = GraphAlgorithms.topologicalSort(dag);

// Cycle Detection
boolean cyclic = GraphAlgorithms.hasCycle(directedGraph);
```

| Algorithm | Time |
|-----------|------|
| Dijkstra | O((V + E) log V) |
| Bellman-Ford | O(V × E) |
| Topological Sort | O(V + E) |
| Cycle Detection | O(V + E) |

---

## Running the Tests

The test files in `src/test/java/` use Java assertions (`assert`) for lightweight verification without external frameworks.

```bash
# Compile everything
javac -ea -sourcepath src/main/java:src/test/java -d out \
      $(find src -name "*.java")

# Run data structure tests
java -ea -cp out datastructures.DataStructuresTest

# Run algorithm tests
java -ea -cp out algorithms.AlgorithmsTest
```

Expected output:
```
--- CustomArray ---
  PASS
--- SinglyLinkedList ---
  PASS
...
✅ All tests passed.
```

---

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/add-avl-tree`
3. Commit with conventional format: `git commit -m "feat: add AVL tree with rotation"`
4. Push and open a Pull Request

**Commit message conventions:**

| Prefix | When to use |
|--------|------------|
| `feat:` | New data structure or algorithm |
| `fix:` | Bug fix in existing implementation |
| `docs:` | README or Javadoc update |
| `refactor:` | Code cleanup with no behaviour change |
| `test:` | Adding or updating tests |

**Ideas for contribution:**
- Go / Python implementations (mirror the `java/` layout)
- Doubly linked list, deque, trie, segment tree
- AVL tree / Red-Black tree
- A\* search, Floyd-Warshall, Prim's / Kruskal's MST
- LeetCode problem solutions linked to the relevant data structure

---

## Resources

- [Big-O Cheat Sheet](https://www.bigocheatsheet.com/)
- [Visualgo — Algorithm Visualizations](https://visualgo.net/)
- [LeetCode](https://leetcode.com/)
- [GeeksforGeeks DSA](https://www.geeksforgeeks.org/data-structures/)
- [Algorithms, Part I & II — Princeton (Coursera)](https://www.coursera.org/learn/algorithms-part1)

---

## License

This project is licensed under the [MIT License](LICENSE).
