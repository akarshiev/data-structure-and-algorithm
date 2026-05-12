package algorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * DynamicProgrammingExamples - Dinamik dasturlash misollari.
 * 
 * NIMA UCHUN DYNAMIC PROGRAMMING (DP) MUHIM?
 * DP - murakkab muammolarni kichikroq, takroriy qismlarga bo'lib hal qilish usuli.
 * 
 * DP ning ikki asosiy usuli:
 * 1. Memoization (Top-Down) - Recursion + xotira
 * 2. Tabulation (Bottom-Up) - Iterativ jadval to'ldirish
 * 
 * DP QACHON ISHLATILADI?
 * 1. Optimal Substructure - katta muammo kichik qismlardan tashkil topgan
 * 2. Overlapping Subproblems - bir xil qismlar ko'p marta qayta hisoblanadi
 * 
 * @author DSA Project
 */
public class DynamicProgrammingExamples {

    // ==================== 1. FIBONACCI ====================
    
    /**
     * Fibonacci - Tabulation (Bottom-Up) usuli.
     * 
     * QANDAY ISHLAYDI?
     * 1. Kichikdan boshlab kattaga qarab oldinga siljiymiz
     * 2. Har bir qiymatni jadvalga yozamiz
     * 3. Oldingi 2 ta qiymatdan foydalanamiz
     * 
     * TEZLIK: O(n) - lineer vaqt
     * XOTIRA: O(n) - jadval uchun
     * 
     * @param n - indeks
     * @return fibonacci soni
     */
    public static long fibonacciTabulation(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        // Jadval yaratamiz
        long[] dp = new long[n + 1];
        dp[0] = 0; // Bazaviy holat 1
        dp[1] = 1; // Bazaviy holat 2
        
        // Jadvalni to'ldiramiz
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }

    // ==================== 2. COIN CHANGE ====================
    
    /**
     * Tangalar bilan pul to'lash - minimal tanga soni.
     * 
     * MUAMMO: Berilgan pul miqdorini eng kam tanga bilan to'lash.
     * 
     * MISOL: coins = [1, 5, 10, 25], amount = 30
     * NATIJA: 2 (25 + 5 yoki 10 + 10 + 10)
     * 
     * QANDAY ISHLAYDI?
     * dp[i] = i pulni to'lash uchun minimal tanga soni
     * dp[0] = 0 (0 pul 0 tanga bilan to'lanadi)
     * dp[i] = min(dp[i - coin] + 1) for each coin
     * 
     * TEZLIK: O(amount * coins.length)
     * XOTIRA: O(amount)
     * 
     * @param coins - tangalar massivi
     * @param amount - pul miqdori
     * @return minimal tanga soni yoki -1
     */
    public static int coinChange(int[] coins, int amount) {
        // Jadval yaratamiz
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // "Cheksiz" bilan to'ldiramiz
        dp[0] = 0; // Bazaviy holat
        
        // Jadvalni to'ldiramiz
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        // Agar dp[amount] hali "cheksiz" bo'lsa, yechim yo'q
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ==================== 3. KNAPSACK (XONTO'PA) ====================
    
    /**
     * 0/1 Knapsack - Xonto'pa muammosi.
     * 
     * MUAMMO: Cheklangan sig'imli xonto'paga eng qimmatli narsalarni solish.
     * Har bir narsani faqat bir marta olish mumkin.
     * 
     * MISOL:
     * weight = [1, 3, 4, 5]
     * value = [1, 4, 5, 7]
     * capacity = 7
     * 
     * NATIJA: 9 (3+4=7 kg, 4+5=9 qiymat)
     * 
     * TEZLIK: O(n * capacity)
     * XOTIRA: O(n * capacity)
     * 
     * @param weights - og'irliklar
     * @param values - qiymatlar
     * @param capacity - sig'im
     * @return maksimal qiymat
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        
        // 2D jadval yaratamiz
        // dp[i][w] = i ta elementdan w sig'imga qarab maksimal qiymat
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Jadvalni to'ldiramiz
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // Elementni olmaslik
                dp[i][w] = dp[i - 1][w];
                
                // Elementni olish (agar sig'imga sig'rsa)
                if (weights[i - 1] <= w) {
                    int valueWithItem = dp[i - 1][w - weights[i - 1]] + values[i - 1];
                    dp[i][w] = Math.max(dp[i][w], valueWithItem);
                }
            }
        }
        
        return dp[n][capacity];
    }

    // ==================== 4. LONGEST COMMON SUBSEQUENCE ====================
    
    /**
     * Eng uzun umumiy natija (LCS).
     * 
     * MUAMMO: Ikki stringning eng uzun umumiy ketma-ketligi.
     * 
     * MISOL:
     * s1 = "ABCBDAB"
     * s2 = "BDCAB"
     * LCS = "BCAB" (uzunligi 4)
     * 
     * TEZLIK: O(m * n)
     * XOTIRA: O(m * n)
     * 
     * @param s1 - birinchi string
     * @param s2 - ikkinchi string
     * @return LCS uzunligi
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        // 2D jadval yaratamiz
        int[][] dp = new int[m + 1][n + 1];
        
        // Jadvalni to'ldiramiz
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Harflar teng - diagonaldan +1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Harflar teng emas - yuqoridagi yoki chapdagi kattaroq
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }

    // ==================== 5. LONGEST INCREASING SUBSEQUENCE ====================
    
    /**
     * Eng uzun o'suvchi natija (LIS).
     * 
     * MUAMMO: Massivdagi eng uzun o'suvchi tartibdagi natija.
     * 
     * MISOL: [10, 9, 2, 5, 3, 7, 101, 18]
     * LIS = [2, 3, 7, 101] yoki [2, 5, 7, 18] (uzunligi 4)
     * 
     * TEZLIK: O(n²)
     * XOTIRA: O(n)
     * 
     * @param arr - massiv
     * @return LIS uzunligi
     */
    public static int longestIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n]; // dp[i] = i indeksdagi LIS uzunligi
        
        // Barcha elementlarni 1 ga teng qilamiz
        Arrays.fill(dp, 1);
        
        int maxLength = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Agar arr[j] < arr[i] bo'lsa, LIS ni yangilash mumkin
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        
        return maxLength;
    }

    // ==================== 6. EDIT DISTANCE ====================
    
    /**
     * Tahrir masofasi (Levenshtein Distance).
     * 
     * MUAMMO: Bitta stringni ikkinchisiga aylantirish uchun 
     * kamida nechta amal kerak (qo'shish, o'chirish, almashtirish).
     * 
     * MISOL:
     * s1 = "kitten"
     * s2 = "sitting"
     * Masofa = 3 (k->s, e->i, +g)
     * 
     * TEZLIK: O(m * n)
     * XOTIRA: O(m * n)
     * 
     * @param s1 - birinchi string
     * @param s2 - ikkinchi string
     * @return minimal tahrirlar soni
     */
    public static int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        // Bazaviy holatlar
        for (int i = 0; i <= m; i++) dp[i][0] = i; // Faqat o'chirish
        for (int j = 0; j <= n; j++) dp[0][j] = j; // Faqat qo'shish
        
        // Jadvalni to'ldiramiz
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Harflar teng - hech narsa qilmaymiz
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Harflar teng emas - minimal amalni tanlaymiz
                    dp[i][j] = 1 + Math.min(
                        Math.min(
                            dp[i - 1][j],     // O'chirish
                            dp[i][j - 1]      // Qo'shish
                        ),
                        dp[i - 1][j - 1]      // Almashtirish
                    );
                }
            }
        }
        
        return dp[m][n];
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Dynamic Programming Examples Sinov ===\n");
        
        // Fibonacci
        System.out.println("--- Fibonacci (Tabulation) ---");
        for (int i = 0; i <= 20; i++) {
            System.out.println("F(" + i + ") = " + fibonacciTabulation(i));
        }
        
        // Coin Change
        System.out.println("\n--- Coin Change ---");
        int[] coins = {1, 5, 10, 25};
        int amount = 30;
        System.out.println("Tangalar: " + Arrays.toString(coins));
        System.out.println("Miqdor: " + amount);
        System.out.println("Minimal tanga: " + coinChange(coins, amount));
        
        // Knapsack
        System.out.println("\n--- 0/1 Knapsack ---");
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        System.out.println("Og'irliklar: " + Arrays.toString(weights));
        System.out.println("Qiymatlar: " + Arrays.toString(values));
        System.out.println("Sig'im: " + capacity);
        System.out.println("Maksimal qiymat: " + knapsack(weights, values, capacity));
        
        // LCS
        System.out.println("\n--- Longest Common Subsequence ---");
        String s1 = "ABCBDAB";
        String s2 = "BDCAB";
        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);
        System.out.println("LCS uzunligi: " + longestCommonSubsequence(s1, s2));
        
        // LIS
        System.out.println("\n--- Longest Increasing Subsequence ---");
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Massiv: " + Arrays.toString(arr));
        System.out.println("LIS uzunligi: " + longestIncreasingSubsequence(arr));
        
        // Edit Distance
        System.out.println("\n--- Edit Distance ---");
        String t1 = "kitten";
        String t2 = "sitting";
        System.out.println("s1: " + t1);
        System.out.println("s2: " + t2);
        System.out.println("Tahrir masofasi: " + editDistance(t1, t2));
    }
}
