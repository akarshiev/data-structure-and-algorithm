package algorithms.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * RecursionExamples - Classic recursion problems with clean, documented implementations.
 *
 * <p>Every recursive function has two mandatory parts:
 * <ol>
 *   <li><b>Base case</b> — stops the recursion.</li>
 *   <li><b>Recursive case</b> — reduces the problem toward the base case.</li>
 * </ol>
 * Without a base case the call stack grows until {@link StackOverflowError} is thrown.
 */
public final class RecursionExamples {

    private RecursionExamples() {}

    // ── 1. Factorial ──────────────────────────────────────────────────────────

    /**
     * Returns {@code n!} (n factorial).
     * <pre>
     * factorial(5) = 5 * 4 * 3 * 2 * 1 = 120
     * </pre>
     * Time: O(n) | Space: O(n) call stack.
     *
     * @param n a non-negative integer
     * @return n!
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        return (n <= 1) ? 1 : (long) n * factorial(n - 1);
    }

    // ── 2. Fibonacci ──────────────────────────────────────────────────────────

    /**
     * Returns the n-th Fibonacci number using naive recursion.
     * <pre>
     * F(0)=0, F(1)=1, F(n) = F(n-1) + F(n-2)
     * </pre>
     * <b>Warning:</b> Time O(2ⁿ) — exponential.  Use {@link #fibonacciMemo} for n &gt; 30.
     *
     * @param n index (0-based)
     * @return F(n)
     */
    public static long fibonacci(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Returns the n-th Fibonacci number using top-down memoization.
     * Time: O(n) | Space: O(n).
     *
     * @param n index (0-based)
     * @return F(n)
     */
    public static long fibonacciMemo(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        return fibHelper(n, new long[n + 2]);
    }

    private static long fibHelper(int n, long[] memo) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
    }

    // ── 3. Power ──────────────────────────────────────────────────────────────

    /**
     * Returns {@code base} raised to the power {@code exp}.
     * Time: O(exp) | Space: O(exp).
     *
     * @param base the base
     * @param exp  a non-negative exponent
     * @return base^exp
     */
    public static long power(int base, int exp) {
        if (exp < 0)  throw new IllegalArgumentException("Exponent must be >= 0");
        if (exp == 0) return 1;
        return (long) base * power(base, exp - 1);
    }

    /**
     * Fast exponentiation (binary exponentiation).
     * Squares the base at each step, achieving O(log exp) time.
     *
     * @param base the base
     * @param exp  a non-negative exponent
     * @return base^exp
     */
    public static long fastPower(int base, int exp) {
        if (exp == 0) return 1;
        long half = fastPower(base, exp / 2);
        return (exp % 2 == 0) ? half * half : (long) base * half * half;
    }

    // ── 4. String reverse ─────────────────────────────────────────────────────

    /**
     * Returns the reverse of {@code str} using recursion.
     * Time: O(n²) due to string concatenation; for production prefer iterative.
     *
     * @param str input string
     * @return reversed string
     */
    public static String reverseString(String str) {
        if (str == null || str.length() <= 1) return str;
        return str.charAt(str.length() - 1) + reverseString(str.substring(0, str.length() - 1));
    }

    // ── 5. Tower of Hanoi ────────────────────────────────────────────────────

    /**
     * Solves the Tower of Hanoi for {@code n} disks and prints each move.
     * The minimum number of moves required is 2ⁿ - 1.
     *
     * @param n    number of disks
     * @param from source peg label
     * @param to   destination peg label
     * @param aux  auxiliary peg label
     */
    public static void towerOfHanoi(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + from + " to " + to);
            return;
        }
        towerOfHanoi(n - 1, from, aux, to);
        System.out.println("Move disk " + n + " from " + from + " to " + to);
        towerOfHanoi(n - 1, aux, to, from);
    }

    // ── 6. Subset generation (backtracking) ───────────────────────────────────

    /**
     * Generates all subsets (power set) of {@code arr} using backtracking.
     * Time: O(2ⁿ) | Space: O(n) call stack.
     *
     * <p>Example: {@code arr = [1, 2, 3]} produces
     * {@code [], [3], [2], [2,3], [1], [1,3], [1,2], [1,2,3]}.
     *
     * @param arr     the input array
     * @param index   current index (start with 0)
     * @param current accumulator for the current subset
     */
    public static void generateSubsets(int[] arr, int index, List<Integer> current) {
        if (index == arr.length) {
            System.out.println(current);
            return;
        }
        // Exclude arr[index]
        generateSubsets(arr, index + 1, current);

        // Include arr[index]
        current.add(arr[index]);
        generateSubsets(arr, index + 1, current);
        current.remove(current.size() - 1); // backtrack
    }
}
