package data.structures.heap;

/**
 * MaxHeap - Maksimal kupyura (eng katta element yuqorida).
 * 
 * NIMA UCHUN HEAP KERAK?
 * Heap "priority" (ustuvorlik) asosida ishlaydi.
 * MaxHeap da eng katta element doim yuqorida (root).
 * 
 * QANDAY ISHLAYDI?
 * Heap to'liq ikkinchi darajali daraxt (complete binary tree):
 * 1. Har bir tugun ota tugundan KATTA yoki TENG
 * 2. Daraxt to'liq darajada (barcha darajalar to'liq)
 * 3. Oxirgi darajada chapdan o'ngga to'ldirilgan
 * 
 * MISOL (MaxHeap):
 *         80
 *        /  \
 *      70    60
 *     / \   / \
 *   50  40 30  20
 * 
 * Bu yerda 80 eng katta element va doim yuqorida.
 * 
 * MASSIVDA SAQLANISHI:
 * Daraxt massivda saqlanadi:
 * [80, 70, 60, 50, 40, 30, 20]
 * 
 * Indekslar:
 * - Ota: (i-1)/2
 * - Chap bola: 2*i + 1
 * - O'ng bola: 2*i + 2
 * 
 * FOYDALANISH:
 * 1. Priority Queue - eng muim element birinchi
 * 2. Heap Sort - samarali saralash
 * 3. Top K elements - eng katta/kichik K ta element
 * 
 * MURAKKABLIK:
 * - Qo'shish: O(log n)
 * - O'chirish (max): O(log n)
 * - Max ni olish: O(1)
 * 
 * @author DSA Project
 */
public class MaxHeap {

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Heap massivda saqlanadi.
     * NIMA UCHUN MASSIV?
     * 1. Xotira samarali - reference kerak emas
     * 2. Keshga mos - ketma-ket joylashgan
     * 3. Indekslar orqali tez kirish
     */
    private int[] heap;
    
    /**
     * Hozirgi elementlar soni.
     */
    private int size;
    
    /**
     * Massivning sig'imi.
     */
    private int capacity;

    // ==================== YARATISH ====================
    
    /**
     * MaxHeap yaratish.
     * 
     * @param capacity - massiv sig'imi
     */
    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Ota tugun indeksini hisoblash.
     * 
     * FORMULA: (i - 1) / 2
     * 
     * MISOL:
     * Indeks 1 -> (1-1)/2 = 0 (ota)
     * Indeks 2 -> (2-1)/2 = 0 (ota)
     * Indeks 3 -> (3-1)/2 = 1 (ota)
     * Indeks 4 -> (4-1)/2 = 1 (ota)
     * 
     * @param i - joriy indeks
     * @return ota indeksi
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Chap bola indeksini hisoblash.
     * 
     * FORMULA: 2*i + 1
     * 
     * @param i - ota indeksi
     * @return chap bola indeksi
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * O'ng bola indeksini hisoblash.
     * 
     * FORMULA: 2*i + 2
     * 
     * @param i - ota indeksi
     * @return o'ng bola indeksi
     */
    private int rightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * Ikki qiymatni almashtirish.
     * 
     * @param i - birinchi indeks
     * @param j - ikkinchi indeks
     */
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Element qo'shish (insert).
     * 
     * QANDAY ISHLAYDI?
     * 1. Elementni oxiriga qo'yamiz
     * 2. "Sift Up" (yukoriga siljitish) bajaramiz:
     *    - Ota tugun bilan solishtiramiz
     *    - Agar kattaroq bo'lsa, almashamiz
     *    - Root ga yetguncha takrorlaymiz
     * 
     * MISOL (70 qo'shish):
     * Oldin:    80          Qo'shish:   80
     *          /  \                   /  \
     *        70    60               70    60
     *       / \                   / \   /
     *     50  40                50  40 70
     *                                            (70 otaga siljidi)
     * TEZLIK: O(log n) - daraxt balandligi
     * 
     * @param data - qo'shiladigan element
     * @throws RuntimeException - heap to'lib qolgan bo'lsa
     */
    public void insert(int data) {
        if (size == capacity) {
            throw new RuntimeException("Heap to'lib qoldi!");
        }
        
        // 1-QADAM: Elementni oxiriga qo'yamiz
        heap[size] = data;
        int current = size;
        size++;
        
        // 2-QADAM: Sift Up - ota tugun bilan solishtiramiz
        // Agar joriy element ota tugundan kattaroq bo'lsa, almashamiz
        while (current > 0 && heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current); // Otaga ko'taramiz
        }
    }

    /**
     * Eng katta elementni o'chirish (extractMax).
     * 
     * QANDAY ISHLAYDI?
     * 1. Root (eng katta) ni saqlab qoramiz
     * 2. Oxirgi elementni root ga qo'yamiz
     * 3. "Sift Down" (pastga siljitish) bajaramiz:
     *    - Kattaroq bola bilan almashamiz
     *    - Yaprakka yetguncha takrorlaymiz
     * 
     * MISOL:
     * Oldin:    80          O'chirish:  70
     *          /  \                   /  \
     *        70    60               50    60
     *       / \   /                / \   /
     *     50  40 20              50  40 20
     * 
     * 1. 80 ni saqlaymiz
     * 2. 20 ni root ga qo'yamiz
     * 3. 20 ni 70 bilan almashamiz
     * 4. 20 ni 50 bilan almashamiz
     * 
     * TEZLIK: O(log n) - daraxt balandligi
     * 
     * @return eng katta element
     * @throws RuntimeException - heap bo'sh bo'lsa
     */
    public int extractMax() {
        if (size == 0) {
            throw new RuntimeException("Heap bo'sh!");
        }
        
        // 1-QADAM: Root ni saqlab qoramiz
        int max = heap[0];
        
        // 2-QADAM: Oxirgi elementni root ga qo'yamiz
        heap[0] = heap[size - 1];
        size--;
        
        // 3-QADAM: Sift Down - pastga siljitamiz
        siftDown(0);
        
        return max;
    }

    /**
     * Pastga siljitish operatsiyasi.
     * 
     * QANDAY ISHLAYDI?
     * 1. Kattaroq bola topamiz (chap yoki o'ng)
     * 2. Agar joriy element kattaroq boladan kichik bo'lsa, almashamiz
     * 3. Yaprakka yetguncha takrorlaymiz
     * 
     * @param i - boshlang'ich indeks
     */
    private void siftDown(int i) {
        int largest = i; // Eng katta element indeksi
        
        int left = leftChild(i);
        int right = rightChild(i);
        
        // Chap bola kattaroq bo'lsa
        if (left < size && heap[left] > heap[largest]) {
            largest = left;
        }
        
        // O'ng bola kattaroq bo'lsa
        if (right < size && heap[right] > heap[largest]) {
            largest = right;
        }
        
        // Agar eng katta joriy emas bo'lsa, almashamiz
        if (largest != i) {
            swap(i, largest);
            siftDown(largest); // Recursion bilan davom ettiramiz
        }
    }

    /**
     * Eng katta elementni ko'rish (peek).
     * 
     * TEZLIK: O(1) - faqat root ni qaytaramiz
     * 
     * @return eng katta element
     */
    public int peek() {
        if (size == 0) {
            throw new RuntimeException("Heap bo'sh!");
        }
        return heap[0]; // Root doim eng katta
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Heap bo'shligini tekshirish.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Heap elementlar sonini qaytarish.
     */
    public int size() {
        return size;
    }

    /**
     * Heap ni matn ko'rinishida qaytarish.
     */
    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== MaxHeap Sinov ===\n");
        
        MaxHeap heap = new MaxHeap(10);
        
        // Elementlar qo'shamiz
        System.out.println("--- Elementlar qo'shish ---");
        heap.insert(50);
        heap.insert(30);
        heap.insert(70);
        heap.insert(20);
        heap.insert(40);
        heap.insert(60);
        heap.insert(80);
        
        System.out.println("Heap: " + heap);
        System.out.println("Eng katta: " + heap.peek());
        
        // Elementlar olamiz
        System.out.println("\n--- Elementlar olish (extractMax) ---");
        System.out.println("O'chirildi: " + heap.extractMax());
        System.out.println("Heap: " + heap);
        
        System.out.println("O'chirildi: " + heap.extractMax());
        System.out.println("Heap: " + heap);
        
        // Tekshiramiz
        System.out.println("\n--- Tekshirish ---");
        System.out.println("Hajm: " + heap.size());
        System.out.println("Bo'shmi: " + heap.isEmpty());
    }
}