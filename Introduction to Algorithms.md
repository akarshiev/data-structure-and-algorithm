# 1. Algoritmlarga Kirish (Introduction to Algorithms)

## 1.1 Algoritm nima? (What is an Algorithm?)

**Algoritm** — bu muammoni yechish uchun mo‘ljallangan, aniq va tizimli qadamlar ketma-ketligidir. Muammolar (problems) hayotimizning hamma joyida uchraydi va algoritmlar ularga samarali yechim topishga yordam beradi.

Har qanday tizimli yondashuv (systematic approach) — bu algoritmdir. Dasturlash tilida ifodalangan algoritm esa dastur (program) deb ataladi.

### Real hayot misoli: Osh pishirish algoritmi
- **Kirish (Input):** Piyoz, sabzi, guruch, go‘sht, suv, ziravorlar
- **Jarayon (Process):** 1. Go‘shtni qovurish → 2. Sabzavotlarni qo‘shish → 3. Guruch va suv qo‘shish → 4. 30 daqiqa qaynatish
- **Chiqish (Output):** Tayyor osh

### Keng tarqalgan algoritmlarga misollar:
- **Google qidiruv tizimi:** Internet bo‘ylab milliardlab sahifalarni qidirib, eng relevant (mos) javobni topadi.
- **Raketa boshqaruvi:** Raketani kosmosga uchirish va uni mo‘ljallangan nuqtaga qo‘ndirish uchun murakkab algoritmlar ishlatiladi.

> **Dasturchi uchun ahamiyati:** Yangi algoritmlar yaratish va mavjudlarini optimallashtirish (optimization) orqali dasturchi samarali dasturiy ta’minot yarata oladi.

---

## 1.2 Algoritmning tuzilishi (Structure of an Algorithm)

Har qanday algoritm uchta asosiy qismdan iborat:

| Qism | Ta’rifi | Misol (Osh) |
| :--- | :--- | :--- |
| **Kirish (Input)** | Algoritm ishlashi uchun kerakli ma’lumotlar | Piyoz, sabzi, gurux |
| **Jarayon (Process)** | Kirish ma’lumotlari ustida bajariladigan amallar ketma-ketligi | Qovurish, qaynatish |
| **Chiqish (Output)** | Algoritm natijasida olinadigan ma’lumot | Tayyor osh |

Muammoni qanchalik chuqur tushunsak va uning talablarini (requirements) aniq belgilasak, algoritmni tuzish shunchalik oson bo‘ladi. Muammo bu – ma’lumot (data)dir.

### Matematikadan misol: Kvadrat tenglama yechimi
`ax² + bx + c = 0` ko‘rinishidagi kvadrat tenglamani yechish uchun aniq formula (algoritm) mavjud:
```
x = [-b ± √(b² - 4ac)] / 2a
```
Bu yerda:
- **Kirish:** a, b, c koeffitsiyentlari
- **Jarayon:** Diskriminantni hisoblash, ildizlarni topish
- **Chiqish:** x1 va x2 ildizlari

---

## 1.3 Ma’lumotlar va Xotira (Data and Memory)

Algoritmlar ma’lumotlar ustida ishlaydi. Kompyuterlar esa bu ma’lumotlarni xotirada (memory) saqlaydi.

### Ma’lumotlarni ifodalash
Kompyuterlar faqat **0 va 1 (binary)** lar bilan ishlaydi. Har qanday ma’lumot – matn, rasm, video, musiqa – ikkilik sanoq tizimida saqlanadi.

**Misol: Rasm saqlash**
1. Video -> ketma-ket rasmlar (frames)
2. Rasm -> piksellar (pixels)
3. Piksel -> ranglar (RGB: Red, Green, Blue)
4. Rang -> 0-255 oralig‘idagi son (8 bit = 256 ta kombinatsiya)

> 8 bit (1 byte) yordamida 256 xil rang ifodalanadi. 3 byte (24 bit) esa 16.7 million rangni (True Color) ifodalay oladi.

Texnologiya taraqqiyoti bilan xotira hajmi kengayib bormoqda. Hozirda 1 TB (Terabyte) hajmdagi xotiralar keng tarqalgan.

### Xotira turlari (Types of Memory)

| Xotira turi | Ta’rifi | Tezligi | Hajmi |
| :--- | :--- | :--- | :--- |
| **Cache** | Protsessor (CPU) ichidagi juda tezkor xotira | Eng tez | Kichik (KB/MB) |
| **RAM (Random Access Memory)** | Tezkor xotira, ishlayotgan dasturlar va ma’lumotlar saqlanadi | Tez | O‘rtacha (GB) |
| **Disk (HDD/SSD)** | Doimiy xotira, fayllar va dasturlar saqlanadi | Sekin (ayniqsa HDD) | Katta (TB) |

> **RAM** – "Random Access" so‘zidan kelib chiqqan bo‘lib, istalgan manzildagi (address) ma’lumotga to‘g‘ridan-to‘g‘ri va tez kirish mumkin.
> **Disk** (ayniqsa HDD) – ma’lumotni o‘qish uchun disk aylanishi va kerakli sektorni topishi kerak, shuning uchun sekinroq.

**Dasturchi uchun ahamiyati:** Algoritm qancha xotira sarflayotganini (memory complexity) bilish muhim. Noto‘g‘ri xotira boshqaruvi dasturning sekinlashishiga yoki ishdan chiqishiga sabab bo‘lishi mumkin.

---

# 2. Algoritmlarni Baholash: Big O Notation

## 2.1 Nima uchun Big O?

Bir muammoni yechishning bir necha usuli bo‘lishi mumkin. Qaysi biri "yaxshi" va qaysi biri "yomon" ekanini qanday aniqlaymiz? Algoritmni baholashning ikkita asosiy mezoni bor:

1.  **Vaqt (Time Complexity):** Algoritm qancha vaqt oladi?
2.  **Xotira (Space Complexity):** Algoritm qancha qo‘shimcha xotira talab qiladi?

**Big O Notation** – bu algoritmning ishlash vaqti yoki xotira sarfi **kirish ma’lumotlari hajmiga (n)** qarab qanday o‘zgarishini ifodalovchi matematik belgilashdir. Bu algoritmni "baholash" imkonini beradi.

### Osh misolida tahlil:
Siz 1 kg osh tayyorlash retseptini bilasiz. Agar 1 tonna osh tayyorlashingiz kerak bo‘lsa:
- Qozon hajmi yetarlimi? (xotira muammosi)
- Pishirish vaqti qancha oshadi? (vaqt muammosi)

Big O aynan shu savollarga javob berishga yordam beradi: ma’lumotlar ko‘payganda algoritm qanday "qiynaladi"?

## 2.2 Big O turlari

Eng keng tarqalgan Big O turlari va ularning ma’nosi:

| Big O belgisi | Nomlanishi | Tavsifi | Misol |
| :--- | :--- | :--- | :--- |
| **O(1)** | Konstant vaqt | Algoritm har doim bir xil vaqt oladi, `n` ga bog‘liq emas | Massivdan indeks orqali element olish |
| **O(log n)** | Logarifmik vaqt | Vaqt `n` oshishi bilan sekin o‘sadi | Ikkilik qidiruv (Binary Search) |
| **O(n)** | Chiziqli vaqt | Vaqt `n` ga to‘g‘ri proporsional o‘sadi | Massivdagi eng katta elementni topish |
| **O(n log n)** | Chiziqli-logarifmik | Tezkor saralash algoritmlari (Merge Sort, Quick Sort) | 
| **O(n²)** | Kvadratik vaqt | Vaqt `n` ning kvadratiga proporsional o‘sadi | Ichma-ich sikllar (nested loops) |

### Vizual tahlil:
Tasavvur qiling, sizda 10 ta ma’lumot bor. Keyin bu son 100 taga ko‘paydi:
- **O(n):** Vaqt ham taxminan 10 barobar oshadi (10 -> 100)
- **O(n²):** Vaqt taxminan 100 barobar oshadi (100 -> 10,000) – algoritm juda sekinlashadi!
- **O(log n):** Vaqt juda kam oshadi (taxminan 3.3 -> 6.6) – juda tez!

> **Xulosa:** Ma’lumotlar hajmi katta bo‘lganda O(n²) algoritmlarni ishlatishdan saqlanish kerak. Bu sizning dasturingizni imkon qadar samarali (efficient) qiladi.

## 2.3 Amaliy misol: Ikki xil yondashuv

**Muammo:** 1 dan `n` gacha bo‘lgan sonlar yig‘indisini toping.

**1-usul (Yomon usul - O(n)):**
```java
public class SumCalculator {
    // Yomon usul: sikl yordamida hisoblash
    public static int sumUpToN_Poor(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i; // Bu amal n marta takrorlanadi
        }
        return sum;
    }
}
```
- **Vaqt murakkabligi:** O(n) – 1 million son uchun 1 million qadam.
- **Xotira murakkabligi:** O(1) – faqat bitta `sum` o‘zgaruvchisi.

**2-usul (Yaxshi usul - O(1)):**
```java
public class SumCalculator {
    // Yaxshi usul: matematik formula yordamida hisoblash
    public static int sumUpToN_Good(int n) {
        return n * (n + 1) / 2; // Formula: n*(n+1)/2. Bu amal atigi 1 marta bajariladi.
    }
}
```
- **Vaqt murakkabligi:** O(1) – 1 million son uchun ham atigi 1 qadam.
- **Xotira murakkabligi:** O(1) – qo‘shimcha xotira talab qilinmaydi.

**Solishtirish:**
| Usul | n=10 | n=1,000,000 | Murakkablik |
| :--- | :--- | :--- | :--- |
| **Yomon usul** | 10 qadam | 1,000,000 qadam | O(n) |
| **Yaxshi usul** | 1 qadam | 1 qadam | O(1) |

> Bu misol, ba’zida matematik bilim algoritmni keskin tezlashtirishi mumkinligini ko‘rsatadi.

## 2.4 Vaqt va Xotira o‘rtasidagi kelishuv (Time-Space Tradeoff)

Ba’zida algoritmni tezlashtirish uchun ko‘proq xotira sarflashga to‘g‘ri keladi. Bu **time-space tradeoff** deb ataladi.

**Misol:** Ma’lumotlar ichidan tez-tez qidiruv qilish kerak bo‘lsa:
- **Tez, ko‘p xotira:** Ma’lumotlarni xesh-jadvalda (hash table) saqlash (qidiruv O(1)).
- **Sekin, kam xotira:** Ma’lumotlarni ro‘yxatda saqlash va har safar chiziqli qidiruv (O(n)) qilish.

Qaysi birini tanlash loyiha talablariga bog‘liq.

---

## 2.5 Keyingi Mavzular uchun Tavsiya

Algoritmlarni chuqurroq o‘rganishdan oldin matematikaning quyidagi asosiy mavzularini takrorlab olish foydali:
- Funksiyalar (Functions) va ularning o‘sish sur’ati
- Logarifmlar (Logarithms)
- Darajalar va ildizlar

**Keyingi mavzu:** Ma’lumotlar tuzilmalari (Data Structures) – **String va Array** (Matn va Massivlar).

---

### Tekshiruv Savollari:

1.  Algoritmga ta’rif bering va real hayotdan misol keltiring.
2.  Algoritmning uchta asosiy qismini (Input, Process, Output) osh tayyorlash misolida tushuntiring.
3.  Kompyuter xotirasining asosiy turlarini (Cache, RAM, Disk) tezligi va vazifasiga ko‘ra solishtiring.
4.  Big O Notation nima va u nima uchun kerak?
5.  Quyidagi Big O turlarini eng tezkoridan eng sekiniga qarab tartiblang: O(1), O(n²), O(log n), O(n).
6.  Bir xil muammoni yechadigan O(n) va O(1) algoritmlariga misol keltiring. Qaysi biri yaxshiroq va nima uchun?
7.  "Time-space tradeoff" tushunchasini misol bilan tushuntiring.
