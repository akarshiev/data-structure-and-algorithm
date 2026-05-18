# Murakkablik Tahlili (Complexity Analysis)

## Nima uchun murakkablik tahlili muhim?

Murakkablik tahlili - bu algoritmning qancha vaqt va xotira sarflayotganini baholash usuli. Bu dasturchiga algoritmlarni solishtirish va eng yaxshisini tanlashga yordam beradi.

---

## Vaqt Murakkabligi (Time Complexity)

Vaqt murakkabligi - kirish ma'lumotlari hajmi (n) oshganda algoritm qancha vaqt o'tishini bildiradi.

### Big O belgilari

| Belgi | Nom | Tavsif | Misol |
|-------|-----|--------|-------|
| **O(1)** | Konstant | Har doim bir xil vaqt | Massivdan indeks bilan olish |
| **O(log n)** | Logarifmik | Tez sekinlashadi | Binary Search |
| **O(n)** | Chiziqli | To'g'ridan-to'g'ri o'sadi | Linear Search |
| **O(n log n)** | Lineer-log | Tezkor saralash | Merge Sort, Quick Sort |
| **O(n²)** | Kvadrat | Juda sekin o'sadi | Nested Loops |
| **O(2ⁿ)** | Eksponensial | Juda sekin | Recursive Fibonacci |

### Vizual tahlil

```
Vaqt
  │
  │                                    O(n²)
  │                                   /
  │                                  /
  │                                 /
  │                        O(n log n)
  │                       /
  │              O(n)   /
  │             /      /
  │    O(log n)      /
  │   /             /
  │  /       O(1) /
  │ /           /
  │/___________/__________________________ Kirish (n)
```

---

## Xotira Murakkabligi (Space Complexity)

Xotira murakkabligi - algoritm qancha qo'shimcha xotira sarflayotganini bildiradi.

### Misollar

| Algoritm | Xotira |
|----------|--------|
| Bubble Sort | O(1) |
| Merge Sort | O(n) |
| Quick Sort | O(log n) |
| Binary Search (recursive) | O(log n) |

---

## Amaliy misol: Saralash algoritmlarini solishtirish

```java
// Bubble Sort - O(n²)
public void bubbleSort(int[] arr) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            // Solishtirish va almashtirish
        }
    }
}

// Merge Sort - O(n log n)
public void mergeSort(int[] arr) {
    if (arr.length <= 1) return;
    int mid = arr.length / 2;
    mergeSort(left);
    mergeSort(right);
    merge(arr, left, right);
}
```

**Natija:** n = 1,000,000 bo'lganda:
- Bubble Sort: ~1,000,000,000,000 amal (sekin)
- Merge Sort: ~20,000,000 amal (tez!)

---

## Xulosa

Murakkablik tahlili dasturchiga:
1. Algoritmlarni solishtirishga
2. To'g'ri algoritmni tanlashga
3. Dasturning ishlash tezligini oldindan bilishga yordam beradi
