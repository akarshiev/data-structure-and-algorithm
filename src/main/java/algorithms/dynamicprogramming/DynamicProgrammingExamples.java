package algorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * DynamicProgrammingExamples - Classic dynamic programming problems.
 *
 * <p>Dynamic programming applies when a problem has:
 * <ol>
 *   <li><b>Optimal substructure</b> — the optimal solution of the full problem
 *       can be built from optimal solutions of sub-problems.</li>
 *   <li><b>Overlapping sub-problems</b> — the same sub-problems are solved
 *       multiple times without memoization.</li>
 * </ol>
 *
 * <p>Two standard approaches:
 * <ul>
 *   <li><b>Top-down (memoization)</b>: recursion + cache.</li>
 *   <li><b>Bottom-up (tabulation)</b>: fill a table iteratively from small to large.</li>
 * </ul>
 */
public final class DynamicProgrammingExamples {

    private DynamicProgrammingExamples() {}

    // ── 1. Fibonacci ──────────────────────────────────────────────────────────

    /**
     * Returns the n-th Fibonacci number using bottom-up tabulation.
     * Time: O(n) | Space: O(n).
     *
     * @param n index (0-based, must be &ge; 0)
     * @return F(n)
     */
    public static long fibonacciTabulation(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // ── 2. Coin Change ────────────────────────────────────────────────────────

    /**
     * Returns the minimum number of coins needed to make {@code amount},
     * or {@code -1} if it is impossible.
     *
     * <p>Example: coins = [1, 5, 10, 25], amount = 30 → 2 (25 + 5).
     *
     * <p>Time: O(amount × coins.length) | Space: O(amount).
     *
     * @param coins  available coin denominations
     * @param amount the target amount
     * @return minimum coin count, or {@code -1} if impossible
     */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // sentinel value representing "impossible"
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ── 3. 0/1 Knapsack ───────────────────────────────────────────────────────

    /**
     * Returns the maximum value that can be carried in a knapsack of the given
     * {@code capacity}.  Each item can be taken at most once (0/1 knapsack).
     *
     * <p>Time: O(n × capacity) | Space: O(n × capacity).
     *
     * @param weights  item weights (parallel to {@code values})
     * @param values   item values  (parallel to {@code weights})
     * @param capacity knapsack capacity
     * @return maximum achievable value
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w]; // don't take item i
                if (weights[i - 1] <= w) {
                    int withItem = dp[i - 1][w - weights[i - 1]] + values[i - 1];
                    dp[i][w] = Math.max(dp[i][w], withItem);
                }
            }
        }
        return dp[n][capacity];
    }

    // ── 4. Longest Common Subsequence ─────────────────────────────────────────

    /**
     * Returns the length of the longest common subsequence of {@code s1} and {@code s2}.
     *
     * <p>Example: s1 = "ABCBDAB", s2 = "BDCAB" → LCS length = 4 ("BCAB").
     *
     * <p>Time: O(m × n) | Space: O(m × n).
     *
     * @param s1 first string
     * @param s2 second string
     * @return LCS length
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // ── 5. Longest Increasing Subsequence ────────────────────────────────────

    /**
     * Returns the length of the longest strictly increasing subsequence.
     *
     * <p>Example: [10, 9, 2, 5, 3, 7, 101, 18] → LIS length = 4 (e.g. 2, 3, 7, 101).
     *
     * <p>Time: O(n²) | Space: O(n).
     *
     * @param arr the input array
     * @return LIS length
     */
    public static int longestIncreasingSubsequence(int[] arr) {
        if (arr.length == 0) return 0;
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    // ── 6. Edit Distance (Levenshtein) ────────────────────────────────────────

    /**
     * Returns the minimum number of single-character edits (insert, delete, replace)
     * needed to transform {@code s1} into {@code s2}.
     *
     * <p>Example: "kitten" → "sitting" = 3 edits.
     *
     * <p>Time: O(m × n) | Space: O(m × n).
     *
     * @param s1 source string
     * @param s2 target string
     * @return edit distance
     */
    public static int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                   Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
