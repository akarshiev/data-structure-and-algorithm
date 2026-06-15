package datastructures.stack;

import java.util.EmptyStackException;

/**
 * Stack - A generic LIFO (Last-In, First-Out) stack backed by a singly linked list.
 *
 * <p>Because insertion and removal happen at the head of the linked list,
 * all core operations run in O(1) time and O(1) extra space.
 *
 * <p><b>Real-world uses:</b> browser back-button history, undo/redo systems,
 * expression evaluation, call-stack simulation.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>push / pop / peek : O(1)</li>
 * </ul>
 *
 * @param <T> the type of elements held in this stack
 */
public class Stack<T> {

    // ── Inner Node ────────────────────────────────────────────────────────────

    private static class Node<E> {
        final E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private Node<T> top;
    private int size;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Creates an empty stack. */
    public Stack() {}

    // ── Core operations ───────────────────────────────────────────────────────

    /**
     * Pushes {@code element} onto the top of this stack. O(1).
     *
     * @param element the element to push
     */
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top element. O(1).
     *
     * @return the top element
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Returns (without removing) the top element. O(1).
     *
     * @return the top element
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /** Returns {@code true} if this stack contains no elements. */
    public boolean isEmpty() {
        return top == null;
    }

    /** Returns the number of elements in this stack. */
    public int size() {
        return size;
    }

    /** Removes all elements. */
    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[top -> ");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        return sb.append(" <- bottom]").toString();
    }
}
