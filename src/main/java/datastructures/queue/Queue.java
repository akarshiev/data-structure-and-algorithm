package datastructures.queue;

import java.util.NoSuchElementException;

/**
 * Queue - A generic FIFO (First-In, First-Out) queue backed by a singly linked list.
 *
 * <p>Maintaining both a {@code front} and a {@code rear} pointer allows
 * O(1) enqueue (at the rear) and O(1) dequeue (from the front).
 *
 * <p><b>Real-world uses:</b> task scheduling, print spoolers, BFS traversal,
 * message queues in distributed systems.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>enqueue / dequeue / peek : O(1)</li>
 * </ul>
 *
 * @param <T> the type of elements held in this queue
 */
public class Queue<T> {

    // ── Inner Node ────────────────────────────────────────────────────────────

    private static class Node<E> {
        final E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    /** First element — dequeue happens here. */
    private Node<T> front;

    /**
     * Last element — enqueue happens here.
     * Without a rear pointer, appending would cost O(n).
     */
    private Node<T> rear;

    private int size;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Creates an empty queue. */
    public Queue() {}

    // ── Core operations ───────────────────────────────────────────────────────

    /**
     * Adds {@code element} to the rear of this queue. O(1).
     *
     * @param element the element to add
     */
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the front element. O(1).
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T data = front.data;
        front = front.next;
        if (front == null) rear = null; // queue is now empty
        size--;
        return data;
    }

    /**
     * Returns (without removing) the front element. O(1).
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return front.data;
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /** Returns {@code true} if this queue contains no elements. */
    public boolean isEmpty() {
        return front == null;
    }

    /** Returns the number of elements in this queue. */
    public int size() {
        return size;
    }

    /** Removes all elements. */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[front -> ");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        return sb.append(" <- rear]").toString();
    }
}
