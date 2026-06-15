package datastructures.heap;

import java.util.NoSuchElementException;

/**
 * MaxHeap - A max-heap backed by a fixed-size integer array.
 *
 * <p>A max-heap is a complete binary tree where every node is ≥ its children.
 * The maximum element is always at the root, giving O(1) access.
 *
 * <p>The tree is stored compactly in an array.  For a node at index {@code i}:
 * <ul>
 *   <li>parent     : (i - 1) / 2</li>
 *   <li>left child : 2 * i + 1</li>
 *   <li>right child: 2 * i + 2</li>
 * </ul>
 *
 * <pre>
 * Array:  [80, 70, 60, 50, 40, 30, 20]
 * Tree:
 *         80
 *        /  \
 *      70    60
 *     / \   / \
 *   50  40 30  20
 * </pre>
 *
 * <p><b>Common uses:</b> priority queues, heap sort, top-K problems.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>insert      : O(log n)</li>
 *   <li>extractMax  : O(log n)</li>
 *   <li>peek (max)  : O(1)</li>
 * </ul>
 */
public class MaxHeap {

    // ── Fields ────────────────────────────────────────────────────────────────

    private int[] heap;
    private int size;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Creates a MaxHeap with the given initial capacity.
     * The heap grows automatically when full.
     *
     * @param initialCapacity initial backing array size
     * @throws IllegalArgumentException if capacity is non-positive
     */
    public MaxHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive: " + initialCapacity);
        }
        this.heap = new int[initialCapacity];
        this.size = 0;
    }

    /** Creates a MaxHeap with a default capacity of 16. */
    public MaxHeap() {
        this(16);
    }

    // ── Core operations ───────────────────────────────────────────────────────

    /**
     * Inserts {@code data} into this heap. O(log n).
     *
     * <p>The element is appended at the end, then "sifted up" until the
     * heap property is restored.
     *
     * @param data the value to insert
     */
    public void insert(int data) {
        if (size == heap.length) {
            grow();
        }
        heap[size] = data;
        siftUp(size);
        size++;
    }

    /**
     * Removes and returns the maximum element (the root). O(log n).
     *
     * <p>The last element replaces the root, then is "sifted down" until
     * the heap property is restored.
     *
     * @return the maximum value
     * @throws NoSuchElementException if the heap is empty
     */
    public int extractMax() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        int max = heap[0];
        heap[0] = heap[--size];
        siftDown(0);
        return max;
    }

    /**
     * Returns (without removing) the maximum element. O(1).
     *
     * @return the maximum value
     * @throws NoSuchElementException if the heap is empty
     */
    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /** Returns {@code true} if this heap contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements in this heap. */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /** Moves element at {@code i} upward until heap property holds. */
    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            if (heap[i] > heap[p]) {
                swap(i, p);
                i = p;
            } else {
                break;
            }
        }
    }

    /** Moves element at {@code i} downward until heap property holds. */
    private void siftDown(int i) {
        int largest = i;
        int left  = leftChild(i);
        int right = rightChild(i);

        if (left  < size && heap[left]  > heap[largest]) largest = left;
        if (right < size && heap[right] > heap[largest]) largest = right;

        if (largest != i) {
            swap(i, largest);
            siftDown(largest);
        }
    }

    private int parent(int i)     { return (i - 1) / 2; }
    private int leftChild(int i)  { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        int tmp  = heap[i];
        heap[i]  = heap[j];
        heap[j]  = tmp;
    }

    /** Doubles the backing array when capacity is exhausted. */
    private void grow() {
        int[] newHeap = new int[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }
}
