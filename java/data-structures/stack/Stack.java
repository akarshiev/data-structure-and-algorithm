package data.structures.stack;

/**
 * Stack - Stek (qatorning teskari versiyasi).
 * 
 * NIMA UCHUN STACK KERAK?
 * Stack "LIFO" (Last In, First Out) tamoilasi bilan ishlaydi.
 * Ya'ni, oxirgi qo'shilgan element birinchi chiqariladi.
 * 
 * REAL HAYOT MISOLLARI:
 * 1. Tarozi (stack of plates) - yuqorisidagi tarozini olasiz
 * 2. Brauzer tarixi - "Back" tugmasi oldingi sahifaga qaytaradi
 * 3. Undo funksiyasi - oxirgi amalni bekor qilish
 * 4. Rekursiya - funktsiya chaqiruvlari stekda saqlanadi
 * 
 * ASOSIY AMALLAR:
 * - push(element) - stekga qo'shish (yuklash)
 * - pop() - stekdan olish (tushirish)
 * - peek() - yuqoridagi elementni ko'rish (olmasdan)
 * - isEmpty() - stek bo'shligini tekshirish
 * 
 * MURAKKABLIK:
 * - Barcha asosiy amallar O(1) - juda tez!
 * 
 * @author DSA Project
 */
public class Stack<T> {

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Stekni linked list yordamida bajaramiz.
     * 
     * NIMA UCHUN LINKED LIST?
     * 1. Dynamic - o'lchami oldindan belgilanmagan
     * 2. O(1) qo'shish/o'chirish - massivga nisbatan
     * 3. Xotira samarali - faqat kerakli joy ajratiladi
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
     * Stekning "yuklari" - eng yuqoridagi element.
     * Bu "darvoza" (gate) - stekga kirish shu yerdan.
     */
    private Node<T> top;
    
    /**
     * Stekdagi elementlar soni.
     */
    private int size;

    // ==================== YARATISH ====================
    
    /**
     * Bo'sh stek yaratish.
     */
    public Stack() {
        this.top = null; // Bo'sh stek
        this.size = 0;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Stekga element qo'shish (push).
     * 
     * QANDAY ISHLAYDI?
     * 1. Yangi Node yaratamiz
     * 2. Yangi Node ning next ni hozirgi top ga bog'laymiz
     * 3. Top ni yangi Node ga o'zgartiramiz
     * 
     * MISOL:
     * Oldin: [A] -> [B] -> null   (top = A)
     * Push C: [C] -> [A] -> [B] -> null   (top = C)
     * 
     * TEZLIK: O(1) - faqat 2 ta amal!
     * 
     * @param element - qo'shiladigan element
     */
    public void push(T element) {
        // 1-QADAM: Yangi Node yaratamiz
        Node<T> newNode = new Node<>(element);
        
        // 2-QADAM: Yangi Node ni hozirgi top ga bog'laymiz
        newNode.next = top;
        
        // 3-QADAM: Top ni yangi Node ga o'zgartiramiz
        top = newNode;
        
        // 4-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Stekdan element olish (pop).
     * 
     * QANDAY ISHLAYDI?
     * 1. Top elementning ma'lumotini saqlab qoramiz
     * 2. Top ni keyingi elementga o'tkazamiz
     * 3. Eski top GC tomonidan tozalanadi
     * 
     * MISOL:
     * Oldin: [C] -> [A] -> [B] -> null   (top = C)
     * Pop:   [A] -> [B] -> null   (top = A, C qaytariladi)
     * 
     * TEZLIK: O(1) - faqat 2 ta amal!
     * 
     * @return olingan element
     * @throws RuntimeException - stek bo'sh bo'lsa
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stek bo'sh! Pop qilib bo'lmaydi.");
        }
        
        // 1-QADAM: Top elementning ma'lumotini saqlab qoramiz
        T poppedData = top.data;
        
        // 2-QADAM: Top ni keyingi elementga o'tkazamiz
        top = top.next;
        
        // 3-QADAM: Elementlar sonini 1 ga kamaytiramiz
        size--;
        
        return poppedData;
    }

    /**
     * Yuqoridagi elementni ko'rish (peek).
     * 
     * NIMA UCHUN FARQI BOR?
     * pop() elementni O'CHIRADI, peek() esa FAQAT KO'RADI.
     * 
     * TEZLIK: O(1) - faqat bir amal
     * 
     * @return yuqoridagi element
     * @throws RuntimeException - stek bo'sh bo'lsa
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stek bo'sh! Peek qilib bo'lmaydi.");
        }
        return top.data; // Faqat ma'lumotni qaytaramiz, o'zgartirmaymiz
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Stek bo'shligini tekshirish.
     * 
     * @return true - bo'sh, false - elementlar mavjud
     */
    public boolean isEmpty() {
        return top == null; // Agar top null bo'lsa, bo'sh
    }

    /**
     * Stekdagi elementlar sonini qaytarish.
     * 
     * @return elementlar soni
     */
    public int size() {
        return size;
    }

    /**
     * Stekni tozalash.
     * 
     * TEZLIK: O(1) - faqat top va size ni o'zgartiramiz
     */
    public void clear() {
        top = null; // Barcha bog'lanishlar uziladi
        size = 0;
        // GC eski Node larni tozalaydi
    }

    /**
     * Stekni matn ko'rinishida qaytarish.
     * 
     * @return stekning matn ko'rinishi
     */
    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[Top -> ");
        
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        
        sb.append(" <- Bottom]");
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== Stack Sinov ===\n");
        
        Stack<String> stack = new Stack<>();
        
        // Elementlar qo'shamiz
        System.out.println("--- Elementlar qo'shish (push) ---");
        stack.push("Birinchi");
        stack.push("Ikkinchi");
        stack.push("Uchinchi");
        System.out.println("Stek: " + stack);
        System.out.println("Hajm: " + stack.size());
        
        // Yuqoridagi elementni ko'ramiz
        System.out.println("\n--- Yuqoridagi element (peek) ---");
        System.out.println("Yuqorisi: " + stack.peek());
        System.out.println("Stek o'zgarmadi: " + stack);
        
        // Elementlar olamiz
        System.out.println("\n--- Elementlar olish (pop) ---");
        System.out.println("Olindi: " + stack.pop());
        System.out.println("Stek: " + stack);
        System.out.println("Olindi: " + stack.pop());
        System.out.println("Stek: " + stack);
        
        // Tekshiramiz
        System.out.println("\n--- Tekshirish ---");
        System.out.println("Bo'shmi: " + stack.isEmpty());
        System.out.println("Hajm: " + stack.size());
        
        // Tozalaymiz
        System.out.println("\n--- Tozalash ---");
        stack.clear();
        System.out.println("Tozalandi. Bo'shmi: " + stack.isEmpty());
    }
}