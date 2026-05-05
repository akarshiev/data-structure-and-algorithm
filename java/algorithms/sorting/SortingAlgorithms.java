package algorithms.sorting;

/**
 * SortingAlgorithms - Saralash algoritmlari to'plami.
 * 
 * NIMA UCHUN SARALASH MUHIM?
 * Saralash - bu ma'lumotlarni tartibga solish. Ko'p algoritmlar
 * (qidirish, birlashtirish va boshqalar) saralangan ma'lumotlarda
 * ancha tezroq ishlaydi.
 * 
 * SARALASH ALGORITMLARI VA ULARNING MURAKKABLIGI:
 * 
 * | Algoritm         | Eng yaxshi | O'rtacha    | Eng yomon  | Xotira   | Barqarorlik |
 * |------------------|------------|-------------|------------|----------|-------------|
 * | Bubble Sort      | O(n)       | O(n²)       | O(n²)      | O(1)     | Ha          |
 * | Selection Sort   | O(n²)      | O(n²)       | O(n²)      | O(1)     | Yo'q        |
 * | Insertion Sort   | O(n)       | O(n²)       | O(n²)      | O(1)     | Ha          |
 * | Merge Sort       | O(n log n) | O(n log n)  | O(n log n) | O(n)     | Ha          |
 * | Quick Sort       | O(n log n) | O(n log n)  | O(n²)      | O(log n) | Yo'q        |
 * | Heap Sort        | O(n log n) | O(n log n)  | O(n log n) | O(1)     | Yo'q        |
 * 
 * @author DSA Project
 */
public class SortingAlgorithms {

    // ==================== BUBBLE SORT (Pufakcha saralash) ====================
    
    /**
     * Bubble Sort - Har bir qadamda eng katta element "puflanib" oxiriga boradi.
     * 
     * QANDAY ISHLAYDI?
     * 1. Yonma-yon turgan elementlarni solishtiramiz
     * 2. Agar noto'g'ri tartibda bo'lsa, almashamiz
     * 3. Har bir iteratsiyada eng katta element oxiriga boradi
     * 4. Saralanmagan qism kamayaveradi
     * 
     * MISOL (5, 3, 8, 1, 2):
     * Iteratsiya 1: [5,3,8,1,2] -> [3,5,8,1,2] -> [3,5,8,1,2] -> [3,5,1,8,2] -> [3,5,1,2,8]
     * Iteratsiya 2: [3,5,1,2,8] -> [3,5,1,2,8] -> [3,1,5,2,8] -> [3,1,2,5,8]
     * Iteratsiya 3: [3,1,2,5,8] -> [1,3,2,5,8] -> [1,2,3,5,8]
     * Iteratsiya 4: [1,2,3,5,8] -> [1,2,3,5,8] (o'zgarmadi - TUGADI!)
     * 
     * TEZLIK: O(n²) o'rtacha va eng yomon holat
     * XOTIRA: O(1) - faqat bitta qo'shimcha o'zgaruvchi
     * 
     * @param arr - saralanadigan massiv
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        
        // Har bir iteratsiyada kamida bitta element o'z joyiga keladi
        // Shuning uchun n-1 marta takrorlaymiz
        for (int i = 0; i < n - 1; i++) {
            // Tezlashtirish: agar bir iteratsiyada almashuv bo'lmasa, tugadi
            boolean swapped = false;
            
            // Saralanmagan qismni tekshiramiz
            // Har iteratsiyada oxirgi i ta element allaqachon saralangan
            for (int j = 0; j < n - 1 - i; j++) {
                // Yonma-yon turgan elementlarni solishtiramiz
                if (arr[j] > arr[j + 1]) {
                    // Almashamiz
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Agar almashuv bo'lmasa, massiv allaqachon saralangan
            if (!swapped) break;
        }
    }

    // ==================== SELECTION SORT (Tanlash saralash) ====================
    
    /**
     * Selection Sort - Har qadamda eng kichik elementni topib, o'rniga qo'yadi.
     * 
     * QANDAY ISHLAYDI?
     * 1. Saralanmagan qismdan eng kichik elementni topamiz
     * 2. Uni saralanmagan qismning birinchisi bilan almashamiz
     * 3. Saralanmagan qism 1 element kamayadi
     * 
     * MISOL (5, 3, 8, 1, 2):
     * Qadam 1: Eng kichik = 1 (indeks 3), 5 bilan almashtiramiz -> [1, 3, 8, 5, 2]
     * Qadam 2: Eng kichik = 2 (indeks 4), 3 bilan almashtiramiz -> [1, 2, 8, 5, 3]
     * Qadam 3: Eng kichik = 3 (indeks 4), 8 bilan almashtiramiz -> [1, 2, 3, 5, 8]
     * Qadam 4: Eng kichik = 5 (indeks 3), 5 bilan almashtiramiz -> [1, 2, 3, 5, 8]
     * 
     * TEZLIK: O(n²) - barcha holatlar uchun bir xil
     * XOTIRA: O(1) - joyida saralash
     * 
     * @param arr - saralanadigan massiv
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Joriy pozitsiyadan boshlab eng kichik elementni topamiz
            int minIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // Yangi minimum topdik
                }
            }
            
            // Agar eng kichik element boshqa joyda bo'lsa, almashamiz
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    // ==================== INSERTION SORT (Kiritish saralash) ====================
    
    /**
     * Insertion Sort - Har elementni to'g'ri joyiga "kiritadi".
     * 
     * QANDAY ISHLAYDI?
     * 1. Ikkinchi elementdan boshlaymiz
     * 2. Joriy elementni oldingi saralangan qism bilan solishtiramiz
     * 3. To'g'ri joy topilguncha oldinga suramiz
     * 
     * MISOL (5, 3, 8, 1, 2):
     * [5, 3, 8, 1, 2] - 3 ni 5 dan oldin qo'yamiz -> [3, 5, 8, 1, 2]
     * [3, 5, 8, 1, 2] - 8 o'z joyida -> [3, 5, 8, 1, 2]
     * [3, 5, 8, 1, 2] - 1 ni 3 dan oldin qo'yamiz -> [1, 3, 5, 8, 2]
     * [1, 3, 5, 8, 2] - 2 ni 1 va 3 orasiga qo'yamiz -> [1, 2, 3, 5, 8]
     * 
     * TEZLIK: O(n²) o'rtacha, O(n) eng yaxshi (saralangan massiv)
     * XOTIRA: O(1)
     * 
     * NIMA UCHUN KICHIK MASSIVLAR UCHUN YAXSHI?
     * Kichik massivlarda Overhead kam, shuning uchun tezroq ishlaydi.
     * Java Arrays.sort() ham kichik massivlarda insertion sort ishlatadi.
     * 
     * @param arr - saralanadigan massiv
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        
        // Ikkinchi elementdan boshlaymiz (birinchi allaqachon "saralangan")
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // Kiritiladigan element
            int j = i - 1;
            
            // key dan kattaroq elementlarni 1 ga o'ngga suramiz
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // Elementni o'ngga suramiz
                j--;
            }
            
            // To'g'ri joyga qo'yamiz
            arr[j + 1] = key;
        }
    }

    // ==================== MERGE SORT (Birlashtirish saralash) ====================
    
    /**
     * Merge Sort - Massivni yarmiga bo'lib, saralab, birlashtiradi.
     * 
     * QANDAY ISHLAYDI?
     * 1. DIVIDE: Massivni ikki yarmiga bo'lamiz
     * 2. CONQUER: Har bir yarimni recursion bilan saralaymiz
     * 3. COMBINE: Ikkala saralangan yarimni birlashtiramiz
     * 
     * MISOL (5, 3, 8, 1):
     * Bo'lish: [5, 3, 8, 1] -> [5, 3] va [8, 1]
     * Bo'lish: [5, 3] -> [5] va [3]  |  [8, 1] -> [8] va [1]
     * Birlashtirish: [5] va [3] -> [3, 5]  |  [8] va [1] -> [1, 8]
     * Birlashtirish: [3, 5] va [1, 8] -> [1, 3, 5, 8]
     * 
     * TEZLIK: O(n log n) - BARCHA HOLATLAR UCHUN!
     * XOTIRA: O(n) - qo'shimcha massiv kerak
     * 
     * @param arr - saralanadigan massiv
     */
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return; // Bazaviy holat
        
        int mid = arr.length / 2;
        
        // Chap va o'ng yarimlarni ajratamiz
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];
        
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);
        
        // Recursion bilan har bir yarimni saralaymiz
        mergeSort(left);
        mergeSort(right);
        
        // Saralangan yarimlarni birlashtiramiz
        merge(arr, left, right);
    }

    /**
     * Ikkala saralangan massivni birlashtirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Ikkala massivning boshidan boshlaymiz
     * 2. Kichikroq elementni natijaga qo'shamiz
     * 3. Bittasi tugaguncha takrorlaymiz
     * 4. Qolgan elementlarni qo'shamiz
     * 
     * @param result - natija massivi
     * @param left - chap yarim
     * @param right - o'ng yarim
     */
    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        
        // Ikkala massivdagi elementlarni solishtiramiz
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        // Qolgan elementlarni qo'shamiz
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
    }

    // ==================== QUICK SORT (Tez saralash) ====================
    
    /**
     * Quick Sort - Pivot tanlab, massivni ikki qismga bo'ladi.
     * 
     * QANDAY ISHLAYDI?
     * 1. PIVOT: Bitta elementni "pivit" sifatida tanlaymiz
     * 2. PARTITION: Pivit dan kichikroqlar chapga, kattaroqlar o'nga
     * 3. Recursion: Har bir qismni qayta saralaymiz
     * 
     * MISOL (5, 3, 8, 1, 2), pivot = 5:
     * Kichikroqlar: [3, 1, 2]  |  Pivit: [5]  |  Kattaroqlar: [8]
     * [3, 1, 2] ni qayta saralash...
     * 
     * TEZLIK: O(n log n) o'rtacha, O(n²) eng yomon holat
     * XOTIRA: O(log n) - recursion stek
     * 
     * NIMA UCHUN O(n²) BO'LISHI MUMKIN?
     * Agar har safar eng kichik yoki eng katta element pivit bo'lsa,
     * massiv notekis bo'linadi va O(n²) bo'ladi.
     * 
     * @param arr - saralanadigan massiv
     * @param low - boshlang'ich indeks
     * @param high - oxirgi indeks
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Pivot ni to'g'ri joyga qo'yamiz
            int pivotIndex = partition(arr, low, high);
            
            // Pivot dan oldingi va keyingi qismlarni saralaymiz
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Massivni pivit bo'yicha ajratish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Oxirgi elementni pivit sifatida tanlaymiz
     * 2. Pivit dan kichikroq elementlarni chapga siljitamiz
     * 3. Pivit ni to'g'ri joyga qo'yamiz
     * 
     * @param arr - massiv
     * @param low - boshlang'ich indeks
     * @param high - oxirgi indeks
     * @return pivit indeksi
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Oxirgi element pivit
        int i = low - 1;       // Kichikroq elementlar chegarasi
        
        for (int j = low; j < high; j++) {
            // Agar j element pivit dan kichik yoki teng bo'lsa
            if (arr[j] <= pivot) {
                i++; // Chegarani kengaytiramiz
                // Elementni chap tomonga siljitamiz
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Pivit ni to'g'ri joyga qo'yamiz
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1; // Pivit indeksi
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Massivni ekranga chiqarish.
     */
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * Massivni nusxalash (har bir test uchun yangisini yaratish).
     */
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Sorting Algorithms Sinov ===\n");
        
        int[] original = {64, 34, 25, 12, 22, 11, 90};
        
        // Bubble Sort
        System.out.println("--- Bubble Sort ---");
        int[] arr = copyArray(original);
        System.out.print("Oldin: ");
        printArray(arr);
        bubbleSort(arr);
        System.out.print("Keyin: ");
        printArray(arr);
        
        // Selection Sort
        System.out.println("\n--- Selection Sort ---");
        arr = copyArray(original);
        System.out.print("Oldin: ");
        printArray(arr);
        selectionSort(arr);
        System.out.print("Keyin: ");
        printArray(arr);
        
        // Insertion Sort
        System.out.println("\n--- Insertion Sort ---");
        arr = copyArray(original);
        System.out.print("Oldin: ");
        printArray(arr);
        insertionSort(arr);
        System.out.print("Keyin: ");
        printArray(arr);
        
        // Merge Sort
        System.out.println("\n--- Merge Sort ---");
        arr = copyArray(original);
        System.out.print("Oldin: ");
        printArray(arr);
        mergeSort(arr);
        System.out.print("Keyin: ");
        printArray(arr);
        
        // Quick Sort
        System.out.println("\n--- Quick Sort ---");
        arr = copyArray(original);
        System.out.print("Oldin: ");
        printArray(arr);
        quickSort(arr, 0, arr.length - 1);
        System.out.print("Keyin: ");
        printArray(arr);
    }
}
