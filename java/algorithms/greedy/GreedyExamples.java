package algorithms.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * GreedyExamples - Ochko'zlik algoritmlari to'plami.
 * 
 * NIMA UCHUN GREEDY MUHIM?
 * Greedy algoritmlar har qadamda "eng yaxshi" (optimal) tanlovni qiladi.
 * Bu tanlovlar yig'indisi umumiy optimal yechimni berishi mumkin.
 * 
 * GREEDY QACHON ISHLAYDI?
 * 1. Greedy Choice Property - lokal optimal tanlov global optimalga olib keladi
 * 2. Optimal Substructure - katta muammo kichik qismlardan tashkil topgan
 * 
 * GREEDY QACHON ISHLAMAYDI?
 * Ba'zan lokal optimal tanlov global optimalga OLIB KELMAYDI.
 * Bu holatda Dynamic Programming ishlatiladi.
 * 
 * @author DSA Project
 */
public class GreedyExamples {

    // ==================== 1. FRACTIONAL KNAPSACK ====================
    
    /**
     * Kasarlik xonto'pa (Fractional Knapsack).
     * 
     * MUAMMO: Cheklangan sig'imli xonto'paga eng qimmatli narsalarni solish.
     * Bu versiyada narsalarning qismini olish mumkin.
     * 
     * GREEDY TANLOV: Har qadamda "qiymat/og'irlik" nisbati eng yuqori narsani olamiz.
     * 
     * MISOL:
     * Narsa 1: og'irlik=10, qiymat=60 (nisbat=6)
     * Narsa 2: og'irlik=20, qiymat=100 (nisbat=5)
     * Narsa 3: og'irlik=30, qiymat=120 (nisbat=4)
     * Sig'im = 50
     * 
     * YECHIM: Narsa 1 (10kg, 60) + Narsa 2 (20kg, 100) + Narsa 3 ning 2/3 (20kg, 80) = 240
     * 
     * TEZLIK: O(n log n) - saralash uchun
     * 
     * @param weights - og'irliklar
     * @param values - qiymatlar
     * @param capacity - sig'im
     * @return maksimal qiymat
     */
    public static double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        double[][] ratio = new double[n][3]; // [index, qiymat, og'irlik, nisbat]
        
        // Nisbatlarni hisoblaymiz
        for (int i = 0; i < n; i++) {
            ratio[i][0] = i;
            ratio[i][1] = values[i];
            ratio[i][2] = weights[i];
        }
        
        // Nisbat bo'yicha saralaymiz (kattaroqdan kichikroqqa)
        Arrays.sort(ratio, (a, b) -> Double.compare(b[1] / b[2], a[1] / a[2]));
        
        double totalValue = 0;
        int remainingCapacity = capacity;
        
        // Har bir narsani tekshiramiz
        for (int i = 0; i < n; i++) {
            int index = (int) ratio[i][0];
            int weight = (int) ratio[i][2];
            int value = (int) ratio[i][1];
            
            if (weight <= remainingCapacity) {
                // Butun narsani olamiz
                totalValue += value;
                remainingCapacity -= weight;
            } else {
                // Qisman olamiz
                totalValue += value * ((double) remainingCapacity / weight);
                break; // Xonto'pa to'ldi
            }
        }
        
        return totalValue;
    }

    // ==================== 2. ACTIVITY SELECTION ====================
    
    /**
     * Faoliyatlarni tanlash (Activity Selection).
     * 
     * MUAMMO: Berilgan faoliyatlardan eng ko'pini tanlash,
     * ular vaqt jihatidan ustma-ust tushmasligi kerak.
     * 
     * GREEDY TANLOV: Har doim eng erta tugaydigan faoliyatni tanlaymiz.
     * 
     * MISOL:
     * Faoliyat 1: boshlanish=1, tugash=3
     * Faoliyat 2: boshlanish=2, tugash=5
     * Faoliyat 3: boshlanish=4, tugash=7
     * Faoliyat 4: boshlanish=6, tugash=8
     * 
     * TANLANGANLAR: Faoliyat 1 (1-3), Faoliyat 3 (4-7), Faoliyat 4 (6-8) - 3 ta
     * 
     * TEZLIK: O(n log n) - saralash uchun
     * 
     * @param start - boshlanish vaqtlari
     * @param finish - tugash vaqtlari
     * @return tanlangan faoliyatlar soni
     */
    public static int activitySelection(int[] start, int[] finish) {
        int n = start.length;
        
        // Faoliyatlarni tugash vaqtiga qarab saralaymiz
        int[][] activities = new int[n][2];
        for (int i = 0; i < n; i++) {
            activities[i][0] = start[i];
            activities[i][1] = finish[i];
        }
        
        Arrays.sort(activities, Comparator.comparingInt(a -> a[1]));
        
        int count = 1; // Birinchi faoliyatni tanlaymiz
        int lastFinish = activities[0][1]; // Birinchi faoliyatning tugash vaqti
        
        for (int i = 1; i < n; i++) {
            // Agar keyingi faoliyat oldingisidan keyin boshlansa, tanlaymiz
            if (activities[i][0] >= lastFinish) {
                count++;
                lastFinish = activities[i][1];
            }
        }
        
        return count;
    }

    // ==================== 3. JOB SCHEDULING ====================
    
    /**
     * Vazifalarni jadvalga solish (Job Scheduling).
     * 
     * MUAMMO: Berilgan vazifalardan eng ko'p sonlisini bajarish.
     * Har bir vazifaning muddati va foydasi bor.
     * 
     * GREEDY TANLOV: Har doim eng ko'p foyda keltiradigan vazifani tanlaymiz.
     * 
     * TEZLIK: O(n²)
     * 
     * @param jobs - vazifalar [foyda, muddat]
     * @param maxTime - maksimal vaqt
     * @return maksimal foyda
     */
    public static int jobScheduling(int[][] jobs, int maxTime) {
        // Vazifalarni foyda bo'yicha saralaymiz (kattaroqdan kichikroqqa)
        Arrays.sort(jobs, (a, b) -> b[0] - a[0]);
        
        boolean[] timeSlots = new boolean[maxTime + 1]; // Band vaqtlar
        int totalProfit = 0;
        
        for (int[] job : jobs) {
            int profit = job[0];
            int deadline = job[1];
            
            // Muddatdan boshlab orqaga qarab bo'sh joy qidiramiz
            for (int t = Math.min(deadline, maxTime); t > 0; t--) {
                if (!timeSlots[t]) {
                    timeSlots[t] = true;
                    totalProfit += profit;
                    break;
                }
            }
        }
        
        return totalProfit;
    }

    // ==================== 4. COIN CHANGE (Greedy) ====================
    
    /**
     * Tangalar bilan pul to'lash (Greedy usuli).
     * 
     * GREEDY TANLOV: Har doim eng katta tangani olamiz.
     * 
     * DIQQAT: Bu usul har doim optimal natija bermaydi!
     * Faqat ma'lum tangalar to'plami uchun ishlaydi (masalan, AQSH tangalari).
     * 
     * TEZLIK: O(amount)
     * 
     * @param coins - tangalar (kattaroqdan kichikroqqa saralangan)
     * @param amount - pul miqdori
     * @return minimal tanga soni yoki -1
     */
    public static int coinChangeGreedy(int[] coins, int amount) {
        int count = 0;
        
        for (int coin : coins) {
            while (amount >= coin) {
                amount -= coin;
                count++;
            }
        }
        
        return amount == 0 ? count : -1;
    }

    // ==================== 5. Huffman CODING ====================
    
    /**
     * Huffman kodlash - ma'lumotlarni siqish.
     * 
     * GREEDY TANLOV: Har doim eng kam uchrashchi belgini birlashtiramiz.
     * 
     * QANDAY ISHLAYDI?
     * 1. Har bir belgi uchun chastotani hisoblaymiz
     * 2. Eng kam chastotali 2 ta tugunni birlashtiramiz
     * 3. Takrorlaymiz, bitta tugun qolguncha
     * 4. Har bir yo'lga 0 yoki 1 yozamiz
     * 
     * MISOL:
     * a: 5 marta, b: 3 marta, c: 2 marta, d: 1 marta
     * Huffman: a=0, b=10, c=110, d=111
     * 
     * TEZLIK: O(n log n)
     */
    
    // Huffman tuguni
    static class HuffmanNode {
        char ch;
        int freq;
        HuffmanNode left, right;
        
        HuffmanNode(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Greedy Algorithms Examples Sinov ===\n");
        
        // Fractional Knapsack
        System.out.println("--- Fractional Knapsack ---");
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 50;
        System.out.println("Og'irliklar: " + Arrays.toString(weights));
        System.out.println("Qiymatlar: " + Arrays.toString(values));
        System.out.println("Sig'im: " + capacity);
        System.out.println("Maksimal qiymat: " + fractionalKnapsack(weights, values, capacity));
        
        // Activity Selection
        System.out.println("\n--- Activity Selection ---");
        int[] start = {1, 2, 4, 6};
        int[] finish = {3, 5, 7, 8};
        System.out.println("Boshlanish: " + Arrays.toString(start));
        System.out.println("Tugash: " + Arrays.toString(finish));
        System.out.println("Tanlangan faoliyatlar soni: " + activitySelection(start, finish));
        
        // Job Scheduling
        System.out.println("\n--- Job Scheduling ---");
        int[][] jobs = {{100, 2}, {19, 1}, {27, 2}, {25, 1}, {15, 3}};
        int maxTime = 3;
        System.out.println("Vazifalar: [foydа, muddat]");
        System.out.println("Maksimal vaqt: " + maxTime);
        System.out.println("Maksimal foyda: " + jobScheduling(jobs, maxTime));
        
        // Coin Change (Greedy)
        System.out.println("\n--- Coin Change (Greedy) ---");
        int[] coins = {25, 10, 5, 1};
        int amount = 30;
        System.out.println("Tangalar: " + Arrays.toString(coins));
        System.out.println("Miqdor: " + amount);
        System.out.println("Minimal tanga: " + coinChangeGreedy(coins, amount));
    }
}
