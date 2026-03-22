package data.structures.linkedlist;

/**
 * SinglyLinkedList - Yagona bog'langan ro'yxat (Singly Linked List).
 * 
 * NIMA UCHUN LINKED LIST KERAK?
 * Massivlarning eng katta kamchiligi - o'rtasiga element qo'shish yoki o'chirish
 * juda sekin (O(n)). Linked List bu muammoni hal qiladi.
 * 
 * LINKED LIST QANDAY ISHLAYDI?
 * Har bir element (Node) ikki qismdan iborat:
 * 1. data - haqiqiy ma'lumot (masalan, son, matn)
 * 2. next - keyingi elementga bog'lanish (reference/pointer)
 * 
 * TASAVVUR QILING:
 * [Data|Next] -> [Data|Next] -> [Data|Next] -> null
 * 
 * Bu zanjir (chain) kabi - har bir halqa keyingisiga bog'langan.
 * 
 * AFZALLIKLARI:
 * - O'rtasiga element qo'shish: O(1) - faqat bog'lanishlarni o'zgartirish
 * - O'rtasidan element o'chirish: O(1) - faqat bog'lanishlarni o'zgartirish
 * - Doimiy xotira: Har qanday hajmda qo'shish mumkin
 * 
 * KAMCHILIKLARI:
 * - Elementga indeks bo'yicha murojaat: O(n) - boshidan boshlab ketish kerak
 * - Xotira sarfi: Har bir element uchun qo'shimcha reference saqlanadi
 * 
 * MURAKKABLIK:
 * - Boshiga qo'shish: O(1)
 * - Oxiriga qo'shish: O(n) (tail yo'q bo'lsa) yoki O(1) (tail bo'lsa)
 * - O'rtasiga qo'shish: O(n) - joy topish uchun
 * - Qidirish: O(n) - boshidan boshlab qidirish kerak
 * - Element o'chirish: O(n) - oldingisini topish kerak
 * 
 * @author DSA Project
 */
public class SinglyLinkedList<T> {

    // ==================== NODE (HALQA) ====================
    
    /**
     * Node - Linked List ning asosiy bloki (halqasi).
     * 
     * Har bir Node ikki narsani saqlaydi:
     * 1. Ma'lumot (data) - biz saqlamoqchi bo'lgan narsa
     * 2. Bog'lanish (next) - keyingi Node ga reference
     * 
     * NIMA UCHUN STATIC?
     * Node tashqarida yaratilishi kerak, chunki u linked listning ichki qismi.
     * Static qilsak, tashqaridan foydalanish mumkin bo'ladi.
     */
    private static class Node<E> {
        E data;           // Ma'lumot - qanday turdagi bo'lishi mumkin
        Node<E> next;     // Keyingi Node ga bog'lanish

        /**
         * Yangi Node yaratish.
         * 
         * @param data - saqlanadigan ma'lumot
         */
        Node(E data) {
            this.data = data;
            this.next = null; // Dastlab keyingisi yo'q (null)
        }
    }

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Ro'yxatning boshi - birinchi element.
     * Bu "darvoza" (gate) - ro'yxatga kirish shu yerdan boshlanadi.
     * 
     * NIMA UCHUN HEAD KERAK?
     * Agar head yo'qolsa, butun ro'yxatga kirib bo'lmaydi!
     * Shuning uchun head ni har doim saqlab qolish kerak.
     */
    private Node<T> head;
    
    /**
     * Ro'yxatdagi elementlar soni.
     * Har qo'shish/o'chirishda yangilash kerak.
     */
    private int size;

    // ==================== YARATISH ====================
    
    /**
     * Bo'sh linked list yaratish.
     * 
     * NIMA BO'LADI?
     * 1. head = null - hali elementlar yo'q
     * 2. size = 0 - elementlar soni 0
     */
    public SinglyLinkedList() {
        this.head = null; // Bo'sh ro'yxat
        this.size = 0;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Ro'yxatning BOSHIGA element qo'shish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Yangi Node yaratamiz
     * 2. Yangi Node ning next ni hozirgi head ga bog'laymiz
     * 3. Head ni yangi Node ga o'zgartiramiz
     * 
     * MISOL:
     * Oldin: [1] -> [2] -> [3] -> null
     * Yangi: 0
     * Keyin: [0] -> [1] -> [2] -> [3] -> null
     * 
     * TEZLIK: O(1) - faqat 2 ta amal, elementlar soniga bog'liq emas!
     * 
     * @param element - qo'shiladigan element
     */
    public void addFirst(T element) {
        // 1-QADAM: Yangi Node yaratamiz
        Node<T> newNode = new Node<>(element);
        
        // 2-QADAM: Yangi Node ni hozirgi head ga bog'laymiz
        // Bu juda muhim! Agar bu qadamni unutsak, eski ro'yxat yo'qoladi
        newNode.next = head;
        
        // 3-QADAM: Head ni yangi Node ga o'zgartiramiz
        // Endi ro'yxat yangi Node dan boshlanadi
        head = newNode;
        
        // 4-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Ro'yxatning OXIRIGA element qo'shish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Agar ro'yxat bo'sh bo'lsa, addFirst() ishlatamiz
     * 2. Aks holda, oxirgi elementgacha boramiz
     * 3. Oxirgi elementning next ni yangi Node ga bog'laymiz
     * 
     * MISOL:
     * Oldin: [1] -> [2] -> [3] -> null
     * Yangi: 4
     * Keyin: [1] -> [2] -> [3] -> [4] -> null
     * 
     * TEZLIK: O(n) - oxirgigacha borish kerak
     * 
     * @param element - qo'shiladigan element
     */
    public void addLast(T element) {
        // Bo'sh ro'yxat tekshiriladi
        if (head == null) {
            addFirst(element); // Birinchi elementni qo'shamiz
            return;
        }
        
        // 1-QADAM: Yangi Node yaratamiz
        Node<T> newNode = new Node<>(element);
        
        // 2-QADAM: Oxirgi elementgacha boramiz
        Node<T> current = head;
        while (current.next != null) {
            // current ni keyingiga o'tkazamiz
            // Bu "sargardonlik" (traversal) - ro'yxat bo'ylab yurish
            current = current.next;
        }
        
        // 3-QADAM: Oxirgi elementning next ni yangi Node ga bog'laymiz
        current.next = newNode;
        
        // 4-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Ma'lum indeksga element qo'shish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Indeksni tekshiramiz
     * 2. Agar indeks 0 bo'lsa, addFirst() ishlatamiz
     * 3. Aks holda, indeksdan oldingi elementgacha boramiz
     * 4. Yangi Node ni o'sha joyga joylashtiramiz
     * 
     * MISOL (indeks=2 ga 99 qo'shish):
     * Oldin: [1] -> [2] -> [3] -> [4] -> null
     * Keyin:  [1] -> [2] -> [99] -> [3] -> [4] -> null
     * 
     * TEZLIK: O(n) - indeksgacha borish kerak
     * 
     * @param index - qo'shiladigan joy indeksi
     * @param element - qo'shiladigan element
     * @throws IndexOutOfBoundsException - indeks noto'g'ri bo'lsa
     */
    public void addAt(int index, T element) {
        // Indeksni tekshiramiz
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                "Indeks chegaradan tashqarida: " + index + ". Hajm: " + size
            );
        }
        
        // Boshiga qo'shish kerak bo'lsa
        if (index == 0) {
            addFirst(element);
            return;
        }
        
        // 1-QADAM: Indeksdan oldingi elementgacha boramiz
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        // 2-QADAM: Yangi Node yaratamiz
        Node<T> newNode = new Node<>(element);
        
        // 3-QADAM: Bog'lanishlarni o'zgartiramiz
        // Oldingi -> Yangi -> Keyingi
        newNode.next = current.next; // Yangi Node keyingisiga bog'lanadi
        current.next = newNode;      // Oldingi element yangisiga bog'lanadi
        
        // 4-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Birinchi elementni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Head ni keyingi elementga o'tkazamiz
     * 2. Eski head GC tomonidan tozalanadi
     * 
     * MISOL:
     * Oldin: [1] -> [2] -> [3] -> null
     * Keyin:  [2] -> [3] -> null
     * 
     * TEZLIK: O(1) - faqat bitta amal!
     * 
     * @return o'chirilgan element
     * @throws RuntimeException - ro'yxat bo'sh bo'lsa
     */
    public T removeFirst() {
        if (head == null) {
            throw new RuntimeException("Linked List bo'sh!");
        }
        
        // O'chirilgan elementni saqlab qoramiz
        T removedData = head.data;
        
        // Head ni keyingiga o'tkazamiz
        head = head.next;
        
        // Elementlar sonini 1 ga kamaytiramiz
        size--;
        
        return removedData;
    }

    /**
     * Ma'lum indeksdagi elementni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Indeksni tekshiramiz
     * 2. Agar indeks 0 bo'lsa, removeFirst() ishlatamiz
     * 3. Aks holda, o'chiriladigan elementdan oldingisini topamiz
     * 4. Bog'lanishlarni o'zgartiramiz
     * 
     * MISOL (indeks=1 ni o'chirish):
     * Oldin: [1] -> [2] -> [3] -> [4] -> null
     * Keyin:  [1] -> [3] -> [4] -> null
     * 
     * TEZLIK: O(n) - indeksdan oldingigacha borish kerak
     * 
     * @param index - o'chiriladigan element indeksi
     * @return o'chirilgan element
     */
    public T removeAt(int index) {
        // Indeksni tekshiramiz
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Indeks chegaradan tashqarida: " + index + ". Hajm: " + size
            );
        }
        
        // Boshidagini o'chirish kerak bo'lsa
        if (index == 0) {
            return removeFirst();
        }
        
        // 1-QADAM: Indeksdan oldingi elementgacha boramiz
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        // 2-QADAM: O'chiriladigan elementni topamiz
        Node<T> toRemove = current.next;
        
        // 3-QADAM: Bog'lanishlarni o'zgartiramiz
        // Oldingi -> Keyingi (o'rtadagi element "chekib" qo'yiladi)
        current.next = toRemove.next;
        
        // 4-QADAM: Elementlar sonini 1 ga kamaytiramiz
        size--;
        
        return toRemove.data;
    }

    /**
     * Ma'lum qiymatdagi birinchi elementni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Boshidan boshlab qidiramiz
     * 2. Topilganda, oldingisini topib, bog'lanishni o'zgartiramiz
     * 
     * TEZLIK: O(n) - oxirigacha qidirish kerak bo'lishi mumkin
     * 
     * @param element - o'chiriladigan element
     * @return true - topildi va o'chirildi, false - topilmadi
     */
    public boolean remove(T element) {
        // Bo'sh ro'yxat tekshiriladi
        if (head == null) return false;
        
        // Boshidagi element tekshiriladi
        if (head.data.equals(element)) {
            removeFirst();
            return true;
        }
        
        // Qolgan elementlarni qidiramiz
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                // Topdik! O'chiramiz
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        
        return false; // Element topilmadi
    }

    // ==================== QIDIRISH ====================
    
    /**
     * Indeks bo'yicha element olish.
     * 
     * TEZLIK: O(n) - boshidan boshlab indeksgacha borish kerak
     * 
     * @param index - element indeksi
     * @return o'sha indeksdagi element
     */
    public T get(int index) {
        // Indeksni tekshiramiz
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Indeks chegaradan tashqarida: " + index + ". Hajm: " + size
            );
        }
        
        // Boshidan boshlab indeksgacha boramiz
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current.data;
    }

    /**
     * Element qaysi indekda ekanini topish.
     * 
     * TEZLIK: O(n) - boshidan boshlab qidirish kerak
     * 
     * @param element - qidirilayotgan element
     * @return element indeksi yoki -1 (topilmasa)
     */
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            if (current.data.equals(element)) {
                return index; // Topdik!
            }
            current = current.next;
            index++;
        }
        
        return -1; // Element topilmadi
    }

    /**
     * Element mavjudligini tekshirish.
     * 
     * @param element - tekshirilayotgan element
     * @return true - mavjud, false - mavjud emas
     */
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Ro'yxat bo'shligini tekshirish.
     * 
     * @return true - bo'sh, false - elementlar mavjud
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Elementlar sonini qaytarish.
     * 
     * @return elementlar soni
     */
    public int size() {
        return size;
    }

    /**
     * Ro'yxatni tozalash.
     * 
     * TEZLIK: O(1) - faqat head va size ni o'zgartiramiz
     */
    public void clear() {
        head = null; // Barcha bog'lanishlar uziladi
        size = 0;
        // GC eski Node larni tozalaydi
    }

    /**
     * Ro'yxatni teskari tartibda qaytarish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Har bir elementni boshiga qo'shamiz
     * 2. Natija teskari tartibda bo'ladi
     * 
     * MISOL:
     * Oldin: [1] -> [2] -> [3] -> null
     * Keyin:  [3] -> [2] -> [1] -> null
     * 
     * TEZLIK: O(n) - barcha elementlarni qayta ishlash kerak
     */
    public void reverse() {
        Node<T> prev = null;      // Oldingi element
        Node<T> current = head;   // Joriy element
        Node<T> next;             // Keyingi element
        
        while (current != null) {
            next = current.next;  // Keyingisini saqlab qoramiz
            current.next = prev;  // Jorisni oldingisiga bog'laymiz (teskari)
            prev = current;       // Jorisni prev ga o'tkazamiz
            current = next;       // Keyingisiga o'tamiz
        }
        
        // Prev endi oxirgi element - u yangi head
        head = prev;
    }

    /**
     * Ro'yxatni matn ko'rinishida qaytarish.
     * 
     * @return ro'yxatning matn ko'rinishi
     */
    @Override
    public String toString() {
        if (head == null) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== SinglyLinkedList Sinov ===\n");
        
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        
        // Elementlar qo'shamiz
        System.out.println("--- Boshiga elementlar qo'shish ---");
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        System.out.println("Ro'yxat: " + list); // [10 -> 20 -> 30]
        
        // Oxiriga element qo'shamiz
        System.out.println("\n--- Oxiriga elementlar qo'shish ---");
        list.addLast(40);
        list.addLast(50);
        System.out.println("Ro'yxat: " + list); // [10 -> 20 -> 30 -> 40 -> 50]
        
        // Indeksga qo'shamiz
        System.out.println("\n--- Indeksga element qo'shish ---");
        list.addAt(2, 25);
        System.out.println("2-indeksga 25 qo'shildi: " + list);
        
        // Element olamiz
        System.out.println("\n--- Element olish ---");
        System.out.println("0-indeks: " + list.get(0));
        System.out.println("3-indeks: " + list.get(3));
        
        // Element qidiramiz
        System.out.println("\n--- Element qidirish ---");
        System.out.println("30 indeksi: " + list.indexOf(30));
        System.out.println("99 indeksi: " + list.indexOf(99));
        System.out.println("40 mavjudmi: " + list.contains(40));
        
        // Element o'chiramiz
        System.out.println("\n--- Element o'chirish ---");
        System.out.println("Bosh o'chirildi: " + list.removeFirst());
        System.out.println("2-indeks o'chirildi: " + list.removeAt(2));
        System.out.println("50 o'chirildi: " + list.remove(50));
        System.out.println("O'chirishdan keyin: " + list);
        
        // Teskari tartib
        System.out.println("\n--- Teskari tartib ---");
        list.reverse();
        System.out.println("Teskari: " + list);
        
        // Ro'yxat haqida ma'lumot
        System.out.println("\n--- Ro'yxat ma'lumotlari ---");
        System.out.println("Hajm: " + list.size());
        System.out.println("Bo'shmi: " + list.isEmpty());
    }
}