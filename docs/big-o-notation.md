# Big O Notation

## Big O nima?

Big O - bu algoritmning kirish ma'lumotlari hajmi (n) oshganda qanday o'sishini ifodalovchi matematik belgilash. Bu algoritmlarni solishtirishning universal tili.

---

## Big O turlari

### O(1) - Konstant vaqt

Har qanday kirish uchun bir xil vaqt oladi.

```java
// Misol: Massivdan indeks bilan olish
int getFirst(int[] arr) {
    return arr[0]; // Har doim 1 amal
}

// Misol: HashMap dan olish
map.get(key); // O(1) o'rtacha
```

**Real hayot:** ATM dan pul yechish - qancha pul bo'lsa ham, bir xil vaqt oladi.

---

### O(log n) - Logarifmik vaqt

Kirish 2 barobar oshganda, vaqt faqat 1 qadam oshadi.

```java
// Misol: Binary Search
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

**Real hayot:** Lug'atdan so'z qidirish - avval "M" so'zini qidirasiz, keyin "Ma", va h.k.

---

### O(n) - Chiziqli vaqt

Kirish oshganda, vaqt ham to'g'ridan-to'g'ri o'sadi.

```java
// Misol: Linear Search
int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) return i;
    }
    return -1;
}
```

**Real hayot:** Do'konda barcha mahsulotlarni ko'rib chiqish.

---

### O(n log n) - Lineer-logarifmik

Eng yaxshi saralash algoritmlarining murakkabligi.

```java
// Misol: Merge Sort
void mergeSort(int[] arr) {
    if (arr.length <= 1) return;
    int mid = arr.length / 2;
    mergeSort(left);    // O(n/2)
    mergeSort(right);   // O(n/2)
    merge(arr);         // O(n)
}
```

**Real hayot:** Kutubxonada kitoblarni saralash.

---

### O(n²) - Kvadrat vaqt

Ichma-ich sikllar ko'pincha O(n²) beradi.

```java
// Misol: Bubble Sort
void bubbleSort(int[] arr) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            // Solishtirish
        }
    }
}
```

**Real hayot:** Har bir odam bilan boshqa har bir odamni solishtirish.

---

### O(2ⁿ) - Eksponensial vaqt

Recursion ko'pincha O(2^n) beradi.

```java
// Misol: Recursive Fibonacci
int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2); // 2 marta recursion
}
```

**Real hayot:** Barcha mumkin variantlarni sinab ko'rish.

---

## Solishtirish jadvali

| n | O(1) | O(log n) | O(n) | O(n log n) | O(n²) |
|---|------|----------|------|------------|-------|
| 1 | 1 | 1 | 1 | 1 | 1 |
| 10 | 1 | 3 | 10 | 33 | 100 |
| 100 | 1 | 7 | 100 | 664 | 10,000 |
| 1,000 | 1 | 10 | 1,000 | 9,966 | 1,000,000 |
| 1,000,000 | 1 | 20 | 1,000,000 | 19,931,569 | 10¹² |

---

## Xulosa

| Big O | Sifat | Qachon ishlatiladi |
|-------|-------|-------------------|
| O(1) | Ajoyib | Har doim maqsadga muvofiq |
| O(log n) | Juda yaxshi | Katta ma'lumotlarda qidirish |
| O(n) | Yaxshi | Barcha elementlarni ko'rib chiqish |
| O(n log n) | Qabul qilinadi | Saralash algoritmlari |
| O(n²) | Yomon | Kichik ma'lumotlarda |
| O(2ⁿ) | Juda yomon | Oldini olish kerak |
