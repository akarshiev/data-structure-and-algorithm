package data.structures.hashmap;

/**
 * CustomHashMap - Java'dagi HashMap ni noldan yozilgan versiyasi.
 * 
 * NIMA UCHUN HASH MAP KERAK?
 * HashMap "kalit-qiymat" (key-value) juftliklarini saqlaydi.
 * Eng muhim afzalligi - elementlarni O(1) vaqtda topish mumkin!
 * 
 * QANDAY ISHLAYDI?
 * 1. Kalit (key) "hash function" orqali raqamga (index) aylanadi
 * 2. Indeks bo'yicha "bucket" (quti) ga joylashtiriladi
 * 3. Qidirganda ham shu yo'l bilan tezda topiladi
 * 
 * MISOL:
 * HashMap<String, Integer> ages = new HashMap<>();
 * ages.put("Ali", 25);    // "Ali" -> 25
 * ages.put("Vali", 30);   // "Vali" -> 30
 * int aliAge = ages.get("Ali"); // 25 - O(1) tezlikda!
 * 
 * HASH FUNCTION:
 * Bu kalitni raqamga aylantiradi. Masalan:
 * "Ali".hashCode() -> 65 + 108 + 105 = 278 -> 278 % 16 = 10 (indeks)
 * 
 * COLLISION (TO'QNASHUV):
 * Ba'zida ikki kalit bir xil indeksga tushishi mumkin.
 * Biz "chaining" usulini ishlatamiz - har bir bucket da linked list saqlaymiz.
 * 
 * MURAKKABLIK:
 * - put(): O(1) amortizatsiya
 * - get(): O(1) o'rtacha, O(n) eng yomon holat
 * - remove(): O(1) o'rtacha, O(n) eng yomon holat
 * 
 * @author DSA Project
 */
public class CustomHashMap<K, V> {

    // ==================== NODE (BUCKET ELEMENTI) ====================
    
    /**
     * Node - HashMap ning asosiy bloki.
     * Har bir Node kalit-qiymat juftligini saqlaydi.
     */
    private static class Node<K, V> {
        K key;        // Kalit - elementni topish uchun ishlatiladi
        V value;      // Qiymat - saqlanadigan ma'lumot
        Node<K, V> next; // Keyingi Node (chaining uchun)

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Bucketlar massivi - har bir bucket linked list.
     * NIMA UCHUN MASSIV?
     * Massivga indeks bo'yicha O(1) kirish mumkin.
     */
    private Node<K, V>[] buckets;
    
    /**
     * HashMap dagi elementlar soni.
     */
    private int size;
    
    /**
     * Bucketlar soni - default 16.
     * NIMA UCHUN 16?
     * 2 ning darajasi bo'lsa, hash ni modulga ajratish tezroq.
     */
    private static final int DEFAULT_CAPACITY = 16;
    
    /**
     * Yuklama koeffitsienti - qachon kengaytirish kerak.
     * 0.75 dan oshsa, kengaytiramiz.
     * NIMA UCHUN 0.75?
     * Bu empirik qiymat - xotira va tezlik o'rtasida muvozanat.
     */
    private static final double LOAD_FACTOR = 0.75;

    // ==================== YARATISH ====================
    
    /**
     * Bo'sh HashMap yaratish.
     */
    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.buckets = new Node[DEFAULT_CAPACITY]; // 16 ta bo'sh bucket
        this.size = 0;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Element qo'shish yoki yangilash (put).
     * 
     * QANDAY ISHLAYDI?
     * 1. Kalitning hash indeksini hisoblaymiz
     * 2. O'sha bucket da qidiramiz
     * 3. Kalit mavjud bo'lsa, qiymatni yangilaymiz
     * 4. Yo'q bo'lsa, yangi Node qo'shamiz
     * 5. Yuklama chegaradan oshsa, kengaytiramiz
     * 
     * MISOL:
     * put("Ali", 25):
     * 1. "Ali".hashCode() -> 278
     * 2. 278 % 16 = 10 (indeks)
     * 3. buckets[10] ga qo'shamiz
     * 
     * TEZLIK: O(1) amortizatsiya, O(n) eng yomon holat
     * 
     * @param key - kalit
     * @param value - qiymat
     */
    public void put(K key, V value) {
        // 1-QADAM: Kalitning hash indeksini hisoblaymiz
        int index = getBucketIndex(key);
        
        // 2-QADAM: Bucket da qidiramiz
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                // Kalit mavjud - qiymatni yangilaymiz
                current.value = value;
                return;
            }
            current = current.next;
        }
        
        // 3-QADAM: Kalit topilmadi - yangi Node qo'shamiz
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index]; // Eski boshni yangisiga bog'laymiz
        buckets[index] = newNode;      // Yangi Node ni bosh qilamiz
        size++;
        
        // 4-QADAM: Yuklama chegaradan oshsa, kengaytiramiz
        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Kalit bo'yicha qiymat olish (get).
     * 
     * QANDAY ISHLAYDI?
     * 1. Kalitning hash indeksini hisoblaymiz
     * 2. Bucket da qidiramiz
     * 3. Topilsa, qiymatni qaytaramiz
     * 4. Topilmasa, null qaytaramiz
     * 
     * TEZLIK: O(1) o'rtacha, O(n) eng yomon holat
     * 
     * @param key - kalit
     * @return qiymat yoki null
     */
    public V get(K key) {
        // 1-QADAM: Kalitning hash indeksini hisoblaymiz
        int index = getBucketIndex(key);
        
        // 2-QADAM: Bucket da qidiramiz
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value; // Topdik!
            }
            current = current.next;
        }
        
        return null; // Topilmadi
    }

    /**
     * Kalitni o'chirish (remove).
     * 
     * QANDAY ISHLAYDI?
     * 1. Kalitning hash indeksini hisoblaymiz
     * 2. Bucket da qidiramiz
     * 3. Topilganda, bog'lanishni o'zgartiramiz
     * 
     * TEZLIK: O(1) o'rtacha, O(n) eng yomon holat
     * 
     * @param key - o'chiriladigan kalit
     * @return o'chirilgan qiymat yoki null
     */
    public V remove(K key) {
        // 1-QADAM: Kalitning hash indeksini hisoblaymiz
        int index = getBucketIndex(key);
        
        // 2-QADAM: Bucket da qidiramiz
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;
        
        while (current != null) {
            if (current.key.equals(key)) {
                // Topdik! O'chiramiz
                if (prev == null) {
                    // Birinchi elementni o'chiramiz
                    buckets[index] = current.next;
                } else {
                    // O'rtadagi yoki oxirgi elementni o'chiramiz
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        
        return null; // Topilmadi
    }

    /**
     * Kalit mavjudligini tekshirish (containsKey).
     * 
     * @param key - tekshirilayotgan kalit
     * @return true - mavjud, false - mavjud emas
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Qiymat mavjudligini tekshirish (containsValue).
     * 
     * TEZLIK: O(n) - barcha bucketlarni tekshirish kerak
     * 
     * @param value - tekshirilayotgan qiymat
     * @return true - mavjud, false - mavjud emas
     */
    public boolean containsValue(V value) {
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Kalitning bucket indeksini hisoblash.
     * 
     * QANDAY ISHLAYDI?
     * 1. key.hashCode() - kalitning hash qiymati
     * 2. Math.abs() - manfiy bo'lmasligi uchun
     * 3. % buckets.length - bucket indeksiga aylantirish
     * 
     * NIMA UCHUN %?
     * Hash qiymati katta bo'lishi mumkin, shuning uchun bucket soniga qoldiq olamiz.
     * 
     * @param key - kalit
     * @return bucket indeksi
     */
    private int getBucketIndex(K key) {
        // hashCode() - Java Object sinfiga xos metod
        // Bu har bir ob'ekt uchun noyob raqam qaytaradi
        int hash = key.hashCode();
        
        // Manfiy bo'lsa, musbatga aylantiramiz
        // Chunki manfiy indeks xato berishi mumkin
        hash = Math.abs(hash);
        
        // Bucket indeksiga aylantiramiz
        return hash % buckets.length;
    }

    /**
     * HashMap ni kengaytirish (resize).
     * 
     * QANDAY ISHLAYDI?
     * 1. Yangi, 2 barobar kattaroq massiv yaratamiz
     * 2. Barcha elementlarni yangisiga ko'chiramiz
     * 
     * NIMA UCHUN 2 BAROBAR?
     * Bu amortizatsiya vaqtini O(1) ga saqlab qoladi.
     * 
     * TEZLIK: O(n) - barcha elementlarni qayta hash qilish kerak
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        // 1-QADAM: Yangi, kattaroq massiv yaratamiz
        Node<K, V>[] newBuckets = new Node[buckets.length * 2];
        
        // 2-QADAM: Barcha elementlarni yangisiga ko'chiramiz
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                // Yangi indeksni hisoblaymiz
                int newIndex = Math.abs(current.key.hashCode()) % newBuckets.length;
                
                // Yangi Node yaratamiz
                Node<K, V> newNode = new Node<>(current.key, current.value);
                
                // Yangi bucket ga qo'shamiz
                newNode.next = newBuckets[newIndex];
                newBuckets[newIndex] = newNode;
                
                current = current.next;
            }
        }
        
        // 3-QADAM: Eski massivni almashtiramiz
        buckets = newBuckets;
    }

    /**
     * HashMap bo'shligini tekshirish.
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
     * HashMap ni tozalash.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = new Node[DEFAULT_CAPACITY]; // Yangi bo'sh massiv
        size = 0;
    }

    /**
     * HashMap ni matn ko'rinishida qaytarish.
     */
    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        
        boolean first = true;
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) sb.append(", ");
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.next;
            }
        }
        
        sb.append("}");
        return sb.toString();
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== CustomHashMap Sinov ===\n");
        
        CustomHashMap<String, Integer> ages = new CustomHashMap<>();
        
        // Elementlar qo'shamiz
        System.out.println("--- Elementlar qo'shish (put) ---");
        ages.put("Ali", 25);
        ages.put("Vali", 30);
        ages.put("Gani", 35);
        ages.put("Sardor", 28);
        System.out.println("HashMap: " + ages);
        System.out.println("Hajm: " + ages.size());
        
        // Element olamiz
        System.out.println("\n--- Element olish (get) ---");
        System.out.println("Ali yoshi: " + ages.get("Ali"));
        System.out.println("Vali yoshi: " + ages.get("Vali"));
        System.out.println("Noma'lum: " + ages.get("Noma'lum"));
        
        // Element yangilaymiz
        System.out.println("\n--- Element yangilash ---");
        ages.put("Ali", 26);
        System.out.println("Ali yangilangan yoshi: " + ages.get("Ali"));
        
        // Element o'chiramiz
        System.out.println("\n--- Element o'chirish (remove) ---");
        System.out.println("O'chirildi: " + ages.remove("Gani"));
        System.out.println("HashMap: " + ages);
        
        // Tekshiramiz
        System.out.println("\n--- Tekshirish ---");
        System.out.println("Ali mavjudmi: " + ages.containsKey("Ali"));
        System.out.println("28 qiymat mavjudmi: " + ages.containsValue(28));
        System.out.println("Bo'shmi: " + ages.isEmpty());
    }
}