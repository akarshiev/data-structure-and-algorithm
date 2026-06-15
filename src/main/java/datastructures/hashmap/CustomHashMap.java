package datastructures.hashmap;

/**
 * CustomHashMap - A generic hash map using separate chaining for collision resolution.
 *
 * <p>A hash function maps each key to a bucket index.  When two keys hash to the
 * same index (a <em>collision</em>), they are stored in a linked list at that bucket.
 * When the load factor exceeds {@value #LOAD_FACTOR}, the backing array is doubled
 * and all entries are rehashed.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>put / get / remove : O(1) average, O(n) worst case</li>
 *   <li>containsValue      : O(n)</li>
 * </ul>
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class CustomHashMap<K, V> {

    // ── Inner Node ────────────────────────────────────────────────────────────

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // ── Constants ─────────────────────────────────────────────────────────────

    /** Default number of buckets — a power of 2 for efficient modulo. */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Load factor threshold.  The empirical value 0.75 balances memory usage
     * and average chain length.
     */
    private static final double LOAD_FACTOR = 0.75;

    // ── Fields ────────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private Node<K, V>[] buckets = new Node[DEFAULT_CAPACITY];

    private int size;

    // ── Core operations ───────────────────────────────────────────────────────

    /**
     * Associates {@code value} with {@code key}.
     * If the key already exists, its value is updated.
     *
     * @param key   must not be {@code null}
     * @param value the value to associate
     */
    public void put(K key, V value) {
        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];

        // Update existing key
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Insert at the head of the chain (O(1))
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Returns the value associated with {@code key}, or {@code null} if absent.
     *
     * @param key the key to look up
     * @return the associated value, or {@code null}
     */
    public V get(K key) {
        Node<K, V> current = buckets[bucketIndex(key)];
        while (current != null) {
            if (current.key.equals(key)) return current.value;
            current = current.next;
        }
        return null;
    }

    /**
     * Removes the mapping for {@code key} and returns its value,
     * or {@code null} if the key was not present.
     *
     * @param key the key to remove
     * @return the removed value, or {@code null}
     */
    public V remove(K key) {
        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /** Returns {@code true} if this map contains a mapping for {@code key}. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns {@code true} if this map maps one or more keys to {@code value}.
     * This requires a full scan — O(n).
     */
    public boolean containsValue(V value) {
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (current.value.equals(value)) return true;
                current = current.next;
            }
        }
        return false;
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /** Returns {@code true} if this map contains no key-value mappings. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** Removes all mappings. */
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) sb.append(", ");
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.next;
            }
        }
        return sb.append("}").toString();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private int bucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] newBuckets = new Node[buckets.length * 2];
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                int newIndex = Math.abs(current.key.hashCode()) % newBuckets.length;
                Node<K, V> newNode = new Node<>(current.key, current.value);
                newNode.next = newBuckets[newIndex];
                newBuckets[newIndex] = newNode;
                current = current.next;
            }
        }
        buckets = newBuckets;
    }
}
