package data.structures.queue;

/**
 * Queue - Navbat (FIFO: First In, First Out).
 * 
 * NIMA UCHUN QUEUE KERAK?
 * Queue "FIFO" tamoilasi bilan ishlaydi. Ya'ni, birinchi qo'shilgan element
 * birinchi chiqariladi. Bu real hayotdagi navbat kabi:
 * - Do'konda navbat
 * - Printerdagi hujjatlar navbati
 * - Operatsion tizimda vazifalar navbati
 * 
 * ASOSIY AMALLAR:
 * - enqueue(element) - navbatga qo'shish (oringa turish)
 * - dequeue() - navbatdan olish (navbatdan chiqish)
 * - peek() - birinchi elementni ko'rish (olmasdan)
 * - isEmpty() - navbat bo'shligini tekshirish
 * 
 * MURAKKABLIK:
 * - Barcha asosiy amallar O(1) - juda tez!
 * 
 * QUEUE TURLARI:
 * 1. Simple Queue - oddiy navbat
 * 2. Circular Queue - halqaviy navbat (xotira samarali)
 * 3. Priority Queue - ustuvorlik navbati (eng muhim birinchi)
 * 4. Deque - ikki tomonlama navbat
 * 
 * @author DSA Project
 */
public class Queue<T> {

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Node - linked list elementi.
     */
    private static class Node<E> {
        E data;
        Node<E> next;
        
        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Navbatning BOSHI - birinchi element.
     * Bu yerdan elementlar chiqariladi (dequeue).
     */
    private Node<T> front;
    
    /**
     * Navbatning OXIRI - oxirgi element.
     * Bu yerga elementlar qo'shiladi (enqueue).
     * 
     * NIMA UCHUN TAIL KERAK?
     * Agar tail yo'q bo'lsa, har safar oxiriga qo'shish uchun
     * boshidan boshlab oxirigacha borish kerak (O(n)).
     * Tail bilan bu O(1) bo'ladi!
     */
    private Node<T> rear;
    
    /**
     * Navbatdagi elementlar soni.
     */
    private int size;

    // ==================== YARATISH ====================
    
    /**
     * Bo'sh navbat yaratish.
     */
    public Queue() {
        this.front = null; // Bo'sh navbat
        this.rear = null;
        this.size = 0;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Navbatga element qo'shish (enqueue).
     * 
     * QANDAY ISHLAYDI?
     * 1. Yangi Node yaratamiz
     * 2. Agar navbat bo'sh bo'lsa, front va rear ni yangi Node ga o'rnatamiz
     * 3. Aks holda, rear ning next ni yangi Node ga bog'laymiz
     * 4. Rear ni yangi Node ga o'zgartiramiz
     * 
     * MISOL:
     * Oldin: [A] -> [B] -> null   (front=A, rear=B)
     * Enqueue C: [A] -> [B] -> [C] -> null   (front=A, rear=C)
     * 
     * TEZLIK: O(1) - faqat 3-4 ta amal!
     * 
     * @param element - qo'shiladigan element
     */
    public void enqueue(T element) {
        // 1-QADAM: Yangi Node yaratamiz
        Node<T> newNode = new Node<>(element);
        
        // 2-QADAM: Navbat bo'shligini tekshiramiz
        if (isEmpty()) {
            // Bo'sh navbat - front va rear bir xil
            front = newNode;
            rear = newNode;
        } else {
            // 3-QADAM: Rear ning next ni yangi Node ga bog'laymiz
            rear.next = newNode;
            // 4-QADAM: Rear ni yangi Node ga o'zgartiramiz
            rear = newNode;
        }
        
        // 5-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Navbatdan element olish (dequeue).
     * 
     * QANDAY ISHLAYDI?
     * 1. Front elementning ma'lumotini saqlab qoramiz
     * 2. Front ni keyingi elementga o'tkazamiz
     * 3. Agar navbat bo'sh bo'lib qolsa, rear ni ham null qilamiz
     * 
     * MISOL:
     * Oldin: [A] -> [B] -> [C] -> null   (front=A, rear=C)
     * Dequeue: [B] -> [C] -> null   (front=B, rear=C, A qaytariladi)
     * 
     * TEZLIK: O(1) - faqat 2-3 ta amal!
     * 
     * @return olingan element
     * @throws RuntimeException - navbat bo'sh bo'lsa
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Navbat bo'sh! Dequeue qilib bo'lmaydi.");
        }
        
        // 1-QADAM: Front elementning ma'lumotini saqlab qoramiz
        T dequeuedData = front.data;
        
        // 2-QADAM: Front ni keyingi elementga o'tkazamiz
        front = front.next;
        
        // 3-QADAM: Agar navbat bo'sh bo'lib qolsa, rear ni ham null qilamiz
        if (front == null) {
            rear = null;
        }
        
        // 4-QADAM: Elementlar sonini 1 ga kamaytiramiz
        size--;
        
        return dequeuedData;
    }

    /**
     * Birinchi elementni ko'rish (peek).
     * 
     * NIMA UCHUN FARQI BOR?
     * dequeue() elementni O'CHIRADI, peek() esa FAQAT KO'RADI.
     * 
     * TEZLIK: O(1) - faqat bir amal
     * 
     * @return birinchi element
     * @throws RuntimeException - navbat bo'sh bo'lsa
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Navbat bo'sh! Peek qilib bo'lmaydi.");
        }
        return front.data; // Faqat ma'lumotni qaytaramiz
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Navbat bo'shligini tekshirish.
     * 
     * @return true - bo'sh, false - elementlar mavjud
     */
    public boolean isEmpty() {
        return front == null; // Agar front null bo'lsa, bo'sh
    }

    /**
     * Navbatdagi elementlar sonini qaytarish.
     * 
     * @return elementlar soni
     */
    public int size() {
        return size;
    }

    /**
     * Navbatni tozalash.
     * 
     * TEZLIK: O(1) - faqat front, rear va size ni o'zgartiramiz
     */
    public void clear() {
        front = null; // Barcha bog'lanishlar uziladi
        rear = null;
        size = 0;
        // GC eski Node larni tozalaydi
    }

    /**
     * Navbatni matn ko'rinishida qaytarish.
     * 
     * @return navbatning matn ko'rinishi
     */
    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[Front -> ");
        
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        
        sb.append(" <- Rear]");
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Queue Sinov ===\n");
        
        Queue<String> queue = new Queue<>();
        
        // Elementlar qo'shamiz
        System.out.println("--- Elementlar qo'shish (enqueue) ---");
        queue.enqueue("Birinchi");
        queue.enqueue("Ikkinchi");
        queue.enqueue("Uchinchi");
        System.out.println("Navbat: " + queue);
        System.out.println("Hajm: " + queue.size());
        
        // Birinchi elementni ko'ramiz
        System.out.println("\n--- Birinchi element (peek) ---");
        System.out.println("Birinchisi: " + queue.peek());
        System.out.println("Navbat o'zgarmadi: " + queue);
        
        // Elementlar olamiz
        System.out.println("\n--- Elementlar olish (dequeue) ---");
        System.out.println("Olindi: " + queue.dequeue());
        System.out.println("Navbat: " + queue);
        System.out.println("Olindi: " + queue.dequeue());
        System.out.println("Navbat: " + queue);
        
        // Tekshiramiz
        System.out.println("\n--- Tekshirish ---");
        System.out.println("Bo'shmi: " + queue.isEmpty());
        System.out.println("Hajm: " + queue.size());
        
        // Tozalaymiz
        System.out.println("\n--- Tozalash ---");
        queue.clear();
        System.out.println("Tozalandi. Bo'shmi: " + queue.isEmpty());
    }
}