package algorithms.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * GreedyExamples - Classic greedy algorithm implementations.
 *
 * <p>A greedy algorithm makes the locally optimal choice at each step, hoping
 * the sequence of local optima leads to a global optimum.  This works when
 * the problem has:
 * <ul>
 *   <li><b>Greedy choice property</b> — a local optimum can be incorporated
 *       into a global optimum.</li>
 *   <li><b>Optimal substructure</b> — an optimal solution contains optimal
 *       solutions to sub-problems.</li>
 * </ul>
 * When these properties do not hold, use Dynamic Programming instead.
 */
public final class GreedyExamples {

    private GreedyExamples() {}

    // ── 1. Fractional Knapsack ────────────────────────────────────────────────

    /**
     * Solves the fractional knapsack problem.
     *
     * <p>Unlike the 0/1 knapsack, items may be taken fractionally.
     * The greedy strategy: always take the item with the highest
     * value-to-weight ratio first.
     *
     * <p>Example: weights = [10, 20, 30], values = [60, 100, 120], capacity = 50
     * → result = 240.0 (full items 1 &amp; 2 + 2/3 of item 3).
     *
     * <p>Time: O(n log n) for sorting | Space: O(n).
     *
     * @param weights  item weights (parallel to {@code values})
     * @param values   item values  (parallel to {@code weights})
     * @param capacity knapsack capacity
     * @return maximum value (may be fractional)
     */
    public static double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        // Build item array: [value/weight ratio, value, weight]
        double[][] items = new double[n][3];
        for (int i = 0; i < n; i++) {
            items[i][0] = (double) values[i] / weights[i]; // ratio
            items[i][1] = values[i];
            items[i][2] = weights[i];
        }
        // Sort descending by ratio
        Arrays.sort(items, (a, b) -> Double.compare(b[0], a[0]));

        double totalValue = 0;
        int remaining = capacity;
        for (double[] item : items) {
            int w = (int) item[2];
            int v = (int) item[1];
            if (remaining >= w) {
                totalValue += v;
                remaining  -= w;
            } else {
                // Take the fraction that fits
                totalValue += v * ((double) remaining / w);
                break;
            }
        }
        return totalValue;
    }

    // ── 2. Activity Selection ─────────────────────────────────────────────────

    /**
     * Selects the maximum number of non-overlapping activities.
     *
     * <p>Greedy strategy: always pick the activity that finishes earliest
     * (leaves the most room for future activities).
     *
     * <p>Time: O(n log n) for sorting | Space: O(n).
     *
     * @param start  activity start times (parallel to {@code finish})
     * @param finish activity finish times (parallel to {@code start})
     * @return maximum number of non-overlapping activities
     */
    public static int activitySelection(int[] start, int[] finish) {
        int n = start.length;
        int[][] activities = new int[n][2];
        for (int i = 0; i < n; i++) {
            activities[i][0] = start[i];
            activities[i][1] = finish[i];
        }
        Arrays.sort(activities, Comparator.comparingInt(a -> a[1]));

        int count      = 1;
        int lastFinish = activities[0][1];
        for (int i = 1; i < n; i++) {
            if (activities[i][0] >= lastFinish) {
                count++;
                lastFinish = activities[i][1];
            }
        }
        return count;
    }

    // ── 3. Job Scheduling ─────────────────────────────────────────────────────

    /**
     * Schedules jobs to maximise total profit.
     * Each job has a deadline and a profit; only one job can run per unit time.
     *
     * <p>Greedy strategy: sort jobs by profit (descending) and schedule each
     * job in the latest free slot before its deadline.
     *
     * <p>Time: O(n²) | Space: O(n).
     *
     * @param profits   job profits (parallel to {@code deadlines})
     * @param deadlines job deadlines, 1-based (parallel to {@code profits})
     * @return maximum achievable profit
     */
    public static int jobScheduling(int[] profits, int[] deadlines) {
        int n = profits.length;
        int[][] jobs = new int[n][2];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = profits[i];
            jobs[i][1] = deadlines[i];
        }
        // Sort descending by profit
        Arrays.sort(jobs, (a, b) -> b[0] - a[0]);

        int maxDeadline = 0;
        for (int[] job : jobs) maxDeadline = Math.max(maxDeadline, job[1]);

        boolean[] slot = new boolean[maxDeadline + 1];
        int totalProfit = 0;

        for (int[] job : jobs) {
            // Find the latest free slot at or before this job's deadline
            for (int t = job[1]; t >= 1; t--) {
                if (!slot[t]) {
                    slot[t]      = true;
                    totalProfit += job[0];
                    break;
                }
            }
        }
        return totalProfit;
    }
}
