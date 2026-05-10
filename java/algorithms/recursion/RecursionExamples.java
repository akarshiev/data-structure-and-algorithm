package algorithms.recursion;

/**
 * RecursionExamples - Rekursiya misollari to'plami.
 * 
 * NIMA UCHUN RECURSION MUHIM?
 * Rekursiya - funksiya o'zini o'zi chaqirishi. Bu murakkab muammolarni
 * oddiy qismlarga bo'lishga yordam beradi.
 * 
 * RECURSIONNING ASOSIY QISMLARI:
 * 1. Base Case (Bazaviy holat) - recursion to'xtashi kerak
 * 2. Recursive Case - funksiya o'zini qayta chaqiradi
 * 
 * DIQQAT: Agar base case yo'q bo'lsa, StackOverflowError xatosi beradi!
 * 
 * MISOL: Rekursiv funksiya qanday ishlaydi?
 * factorial(4):
 * 4 * factorial(3)
 * 4 * 3 * factorial(2)
 * 4 * 3 * 2 * factorial(1)
 * 4 * 3 * 2 * 1 = 24
 * 
 * @author DSA Project
 */
public class RecursionExamples {

    // ==================== 1. FAKTORIAL ====================
    
    /**
     * Faktorialni hisoblash (rekursiv).
     * 
     * n! = n * (n-1) * (n-2) * ... * 1
     * 
     * MISOL:
     * 5! = 5 * 4 * 3 * 2 * 1 = 120
     * 
     * RECURSION CHIZIĞI:
     * factorial(5) = 5 * factorial(4)
     * factorial(4) = 4 * factorial(3)
     * factorial(3) = 3 * factorial(2)
     * factorial(2) = 2 * factorial(1)
     * factorial(1) = 1 (BASE CASE!)
     * 
     * TEZLIK: O(n) - n marta recursion
     * XOTIRA: O(n) - recursion stek uchun
     * 
     * @param n - son
     * @return n!
     */
    public static long factorial(int n) {
        // BASE CASE: 0! va 1! = 1
        if (n <= 1) {
            return 1;
        }
        
        // RECURSIVE CASE: n! = n * (n-1)!
        return n * factorial(n - 1);
    }

    // ==================== 2. FIBONACCI ====================
    
    /**
     * Fibonacci sonini topish (rekursiv).
     * 
     * Fibonacci ketma-ketligi: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
     * Har bir son ikki oldingisining yig'indisi:
     * F(0) = 0, F(1) = 1
     * F(n) = F(n-1) + F(n-2)
     * 
     * RECURSION CHIZIĞI:
     * fibonacci(5) = fibonacci(4) + fibonacci(3)
     * fibonacci(4) = fibonacci(3) + fibonacci(2)
     * ...
     * 
     * TEZLIK: O(2^n) - JUDA SEKIN! (eksonensial)
     * XOTIRA: O(n) - recursion stek uchun
     * 
     * NIMA UCHUN SEKIN?
     * Bir xil qiymatlar ko'p marta hisoblanadi!
     * Masalan, fibonacci(3) 2 marta, fibonacci(2) 3 marta hisoblanadi.
     * 
     * @param n - indeks
     * @return fibonacci soni
     */
    public static long fibonacci(int n) {
        // BASE CASE
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        // RECURSIVE CASE
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Fibonacci - Tezlashtirilgan versiya (Memoization).
     * 
     * NIMA UCHUN TEZROQ?
     * Oldin hisoblangan qiymatlarni saqlab qoramiz.
     * Bu "Dynamic Programming" ning bir usuli.
     * 
     * TEZLIK: O(n) - har bir qiymat faqat 1 marta hisoblanadi
     * 
     * @param n - indeks
     * @return fibonacci soni
     */
    public static long fibonacciMemo(int n) {
        long[] memo = new long[n + 1];
        return fibonacciHelper(n, memo);
    }
    
    private static long fibonacciHelper(int n, long[] memo) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        // Agar allaqachon hisoblangan bo'lsa, qaytaramiz
        if (memo[n] != 0) return memo[n];
        
        // Hisoblaymiz va saqlab qoramiz
        memo[n] = fibonacciHelper(n - 1, memo) + fibonacciHelper(n - 2, memo);
        return memo[n];
    }

    // ==================== 3. POWER (DARAJA) ====================
    
    /**
     * Sonning darajasini hisoblash.
     * 
     * base^exp = base * base * ... * base (exp marta)
     * 
     * TEZLIK: O(exp)
     * 
     * @param base - asos
     * @param exp - daraja
     * @return base^exp
     */
    public static long power(int base, int exp) {
        // BASE CASE: har qanday sonning 0-darajasi 1
        if (exp == 0) return 1;
        
        // RECURSIVE CASE
        return base * power(base, exp - 1);
    }

    /**
     * Tez daraja hisoblash (Binary Exponentiation).
     * 
     * QANDAY ISHLAYDI?
     * base^exp = (base^2)^(exp/2) agar exp juft bo'lsa
     * base^exp = base * (base^2)^(exp/2) agar exp toq bo'lsa
     * 
     * TEZLIK: O(log exp) - JUDA TEZ!
     * 
     * MISOL: 2^10
     * 2^10 = (2^2)^5 = 4^5
     * 4^5 = 4 * (4^2)^2 = 4 * 16^2
     * 16^2 = (16^2)^1 = 256^1
     * 256^1 = 256 * (256^2)^0 = 256 * 1 = 256
     * NATIJA: 4 * 256 = 1024
     * 
     * @param base - asos
     * @param exp - daraja
     * @return base^exp
     */
    public static long fastPower(int base, int exp) {
        if (exp == 0) return 1;
        
        if (exp % 2 == 0) {
            // Toq daraja
            long half = fastPower(base, exp / 2);
            return half * half;
        } else {
            // Juft daraja
            long half = fastPower(base, (exp - 1) / 2);
            return base * half * half;
        }
    }

    // ==================== 4. STRING REVERSING ====================
    
    /**
     * String ni teskari tartibda qaytarish.
     * 
     * RECURSION CHIZIĞI:
     * reverse("hello") = reverse("ello") + "h"
     * reverse("ello") = reverse("llo") + "e"
     * reverse("llo") = reverse("lo") + "l"
     * reverse("lo") = reverse("o") + "l"
     * reverse("o") = "o" (BASE CASE!)
     * 
     * NATIJA: "olleh"
     * 
     * @param str - matn
     * @return teskari matn
     */
    public static String reverseString(String str) {
        // BASE CASE: bo'sh yoki bitta harf
        if (str.length() <= 1) return str;
        
        // RECURSIVE CASE: oxirgi harf + qolganlarning teskari
        return str.charAt(str.length() - 1) + reverseString(str.substring(0, str.length() - 1));
    }

    // ==================== 5. TOWER OF HANOI ====================
    
    /**
     * Hanoi minoralari muammosi.
     * 
     * QOIDALAR:
     * 1. Faqat bitta diskni ko'tarish mumkin
     * 2. Kattaroq disk kichikroq ustiga qo'yilmasligi kerak
     * 3. Bitta ustundan boshqasiga ko'chirish kerak
     * 
     * QANDAY ISHLAYDI?
     * 1. n-1 diskni yordamchi ustunga ko'chiramiz
     * 2. Eng katta diskni maqsad ustunga ko'chiramiz
     * 3. n-1 diskni yordamchidan maqsadga ko'chiramiz
     * 
     * TEZLIK: O(2^n - 1)
     * 
     * @param n - disklar soni
     * @param from - boshlang'ich ustun
     * @param to - maqsad ustun
     * @param aux - yordamchi ustun
     */
    public static void towerOfHanoi(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.println("Disk 1 ni " + from + " dan " + + to + " ga ko'chirish");
            return;
        }
        
        // n-1 diskni yordamchi ustunga ko'chiramiz
        towerOfHanoi(n - 1, from, aux, to);
        
        // Eng katta diskni maqsadga ko'chiramiz
        System.out.println("Disk " + n + " ni " + from + " dan " + to + " ga ko'chirish");
        
        // n-1 diskni yordamchidan maqsadga ko'chiramiz
        towerOfHanoi(n - 1, aux, to, from);
    }

    // ==================== 6. SUBSET GENERATION ====================
    
    /**
     * To'plamning barcha natijalarini (subsets) generatsiya qilish.
     * 
     * MISOL: {1, 2, 3}
     * Natija: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
     * 
     * QANDAY ISHLAYDI?
     * Har bir element uchun 2 ta variant bor: qo'shish yoki qo'shmaslik
     * 
     * @param arr - to'plam
     * @param index - hozirgi indeks
     * @param current - joriy natija
     */
    public static void generateSubsets(int[] arr, int index, java.util.List<Integer> current) {
        // BASE CASE: barcha elementlarni ko'rib chiqdik
        if (index == arr.length) {
            System.out.println(current);
            return;
        }
        
        // Variant 1: Elementni QO'SHMAYMIZ
        generateSubsets(arr, index + 1, current);
        
        // Variant 2: Elementni QO'SHAMIZ
        current.add(arr[index]);
        generateSubsets(arr, index + 1, current);
        current.remove(current.size() - 1); // BACKTRACKING
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Recursion Examples Sinov ===\n");
        
        // Faktorial
        System.out.println("--- Faktorial ---");
        for (int i = 0; i <= 10; i++) {
            System.out.println(i + "! = " + factorial(i));
        }
        
        // Fibonacci
        System.out.println("\n--- Fibonacci ---");
        for (int i = 0; i <= 15; i++) {
            System.out.println("F(" + i + ") = " + fibonacci(i));
        }
        
        // Tez Fibonacci
        System.out.println("\n--- Tez Fibonacci (Memoization) ---");
        System.out.println("F(50) = " + fibonacciMemo(50));
        
        // Daraja
        System.out.println("\n--- Daraja ---");
        System.out.println("2^10 = " + power(2, 10));
        System.out.println("3^5 = " + power(3, 5));
        System.out.println("2^10 (tez) = " + fastPower(2, 10));
        
        // String teskari
        System.out.println("\n--- String teskari ---");
        System.out.println("reverse(\"hello\") = " + reverseString("hello"));
        
        // Hanoi minoralari
        System.out.println("\n--- Hanoi minoralari (3 disk) ---");
        towerOfHanoi(3, 'A', 'C', 'B');
        
        // Natijalar
        System.out.println("\n--- Subset generatsiya ---");
        generateSubsets(new int[]{1, 2, 3}, 0, new java.util.ArrayList<>());
    }
}
