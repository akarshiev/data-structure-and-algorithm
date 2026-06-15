package algorithms.sorting;

import java.util.Arrays;

/**
 * SortingAlgorithms - A collection of classic sorting algorithms.
 *
 * <p>All methods sort in ascending order and modify the input array in place
 * (except {@link #mergeSort}, which uses O(n) auxiliary space internally).
 *
 * <table border="1" cellpadding="4">
 * <caption>Complexity summary</caption>
 * <tr><th>Algorithm</th>     <th>Best</th>      <th>Average</th>    <th>Worst</th>    <th>Space</th>  <th>Stable</th></tr>
 * <tr><td>Bubble Sort</td>   <td>O(n)</td>      <td>O(n²)</td>      <td>O(n²)</td>   <td>O(1)</td>   <td>Yes</td></tr>
 * <tr><td>Selection Sort</td><td>O(n²)</td>     <td>O(n²)</td>      <td>O(n²)</td>   <td>O(1)</td>   <td>No</td></tr>
 * <tr><td>Insertion Sort</td><td>O(n)</td>      <td>O(n²)</td>      <td>O(n²)</td>   <td>O(1)</td>   <td>Yes</td></tr>
 * <tr><td>Merge Sort</td>    <td>O(n log n)</td><td>O(n log n)</td> <td>O(n log n)</td><td>O(n)</td> <td>Yes</td></tr>
 * <tr><td>Quick Sort</td>    <td>O(n log n)</td><td>O(n log n)</td> <td>O(n²)</td>   <td>O(log n)</td><td>No</td></tr>
 * </table>
 */
public final class SortingAlgorithms {

    private SortingAlgorithms() {} // utility class — no instances

    // ── Bubble Sort ───────────────────────────────────────────────────────────

    /**
     * Bubble Sort — repeatedly compares adjacent elements and swaps them if
     * out of order.  The early-exit optimisation terminates in O(n) when the
     * input is already sorted.
     *
     * @param arr the array to sort (modified in place)
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break; // array is already sorted
        }
    }

    // ── Selection Sort ────────────────────────────────────────────────────────

    /**
     * Selection Sort — finds the minimum in the unsorted section and moves it
     * to the front.  Always O(n²) comparisons regardless of input.
     *
     * @param arr the array to sort (modified in place)
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            if (minIdx != i) swap(arr, i, minIdx);
        }
    }

    // ── Insertion Sort ────────────────────────────────────────────────────────

    /**
     * Insertion Sort — builds a sorted prefix by inserting each element at the
     * correct position.  Efficient for small or nearly-sorted arrays.
     * Java's {@code Arrays.sort()} uses it for sub-arrays shorter than 47 elements.
     *
     * @param arr the array to sort (modified in place)
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ── Merge Sort ────────────────────────────────────────────────────────────

    /**
     * Merge Sort — divide-and-conquer algorithm that splits the array in half,
     * recursively sorts each half, then merges them.
     * Guaranteed O(n log n) in all cases at the cost of O(n) auxiliary space.
     *
     * @param arr the array to sort (modified in place)
     */
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        int mid = arr.length / 2;
        int[] left  = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length)  result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
    }

    // ── Quick Sort ────────────────────────────────────────────────────────────

    /**
     * Quick Sort — selects a pivot, partitions the array so that all elements
     * less than the pivot are to its left and all greater elements are to its
     * right, then recurses on each partition.
     *
     * <p>The pivot chosen here is the last element.  For better average-case
     * performance in practice consider a random or median-of-three pivot.
     *
     * @param arr  the array to sort (modified in place)
     * @param low  inclusive lower bound of the sub-array to sort
     * @param high inclusive upper bound of the sub-array to sort
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low >= high) return;
        int pivotIdx = partition(arr, low, high);
        quickSort(arr, low, pivotIdx - 1);
        quickSort(arr, pivotIdx + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Swaps {@code arr[i]} and {@code arr[j]}. */
    public static void swap(int[] arr, int i, int j) {
        int tmp  = arr[i];
        arr[i]   = arr[j];
        arr[j]   = tmp;
    }

    /** Returns a formatted string representation of {@code arr}. */
    public static String arrayToString(int[] arr) {
        return Arrays.toString(arr);
    }
}
