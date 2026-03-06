# 2. Ma'lumotlar Tuzilmalari (Data Structures)

## 2.1 Array (Massiv)

**Array** – bu dasturlashda o'rganiladigan eng asosiy ma'lumot tuzilmalaridan biridir. U **kompozit (composite)** ma'lumot turi hisoblanadi, ya'ni bir nechta bir xil turdagi ma'lumotlarni bitta nom ostida saqlash imkonini beradi.

> **Muhim farq:** Biz dasturlash tilidagi array emas, balki **Computer Science** nuqtayi nazaridan array haqida gaplashamiz. Chunki ba'zi dasturlash tillarida (masalan, Java'da `ArrayList`) array ustida qurilgan murakkabroq ma'lumot tuzilmalari mavjud bo'lib, ular arrayning ba'zi kamchiliklarini yashiradi.

### Array nima?

Arrayni bir turdagi ma'lumotlarni saqlash uchun mo'ljallangan, ketma-ket joylashgan **konteynerlar (containers)** yoki **yashiklar** deb tasavvur qilishingiz mumkin.

### Array'ning asosiy xususiyatlari

| Xususiyat | Tavsifi | Misol |
|:---|:---|:---|
| **Davomiylik (Contiguous)** | Elementlar xotirada ketma-ket joylashgan bo'lishi shart | [10, 20, 30] -> xotirada birin-ketin |
| **Chegaralangan (Fixed Size)** | Yaratilganda berilgan o'lchamdan keyin kengayib bo'lmaydi | 5 elementli array'ga 6-element qo'shib bo'lmaydi |
| **Bir xil o'lcham (Fixed Size Elements)** | Har bir element bir xil hajmda joy egallaydi | Har bir `int` 4 bayt, har bir `char` 2 bayt |

### Xotirada saqlanishi va manzillash (Memory Allocation & Addressing)

Kompyuter xotirasida ma'lumot saqlash uchun joy so'raganimizda, bizga ketma-ket joylashgan xotira bloki ajratiladi.

**Misol:** 4 ta elementdan iborat `int` array (har bir `int` 2 bayt deb faraz qilaylik):
- Kerakli xotira: 4 × 2 = 8 bayt
- Xotira manzillari (soddalashtirilgan): 10, 12, 14, 16

**Array elementiga murojaat formulasi:**
```
Element manzili = Array boshlanish manzili + (indeks × element hajmi)
```
`address + i * size`

**Nima uchun array 0-indeksdan boshlanadi?**
Yuqoridagi formulaga ko'ra, birinchi element (i=0) uchun:
`address + 0 * size = address` – bu to'g'ridan-to'g'ri boshlanish manzili bo'ladi. Agar 1-indeksdan boshlanganida, formuladan `size` ni ayirish kerak bo'lar edi, bu esa hisoblashni sekinlashtiradi.

### Array ustida amallar va ularning murakkabligi

#### 1. Elementga murojaat (Access) - O(1)
Formuladan foydalanib, istalgan elementga to'g'ridan-to'g'ri murojaat qilish mumkin.
```java
int[] numbers = {10, 20, 30, 40};
int thirdElement = numbers[2]; // 30 - bir zumda topiladi (O(1))
```

#### 2. Element qo'shish (Insertion) - O(n) (o'rtacha)
Agar array to'lmagan bo'lsa va oxiriga qo'shilsa - O(1). Lekin ko'p hollarda:
```java
// 5-element qo'shish (array to'lib qolgan holat)
int[] oldArray = {1, 2, 3, 4};
int[] newArray = new int[5]; // Yangi, kattaroq array yaratish

// Eski elementlarni ko'chirish - O(n)
for (int i = 0; i < oldArray.length; i++) {
    newArray[i] = oldArray[i];
}
newArray[4] = 5; // Yangi element qo'shish
```
> **Eslatma:** Dasturlash tillaridagi `ArrayList` yoki `Vector` kabi dinamik array'lar bu jarayonni avtomatlashtiradi, lekin ostida aynan shu amal bajariladi.

#### 3. Element o'chirish (Deletion) - O(n)
```java
int[] array = {10, 20, 30, 40, 50};

// Birinchi elementni o'chirish (20,30,40,50 bir qadam oldinga suriladi)
// 4 ta element surish kerak - O(n)

// Oxirgi elementni o'chirish
// Hech qanday surish kerak emas - O(1)
```

### Array amallari murakkablik jadvali

| Amal | Eng yaxshi holat | O'rtacha holat | Eng yomon holat |
|:---|:---|:---|:---|
| **Murojaat (Access)** | O(1) | O(1) | O(1) |
| **Qidiruv (Search)** | O(1) | O(n) | O(n) |
| **Qo'shish (Insert)** | O(1) (oxiriga) | O(n) | O(n) |
| **O'chirish (Delete)** | O(1) (oxiridan) | O(n) | O(n) |

---

## 2.2 String (Matn)

**String** – bu belgilar (characters) ketma-ketligidan tashkil topgan ma'lumot tuzilmasi. Aslida, string – bu o'zgarmas (immutable) array hisoblanadi.

> **Muhim:** String'lar ham array kabi xotirada saqlanadi, farqi shundaki unda sonlar emas, belgilar (char) saqlanadi.

### Belgilarni raqamga aylantirish: Kodlash jadvallari (Encoding)

Kompyuter faqat raqamlar bilan ishlaydi. Harflarni saqlash uchun esa maxsus **kodlash jadvallari (encoding tables)** kerak bo'ladi.

#### ASCII (American Standard Code for Information Interchange)
- Amerika tomonidan ishlab chiqilgan standart
- Har bir belgi uchun 1 bayt (8 bit) = 256 ta belgi
- Lotin alifbosi, raqamlar va asosiy belgilarni o'z ichiga oladi

![](https://imgs.search.brave.com/QKq7l2fEe_WgMd2kxkv4WNVSJ3cCncPyQCd0eVRwG3E/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly93d3cu/a2luZHBuZy5jb20v/cGljYy9tLzM5Ny0z/OTczMDI4X2ltYWdl/cy1hc2NpaS10YWJs/ZS1hc2NpaS10YWJs/ZS1wcmludGFibGUt/aGQtcG5nLnBuZw)

**Misol:** "Hello DSA" string'ining ASCII dagi ifodasi:
```
H -> 72
e -> 101
l -> 108
l -> 108
o -> 111
(bo'sh joy) -> 32
D -> 68
S -> 83
A -> 65
```
Xotirada: [72, 101, 108, 108, 111, 32, 68, 83, 65]

#### Unicode
- ASCII cheklovlaridan chiqish uchun yaratilgan
- Dunyodagi barcha tillardagi belgilarni, emoji'larni o'z ichiga oladi
- UTF-8, UTF-16 kabi variantlari mavjud

| Kodlash | Bayt/ belgi | Xususiyati |
|:---|:---|:---|
| **ASCII** | 1 bayt | Faqat ingliz tili va asosiy belgilar |
| **UTF-8** | 1-4 bayt | ASCII bilan mos, o'zgaruvchan uzunlik |
| **UTF-16** | 2 yoki 4 bayt | Ko'p tillar uchun qulay |

### String'larning o'zgarmasligi (Immutability)

Java, Python, C# kabi tillarda **String immutable** (o'zgarmas) hisoblanadi. Bu shuni anglatadiki, bir marta yaratilgan stringni o'zgartirib bo'lmaydi.

#### String bilan ishlash (Yomon usul)
```java
// Yomon usul: String ni o'zgartirishga urinish
String text = "hello";
text = text.toUpperCase(); // "HELLO"
// Bu yerda eski "hello" xotirada qoladi, yangi "HELLO" uchun yangi joy ajratiladi
```

#### StringBuilder bilan ishlash (Yaxshi usul)
```java
// Yaxshi usul: StringBuilder (mutable) ishlatish
StringBuilder sb = new StringBuilder("hello");
sb.setCharAt(0, 'H'); // "Hello" - mavjud xotirada o'zgartiriladi
String result = sb.toString(); // kerak bo'lganda String ga o'tkazish
```

### String amallari murakkabligi

| Amal | Kod | Vaqt murakkabligi | Xotira murakkabligi |
|:---|:---|:---|:---|
| **Birinchi harfni katta qilish** | `str.substring(0,1).toUpperCase() + str.substring(1)` | O(n) | O(n) |
| **Butun stringni katta qilish** | `str.toUpperCase()` | O(n) | O(n) |
| **Ikki stringni qo'shish** | `str1 + str2` | O(n+m) | O(n+m) |
| **StringBuilder bilan qo'shish** | `sb.append(str2)` | O(m) (ko'p hollarda) | O(1) * |

\* *Agar StringBuilder yetarli joyi bo'lsa, yangi xotira ajratilmaydi.*

### Amaliy misol: So'zlarni teskari qilish

**Muammo:** Berilgan matndagi so'zlarning har birini teskari qilish.

**1-usul (String bilan - yomon):**
```java
public static String reverseWords_String(String sentence) {
    String[] words = sentence.split(" ");
    String result = "";
    
    for (String word : words) {
        // Har bir iteratsiyada yangi String yaratiladi - O(n²)
        result = result + reverse(word) + " ";
    }
    return result.trim();
}

private static String reverse(String s) {
    String reversed = "";
    for (int i = s.length() - 1; i >= 0; i--) {
        reversed += s.charAt(i); // Har safar yangi string
    }
    return reversed;
}
// Vaqt murakkabligi: O(n²), Xotira: O(n²)
```

**2-usul (StringBuilder bilan - yaxshi):**
```java
public static String reverseWords_StringBuilder(String sentence) {
    String[] words = sentence.split(" ");
    StringBuilder result = new StringBuilder();
    
    for (String word : words) {
        result.append(reverse(word)).append(" "); // StringBuilder qayta ishlatiladi
    }
    return result.toString().trim();
}

private static String reverse(String s) {
    return new StringBuilder(s).reverse().toString();
}
// Vaqt murakkabligi: O(n), Xotira: O(n)
```

### String va Array o'rtasidagi bog'liqlik

| Xususiyat | Array | String |
|:---|:---|:---|
| **Asosiy tuzilma** | Bir xil turdagi elementlar to'plami | Belgilar (char) to'plami |
| **O'zgartirish** | Mutable (o'zgartirish mumkin) | Immutable (ko'p tillarda) |
| **Xotira** | Elementlar ketma-ket joylashgan | Belgilar ketma-ket joylashgan |
| **Indekslash** | 0-dan boshlanadi | 0-dan boshlanadi |
| **Maxsus metodlar** | Yo'q | substring, toUpperCase, split va b. |

---

### Tekshiruv Savollari:

1. Array'ning asosiy xususiyatlarini sanab bering. Nima uchun array elementlariga murojaat qilish tez (O(1))?
2. Array'da element qo'shish va o'chirish amallarining vaqt murakkabligi qanday? Nima uchun oxirgi elementni o'chirish tezroq?
3. "Array 0-indeksdan boshlanadi" degan qoidaning texnik sababini tushuntiring.
4. ASCII va Unicode o'rtasidagi farq nima? Emoji'larni saqlash uchun qaysi kodlash kerak?
5. String immutable degani nimani anglatadi? Bu qanday kamchiliklarga olib kelishi mumkin?
6. String va StringBuilder o'rtasidagi farqni misol bilan tushuntiring. Qachon qaysi birini ishlatish kerak?
7. Berilgan matndagi barcha "a" harflarini "b" ga almashtiradigan funksiya yozing. Bu funksiyaning vaqt va xotira murakkabligi qanday?

---

**Keyingi dars:** Linked List va Rekursiya
