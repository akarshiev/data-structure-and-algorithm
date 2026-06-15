package datastructures.linkedlist;

import java.util.NoSuchElementException;

/**
 * SinglyLinkedList - A generic singly linked list.
 *
 * <p>Each element (Node) stores a value and a reference to the next Node.
 * Unlike arrays, insertions and deletions at the head are O(1) because
 * no element shifting is required.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>addFirst / removeFirst : O(1)</li>
 *   <li>addLast                : O(n)  — no tail pointer</li>
 *   <li>addAt / removeAt       : O(n)</li>
 *   <li>get / indexOf          : O(n)</li>
 * </ul>
 *
 * @param <T> the type of elements held in this list
 */
public class SinglyLinkedList<T> {

    // ── Inner Node ────────────────────────────────────────────────────────────

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private Node<T> head;
    private int size;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Creates an empty linked list. */
    public SinglyLinkedList() {}

    // ── Insertion ─────────────────────────────────────────────────────────────

    /**
     * Inserts {@code element} at the beginning of the list. O(1).
     *
     * @param element the element to add
     */
    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = head;
        head = newNode;
        size++;
    }

    /**
     * Appends {@code element} at the end of the list. O(n).
     *
     * @param element the element to add
     */
    public void addLast(T element) {
        if (head == null) {
            addFirst(element);
            return;
        }
        Node<T> newNode = new Node<>(element);
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        size++;
    }

    /**
     * Inserts {@code element} at the specified index. O(n).
     *
     * @param index   position at which to insert (0-based)
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > size}
     */
    public void addAt(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index + ", size: " + size);
        }
        if (index == 0) {
            addFirst(element);
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        Node<T> newNode = new Node<>(element);
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    // ── Removal ───────────────────────────────────────────────────────────────

    /**
     * Removes and returns the first element. O(1).
     *
     * @return the removed element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    /**
     * Removes and returns the element at the specified index. O(n).
     *
     * @param index the position of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    public T removeAt(int index) {
        checkIndex(index);
        if (index == 0) return removeFirst();

        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        T data = current.next.data;
        current.next = current.next.next;
        size--;
        return data;
    }

    /**
     * Removes the first occurrence of {@code element}. O(n).
     *
     * @param element element to remove
     * @return {@code true} if the element was found and removed
     */
    public boolean remove(T element) {
        if (isEmpty()) return false;

        if (head.data.equals(element)) {
            removeFirst();
            return true;
        }
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // ── Search ────────────────────────────────────────────────────────────────

    /**
     * Returns the element at the specified index. O(n).
     *
     * @param index index of the element to return
     * @return the element at {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the index of the first occurrence of {@code element}, or -1.
     *
     * @param element the element to search for
     * @return index of first occurrence, or -1
     */
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    /** Returns {@code true} if this list contains {@code element}. */
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /** Reverses the list in place. O(n). */
    public void reverse() {
        Node<T> prev = null;
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    /** Returns {@code true} if this list contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements in this list. */
    public int size() {
        return size;
    }

    /** Removes all elements. */
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" -> ");
            current = current.next;
        }
        return sb.append("]").toString();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index + ", size: " + size);
        }
    }
}
