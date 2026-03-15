package data.structures.arrays;

/**
 * CustomArray - Bu Java'dagi ArrayList ga o'xshash dinamik massiv tuzilmasi.
 * 
 * NIMA UCHUN DINAMIK MASSIV KERAK?
 * Oddiy massiv (int[] arr = new int[5]) yaratilganda uning o'lchami doimiy bo'ladi.
 * Agar bizga 6-ta element kerak bo'lsa, yangi massiv yaratib, eski elementlarni
 * ko'chirish kerak. CustomArray buni avtomatik qiladi.
 * 
 * ASOSIY G'OYA:
 * 1. Dastlab kichik massiv yaratamiz (masalan, 10 ta element)
 * 2. Element qo'shilganda, massiv to'lib qolsa, 2 barobar kattaroq yangi massiv yaratamiz
 * 3. Eski elementlarni yangisiga nusxalaymiz
 * 4. Bu "growth strategy" O(1) amortizatsiya vaqt beradi
 * 
 * MURAKKABLIK (Big O):
 * - Elementga murojaat (get): O(1) - indeks orqali to'g'ridan-to'g'ri
 * - Element qo'shish (add): O(1) amortizatsiya - oxiriga qo'shish
 * - Element o'chirish (remove): O(n) - elementlarni surish kerak
 * - Qidirish (indexOf): O(n) - barcha elementlarni tekshirish kerak
 * 
 * @author DSA Project
 */
public class CustomArray<T> {

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Ichki massiv - asosiy ma'lumotlar shu yerda saqlanadi.
     * Java'da massivlar ob'ektga asoslangan, shuning uchun Object[] ishlatamiz.
     * Bu bizga har qanday turdagi (Integer, String, Double va boshqalar) 
     * elementlarni saqlash imkonini beradi.
     */
    private Object[] data;
    
    /**
     * Hozirgi massivdagi haqiqiy elementlar soni.
     * Masalan, 5 ta element qo'shilgan bo'lsa, size = 5.
     * Bu massivning "doldirilgan" qismi.
     */
    private int size;
    
    /**
     * Massivning joriy sig'imi (capacity) - nechta element sig'ishi mumkin.
     * Bu data.length ga teng. Size har doim size <= capacity bo'lishi kerak.
     * 
     * Misol: capacity = 10, size = 7 bo'lsa, yana 3 ta element qo'shish mumkin.
     */
    private static final int DEFAULT_CAPACITY = 10;

    // ==================== YARATISH (CONSTRUCTOR) ====================
    
    /**
     * Standart konstruktor - boshlang'ich sig'im bilan yaratadi.
     * 
     * NIMA BO'LADI?
     * 1. DEFAULT_CAPACITY (10) hajmida bo'sh massiv yaratiladi
     * 2. size 0 ga o'rnatiladi (hali element yo'q)
     * 
     * XOTIRA:
     * - 10 ta Object reference uchun joy ajratiladi
     * - Har bir reference 8 bayt (64-bit tizimlarda)
     * - Jami: ~80 bayt + ob'ekt header
     */
    public CustomArray() {
        this.data = new Object[DEFAULT_CAPACITY]; // 10 ta bo'sh joy yaratdik
        this.size = 0; // Hali elementlar yo'q
    }

    /**
     * Maxsus sig'im bilan yaratadi.
     * Agar biz aniq nechta element bo'lishini bilsak, bu konstruktordan foydalanamiz.
     * Bu ortiqcha xotira kengaytirishdan saqlaydi.
     * 
     * @param initialCapacity - boshlang'ich sig'im
     * @throws IllegalArgumentException - manfiy sig'im berilganda
     */
    public CustomArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Sig'im manfiy bo'lishi mumkin emas: " + initialCapacity);
        }
        this.data = new Object[initialCapacity]; // Berilgan hajmida massiv yaratdik
        this.size = 0;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Massivga element qo'shish (oxiriga).
     * 
     * QANDAY ISHLAYDI?
     * 1. Avval: massiv to'lmaganligini tekshiramiz
     * 2. Agar to'lib qolgan bo'lsa, kengaytiramiz (resize)
     * 3. Yangi elementni oxiriga qo'yamiz
     * 4. size ni 1 ga oshiramiz
     * 
     * TEZLIK:
     * - Oddiy holat: O(1) - faqat bitta qo'shish
     * - Kengaytirish kerak bo'lsa: O(n) - n ta elementni ko'chirish
     * - Lekin amortizatsiya: O(1) - chunki kengaytirish kamdan-kam bo'ladi
     * 
     * @param element - qo'shiladigan element
     */
    public void add(T element) {
        // 1-QADAM: Massiv to'lib qolganligini tekshiramiz
        // size == data.length bo'lsa, yangi joy yo'q demak
        if (size == data.length) {
            // 2-QADAM: Massivni kengaytiramiz (2 barobar)
            // NIMA UCHUN 2 BAROBAR? 
            // Agar har safar 1 ta qo'shsak, O(n) vaqt oladi
            // 2 barobar qilsak, O(1) amortizatsiya vaqt oladi
            resize(data.length * 2);
        }
        
        // 3-QADAM: Elementni to'g'ri joyga qo'yamiz
        // size indeksi - bu "keyingi bo'sh joy"
        data[size] = element;
        
        // 4-QADAM: Elementlar sonini 1 ga oshiramiz
        size++;
    }

    /**
     * Massivning o'lchamini o'zgartirish (kichik yoki katta).
     * 
     * BU JARAYON QANDAY BO'LADI?
     * 1. Yangi, kattaroq (yoki kichikroq) massiv yaratiladi
     * 2. Eski massivdagi barcha elementlar yangisiga ko'chiriladi
     * 3. Eski massiv GC (Garbage Collector) tomonidan tozalanadi
     * 
     * DIQQAT: Bu operatsiya O(n) vaqt oladi, shuning uchun kamdan-kam ishlatiladi.
     * 
     * @param newCapacity - yangi sig'im
     */
    private void resize(int newCapacity) {
        // Yangi massiv yaratamiz
        Object[] newData = new Object[newCapacity];
        
        // Eski elementlarni nusxalaymiz
        // System.arraycopy - bu Java'dagi eng tez nusxalash usuli
        // Parametrlari: (manba, manba_boshlash, maqsad, maqsad_boshlash, nusxalash_soni)
        System.arraycopy(data, 0, newData, 0, size);
        
        // Eski massivni almashtiramiz
        // Eski massiv avtomatik GC tomonidan o'chiriladi
        data = newData;
    }

    /**
     * Indeks bo'yicha element olish.
     * 
     * NIMA UCHUN TEZ (O(1))?
     * Java massivlarida element manzili hisoblanadi:
     * manzil = boshlang'ich_manzil + (indeks × element_razmeri)
     * Bu hisoblash juda tez - faqat bir necha arifmetik amal.
     * 
     * @param index - element indeksi (0 dan boshlanadi)
     * @return o'sha indeksdagi element
     * @throws IndexOutOfBoundsException - indeks chegaradan tashqarida bo'lsa
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        // INDEKSnI TEKSHIRISH - bu xavfsizlik uchun juda muhim!
        // Agar indeks noto'g'ri bo'lsa, dastur xato bermasligi kerak
        checkIndex(index);
        
        // Elementni qaytarish - O(1) tezlikda!
        return (T) data[index];
    }

    /**
     * Indeks bo'yicha element o'zgartirish.
     * 
     * @param index - o'zgartiriladigan element indeksi
     * @param element - yangi qiymat
     * @throws IndexOutOfBoundsException - indeks chegaradan tashqarida bo'lsa
     */
    public void set(int index, T element) {
        checkIndex(index); // Indeksi tekshiramiz
        data[index] = element; // Elementni almashtiramiz - O(1)
    }

    /**
     * Indeksdagi elementni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Elementni topamiz
     * 2. O'chirilgan elementdan keyingi barcha elementlarni 1 qadam oldinga suramiz
     * 3. Oxirgi joyni null qilamiz (xotirani tozalash uchun)
     * 4. size ni 1 ga kamaytiramiz
     * 
     * TEZLIK: O(n) - chunki n ta elementni surish kerak bo'lishi mumkin
     * 
     * @param index - o'chiriladigan element indeksi
     * @return o'chirilgan element
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index); // Indeksi tekshiramiz
        
        // O'chirilgan elementni saqlab qoramiz (foydalanuvchiga qaytarish uchun)
        @SuppressWarnings("rawtypes")
        T removedElement = (T) data[index];
        
        // O'ng tomondagi elementlarni 1 qadam oldinga suramiz
        // System.arraycopy - bu eng tez usul
        // Masalan: [A, B, C, D, E] dan index=1 (B) o'chirilsa:
        // [A, C, D, E, null] ga aylanadi
        int elementsToShift = size - index - 1; // Nechta element surish kerak
        if (elementsToShift > 0) {
            System.arraycopy(data, index + 1, data, index, elementsToShift);
        }
        
        // Oxirgi joyni null qilamiz - GC uchun
        // Bu juda muhim! Agar null qilmasak, ob'ekt hali "yashaydi" deb hisoblanadi
        data[--size] = null;
        
        // Agar juda kichik bo'lib qolsa, kichraytiramiz
        // Masalan, 1/4 bo'lsa va sig'im 10 dan katta bo'lsa
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);
        }
        
        return removedElement;
    }

    /**
     * Element qaysi indekda ekanini topish.
     * 
     * QANDAY ISHLAYDI?
     * Barcha elementlarni birma-bir tekshiramiz.
     * Birinchi topilganda indeksini qaytariladi.
     * Topilmasa -1 qaytariladi.
     * 
     * TEZLIK: O(n) - eng yomon holatda barcha elementlarni tekshirish kerak
     * 
     * @param element - qidirilayotgan element
     * @return element indeksi yoki -1 (topilmasa)
     */
    public int indexOf(T element) {
        // Barcha elementlarni tekshiramiz
        for (int i = 0; i < size; i++) {
            // null bo'lmagan elementlarni equals() bilan solishtiramiz
            // Agar element null bo'lsa, to'g'ridan-to'g'ri solishtiramiz
            if (element == null) {
                if (data[i] == null) return i; // null ni topdik
            } else {
                if (element.equals(data[i])) return i; // elementni topdik
            }
        }
        return -1; // Element topilmadi
    }

    /**
     * Massivda element mavjudligini tekshirish.
     * 
     * @param element - tekshirilayotgan element
     * @return true - element mavjud, false - mavjud emas
     */
    public boolean contains(T element) {
        return indexOf(element) != -1; // indexOf -1 dan farqli bo'lsa, mavjud
    }

    /**
     * Massiv bo'shligini tekshirish.
     * 
     * @return true - massiv bo'sh, false - elementlar mavjud
     */
    public boolean isEmpty() {
        return size == 0; // Agar size 0 bo'lsa, bo'sh
    }

    /**
     * Massiv sig'imini qaytarish.
     * 
     * @return massiv sig'imi
     */
    public int capacity() {
        return data.length; // Massiv uzunligi
    }

    /**
     * Massivdagi elementlar sonini qaytarish.
     * 
     * @return elementlar soni
     */
    public int size() {
        return size;
    }

    /**
     * Massivni tozalash - barcha elementlarni o'chirish.
     * 
     * QANDAY ISHLAYDI?
     * 1. Barcha joylarni null qilamiz
     * 2. size ni 0 ga o'rnatamiz
     * 3. GC eski ob'ektlarni tozalaydi
     * 
     * TEZLIK: O(n) - n ta elementni null qilish kerak
     */
    public void clear() {
        // Barcha elementlarni null qilamiz
        for (int i = 0; i < size; i++) {
            data[i] = null; // GC uchun
        }
        size = 0; // Elementlar sonini 0 qilamiz
    }

    /**
     * Massivni String ko'rinishida qaytarish.
     * 
     * @return massivning matn ko'rinishi
     */
    @Override
    public String toString() {
        if (size == 0) return "[]"; // Bo'sh massiv
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", "); // Oxiridan tashqari, vergul qo'yamiz
            }
        }
        
        sb.append("]");
        return sb.toString();
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Indeksning to'g'riligini tekshirish.
     * 
     * NIMA UCHUN KERAK?
     * Agar indeks noto'g'ri bo'lsa (masalan, manfiy yoki size dan katta),
     * dastur xato berishi yoki noto'g'ri ma'lumot qaytarishi mumkin.
     * Bu funksiya xavfsizlikni ta'minlaydi.
     * 
     * @param index - tekshirilayotgan indeks
     * @throws IndexOutOfBoundsException - indeks noto'g'ri bo'lsa
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Indeks chegaradan tashqarida: " + index + 
                ". Massiv hajmi: " + size
            );
        }
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    /**
     * Bu yerda CustomArray ni sinab ko'ramiz.
     * 
     * QANDAY ISHLASHINI KO'RISH UCHUN:
     * 1. Dasturni kompilyatsiya qiling: javac CustomArray.java
     * 2. Ishga tushiring: java data.structures.arrays.CustomArray
     * 3. Natijalarni kuzating
     */
    public static void main(String[] args) {
        System.out.println("=== CustomArray Sinov ===\n");
        
        // Yangi massiv yaratamiz
        CustomArray<Integer> numbers = new CustomArray<>();
        System.out.println("Yaratildi. Bo'shmi: " + numbers.isEmpty()); // true
        
        // Elementlar qo'shamiz
        System.out.println("\n--- Elementlar qo'shish ---");
        for (int i = 1; i <= 15; i++) {
            numbers.add(i * 10);
            System.out.println("Qo'shildi: " + (i * 10) + 
                             " | Hajm: " + numbers.size() + 
                             | " Sig'im: " + numbers.capacity());
        }
        
        // Massivni ko'rsatamiz
        System.out.println("\nMassiv: " + numbers);
        System.out.println("Elementlar soni: " + numbers.size());
        System.out.println("Sig'im: " + numbers.capacity());
        
        // Element olish
        System.out.println("\n--- Element olish ---");
        System.out.println("3-indeksdagi element: " + numbers.get(3));
        System.out.println("0-indeksdagi element: " + numbers.get(0));
        
        // Element qidirish
        System.out.println("\n--- Element qidirish ---");
        System.out.println("50 indeksi: " + numbers.indexOf(50));
        System.out.println("999 indeksi: " + numbers.indexOf(999));
        System.out.println("100 mavjudmi: " + numbers.contains(100));
        
        // Element o'chirish
        System.out.println("\n--- Element o'chirish ---");
        System.out.println("O'chirildi: " + numbers.remove(4));
        System.out.println("O'chirishdan keyin: " + numbers);
        System.out.println("Yangi hajm: " + numbers.size());
        
        // Element o'zgartirish
        System.out.println("\n--- Element o'zgartirish ---");
        numbers.set(0, 999);
        System.out.println("O'zgartirilgan massiv: " + numbers);
        
        // Massivni tozalash
        System.out.println("\n--- Massivni tozalash ---");
        numbers.clear();
        System.out.println("Tozalandi. Bo'shmi: " + numbers.isEmpty());
        System.out.println("Hajm: " + numbers.size());
    }
}