package algorithms.searching;

/**
 * SearchingAlgorithms - A collection of common searching algorithms.
 *
 * <p>All methods return the index of the target element, or {@code -1} if not found.
 *
 * <table border="1" cellpadding="4">
 * <caption>Complexity summary</caption>
 * <tr><th>Algorithm</th>         <th>Time</th>        <th>Precondition</th></tr>
 * <tr><td>Linear Search</td>     <td>O(n)</td>         <td>None</td></tr>
 * <tr><td>Binary Search</td>     <td>O(log n)</td>     <td>Sorted array</td></tr>
 * <tr><td>Jump Search</td>       <td>O(√n)</td>        <td>Sorted array</td></tr>
 * <tr><td>Interpolation</td>     <td>O(log log n)*</td><td>Sorted, uniformly distributed</td></tr>
 * </table>
 * <p>* Average case for interpolation search on uniformly distributed data.
 */
public final class SearchingAlgorithms {

    private SearchingAlgorithms() {}

    // ── Linear Search ─────────────────────────────────────────────────────────

    /**
     * Linear Search — scans every element from left to right.
     * Works on any array regardless of order.
     *
     * @param arr    the array to search
     * @param target the value to find
     * @return index of {@code target}, or {@code -1} if not found
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // ── Binary Search (iterative) ─────────────────────────────────────────────

    /**
     * Binary Search (iterative) — halves the search space at each step.
     * Requires a sorted array.
     *
     * <p>Uses {@code left + (right - left) / 2} instead of {@code (left + right) / 2}
     * to avoid integer overflow.
     *
     * @param arr    a sorted array
     * @param target the value to find
     * @return index of {@code target}, or {@code -1} if not found
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left  = mid + 1;
            else                   right = mid - 1;
        }
        return -1;
    }

    // ── Binary Search (recursive) ─────────────────────────────────────────────

    /**
     * Binary Search (recursive) — same algorithm as the iterative version but
     * expressed recursively.  Uses O(log n) stack space.
     *
     * @param arr    a sorted array
     * @param target the value to find
     * @param left   inclusive lower bound
     * @param right  inclusive upper bound
     * @return index of {@code target}, or {@code -1} if not found
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target)  return binarySearchRecursive(arr, target, mid + 1, right);
        else                    return binarySearchRecursive(arr, target, left, mid - 1);
    }

    // ── Jump Search ───────────────────────────────────────────────────────────

    /**
     * Jump Search — advances by √n steps to find the block containing the target,
     * then performs a linear scan within that block.
     * Faster than linear search, but slower than binary search.
     *
     * @param arr    a sorted array
     * @param target the value to find
     * @return index of {@code target}, or {@code -1} if not found
     */
    public static int jumpSearch(int[] arr, int target) {
        int n    = arr.length;
        int step = (int) Math.sqrt(n);
        int prev = 0;

        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.sqrt(n);
            if (prev >= n) return -1;
        }
        while (arr[prev] < target) {
            if (++prev == Math.min(step, n)) return -1;
        }
        return arr[prev] == target ? prev : -1;
    }

    // ── Interpolation Search ──────────────────────────────────────────────────

    /**
     * Interpolation Search — estimates the position of the target using linear
     * interpolation.  Achieves O(log log n) on uniformly distributed data but
     * degrades to O(n) on non-uniform distributions.
     *
     * @param arr    a sorted, uniformly distributed array
     * @param target the value to find
     * @return index of {@code target}, or {@code -1} if not found
     */
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high && target >= arr[low] && target <= arr[high]) {
            if (arr[high] == arr[low]) {
                return arr[low] == target ? low : -1;
            }
            int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);
            if (arr[pos] == target) return pos;
            if (arr[pos] < target)  low  = pos + 1;
            else                    high = pos - 1;
        }
        return -1;
    }
}
