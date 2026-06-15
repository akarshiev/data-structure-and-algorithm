package datastructures.array;

import java.util.NoSuchElementException;

/**
 * CustomArray - A generic dynamic array similar to Java's ArrayList.
 *
 * <p>When a fixed-size array fills up, this implementation automatically
 * doubles its capacity and copies all elements to the new array.
 * This "growth strategy" yields O(1) amortized time for {@code add()}.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>get / set  : O(1)</li>
 *   <li>add (end)  : O(1) amortized</li>
 *   <li>remove     : O(n)</li>
 *   <li>indexOf    : O(n)</li>
 * </ul>
 *
 * @param <T> the type of elements held in this array
 */
public class CustomArray<T> {

    // ── Fields ──────────────────────────────────────────────────────────────

    private static final int DEFAULT_CAPACITY = 10;

    /** Raw backing store. We use Object[] because Java forbids generic arrays. */
    private Object[] data;

    /** Number of elements currently stored (always <= data.length). */
    private int size;

    // ── Constructors ─────────────────────────────────────────────────────────

    /** Creates an empty array with default initial capacity (10). */
    public CustomArray() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates an empty array with the given initial capacity.
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     */
    public CustomArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Initial capacity cannot be negative: " + initialCapacity);
        }
        this.data = new Object[Math.max(initialCapacity, 1)];
        this.size = 0;
    }

    // ── Core operations ──────────────────────────────────────────────────────

    /**
     * Appends {@code element} to the end.
     *
     * @param element the element to add
     */
    public void add(T element) {
        ensureCapacity();
        data[size++] = element;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index index of the element to return
     * @return the element at {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    /**
     * Replaces the element at the specified index.
     *
     * @param index   index of the element to replace
     * @param element new value
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    public void set(int index, T element) {
        checkIndex(index);
        data[index] = element;
    }

    /**
     * Removes and returns the element at the specified index.
     * Shifts all subsequent elements one position to the left.
     *
     * @param index index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];

        int toShift = size - index - 1;
        if (toShift > 0) {
            System.arraycopy(data, index + 1, data, index, toShift);
        }
        data[--size] = null; // allow GC to collect the removed object

        // Shrink backing array when it is only 25% full (but keep minimum capacity)
        if (size > 0 && size == data.length / 4 && data.length / 2 >= DEFAULT_CAPACITY) {
            resize(data.length / 2);
        }
        return removed;
    }

    // ── Search ───────────────────────────────────────────────────────────────

    /**
     * Returns the index of the first occurrence of {@code element},
     * or -1 if not found.
     *
     * @param element the element to search for
     * @return index of first occurrence, or -1
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element == null ? data[i] == null : element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns {@code true} if this array contains {@code element}.
     *
     * @param element element whose presence is to be tested
     * @return {@code true} if element is present
     */
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    // ── Utility ──────────────────────────────────────────────────────────────

    /** Returns {@code true} if this array contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements in this array. */
    public int size() {
        return size;
    }

    /** Returns the current capacity of the backing array. */
    public int capacity() {
        return data.length;
    }

    /** Removes all elements. The backing array is reset to default capacity. */
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null; // release object references for GC
        }
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void ensureCapacity() {
        if (size == data.length) {
            resize(data.length == 0 ? DEFAULT_CAPACITY : data.length * 2);
        }
    }

    private void resize(int newCapacity) {
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index + ", size: " + size);
        }
    }
}
