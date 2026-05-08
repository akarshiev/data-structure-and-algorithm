package algorithms.searching;

/**
 * SearchingAlgorithms - Qidiruv algoritmlari to'plami.
 * 
 * NIMA UCHUN QIDIRISH MUHIM?
 * Qidirish - ma'lumotlar ichidan kerakli elementni topish.
 * Bu dasturlashning eng asosiy amallaridan biri.
 * 
 * QIDIRISH ALGORITMLARI:
 * 
 * | Algoritm         | Murakkablik  | Shart                |
 * |------------------|--------------|----------------------|
 * | Linear Search    | O(n)         | Har qanday massiv     |
 * | Binary Search    | O(log n)     | Saralangan massiv     |
 * | Jump Search      | O(√n)        | Saralangan massiv     |
 * | Interpolation    | O(log n)*    | Teng taqsimlangan    |
 * 
 * @author DSA Project
 */
public class SearchingAlgorithms {

    // ==================== LINEAR SEARCH (Chiziqli qidirish) ====================
    
    /**
     * Linear Search - Har bir elementni birma-bir tekshiradi.
     * 
     * QANDAY ISHLAYDI?
     * 1. Birinchi elementdan boshlaymiz
     * 2. Har bir elementni maqsad element bilan solishtiramiz
     * 3. Topilsa, indeksini qaytaramiz
     * 4. Barchasi tugasa, -1 qaytaramiz
     * 
     * TEZLIK: O(n) - eng yomon holatda barcha elementlarni tekshirish kerak
     * 
     * AFZALLIGI: Saralanmagan massivlarda ham ishlaydi!
     * 
     * @param arr - qidirilayotgan massiv
     * @param target - qidirilayotgan element
     * @return element indeksi yoki -1
     */
    public static int linearSearch(int[] arr, int target) {
        // Boshidan oxirigacha har bir elementni tekshiramiz
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Topdik! Indeksni qaytaramiz
            }
        }
        return -1; // Element topilmadi
    }

    // ==================== BINARY SEARCH (Ikkinchi darajali qidirish) ====================
    
    /**
     * Binary Search - Massivni yarmiga bo'lib qidiradi.
     * 
     * SHART: Massiv SARALANGAN bo'lishi shart!
     * 
     * QANDAY ISHLAYDI?
     * 1. Chap (left) va o'ng (right) chegaralarni belgilaymiz
     * 2. O'rtadagi (mid) elementni topamiz
     * 3. Agar mid == target bo'lsa, topdik
     * 4. Agar target < mid bo'lsa, chap yarmida qidiramiz
     * 5. Agar target > mid bo'lsa, o'ng yarmida qidiramiz
     * 6. left > right bo'lguncha takrorlaymiz
     * 
     * MISOL: [1, 3, 5, 7, 9, 11, 13], target = 7
     * Qadam 1: left=0, right=6, mid=3, arr[3]=7 -> TOPDIK!
     * 
     * MISOL: [1, 3, 5, 7, 9, 11, 13], target = 9
     * Qadam 1: left=0, right=6, mid=3, arr[3]=7, 9>7 -> o'ng yarm
     * Qadam 2: left=4, right=6, mid=5, arr[5]=11, 9<11 -> chap yarm
     * Qadam 3: left=4, right=4, mid=4, arr[4]=9 -> TOPDIK!
     * 
     * TEZLIK: O(log n) - har qadamda massiv yarmiga tushadi
     * 
     * @param arr - SARALANGAN massiv
     * @param target - qidirilayotgan element
     * @return element indeksi yoki -1
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        // left > right bo'lguncha davom ettiramiz
        while (left <= right) {
            // O'rtadagi indeksni hisoblaymiz
            // NIMA UCHUN left + (right - left) / 2?
            // left + right / 2 dan foydalanilsa, ortiqcha overflow bo'lishi mumkin
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid; // Topdik!
            } else if (arr[mid] < target) {
                // Target kattaroq - o'ng yarmida qidiramiz
                left = mid + 1;
            } else {
                // Target kichikroq - chap yarmida qidiramiz
                right = mid - 1;
            }
        }
        
        return -1; // Element topilmadi
    }

    // ==================== BINARY SEARCH (Recursion versiyasi) ====================
    
    /**
     * Binary Search - Recursion versiyasi.
     * 
     * NIMA UCHUN RECERSION?
     * Kod sodda va tushunarliroq bo'ladi.
     * Lekin xotira sarfi O(log n) bo'ladi (recursion stek uchun).
     * 
     * @param arr - saralangan massiv
     * @param target - qidirilayotgan element
     * @param left - chap chegara
     * @param right - o'ng chegara
     * @return element indeksi yoki -1
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        // Bazaviy holat: element topilmadi
        if (left > right) return -1;
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid; // Topdik!
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }

    // ==================== JUMP SEARCH (Sakrab qidirish) ====================
    
    /**
     * Jump Search - Belgilangan qadam bilan sakrab qidiradi.
     * 
     * QANDAY ISHLAYDI?
     * 1. √n qadam bilan sakrab o'tamiz
     * 2. Target dan kattaroq element topilganda, orqaga qaytamiz
     * 3. Orqada linearity qidirish bajaramiz
     * 
     * MISOL: [1, 3, 5, 7, 9, 11, 13, 15, 17], target = 13, step = 3
     * Sakrash: [1, 3, 5] -> [7, 9, 11] -> [13, 15, 17] (13 topildi!)
     * 
     * TEZLIK: O(√n)
     * 
     * @param arr - saralangan massiv
     * @param target - qidirilayotgan element
     * @return element indeksi yoki -1
     */
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.sqrt(n); // Optimal qadam
        int prev = 0;
        
        // Target dan kattaroq bo'lguncha sakraymiz
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.sqrt(n);
            if (prev >= n) return -1;
        }
        
        // Orqada linearity qidirish
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) return -1;
        }
        
        if (arr[prev] == target) return prev;
        return -1;
    }

    // ==================== INTERPOLATION SEARCH ====================
    
    /**
     * Interpolation Search - Teng taqsimlangan massivlar uchun.
     * 
     * QANDAY ISHLAYDI?
     * Oddiy binary search o'rniga, target ning ehtimoliy joyini
     * matematik formula bilan hisoblaymiz:
     * 
     * pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low])
     * 
     * Bu formula target qaysi joyda bo'lishini "taxmin qiladi".
     * 
     * TEZLIK: O(log log n) o'rtacha (teng taqsimlangan massivlarda)
     * 
     * @param arr - saralangan, teng taqsimlangan massiv
     * @param target - qidirilayotgan element
     * @return element indeksi yoki -1
     */
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high && target >= arr[low] && target <= arr[high]) {
            // Target ning ehtimoliy joyini hisoblaymiz
            int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);
            
            if (arr[pos] == target) {
                return pos; // Topdik!
            } else if (arr[pos] < target) {
                low = pos + 1; // O'ng tomonga qidiramiz
            } else {
                high = pos - 1; // Chap tomonga qidiramiz
            }
        }
        
        return -1; // Element topilmadi
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Searching Algorithms Sinov ===\n");
        
        int[] sortedArr = {2, 5, 8, 12, 16, 23, 38, 45, 67, 89};
        int[] unsortedArr = {64, 34, 25, 12, 22, 11, 90};
        
        // Linear Search
        System.out.println("--- Linear Search (Saralanmagan massiv) ---");
        System.out.println("Massiv: ");
        printArray(unsortedArr);
        int target = 22;
        int result = linearSearch(unsortedArr, target);
        System.out.println(target + " indeksi: " + result);
        
        // Binary Search
        System.out.println("\n--- Binary Search (Saralangan massiv) ---");
        System.out.println("Massiv: ");
        printArray(sortedArr);
        target = 23;
        result = binarySearch(sortedArr, target);
        System.out.println(target + " indeksi: " + result);
        
        // Binary Search (Recursion)
        System.out.println("\n--- Binary Search (Recursion) ---");
        target = 67;
        result = binarySearchRecursive(sortedArr, target, 0, sortedArr.length - 1);
        System.out.println(target + " indeksi: " + result);
        
        // Jump Search
        System.out.println("\n--- Jump Search ---");
        target = 38;
        result = jumpSearch(sortedArr, target);
        System.out.println(target + " indeksi: " + result);
        
        // Interpolation Search
        System.out.println("\n--- Interpolation Search ---");
        target = 45;
        result = interpolationSearch(sortedArr, target);
        System.out.println(target + " indeksi: " + result);
    }
    
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
