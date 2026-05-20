# 📚 Data Structures and Algorithms

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)

> Ma'lumotlar tuzilmalari va algoritmlarni noldan o'rganish uchun amaliy qo'llanma

---

## 📁 Loyiha tuzilishi

```
data-structures-and-algorithms/
│
├── README.md                          # Loyiha haqida ma'lumot
│
├── java/                              # Java dasturlari
│   ├── data-structures/               # Ma'lumotlar tuzilmalari
│   │   ├── arrays/                    # Massivlar
│   │   ├── linked-list/              # Bog'langan ro'yxatlar
│   │   ├── stack/                     # Stek
│   │   ├── queue/                     # Navbat
│   │   ├── hash-map/                  # Xesh xaritasi
│   │   ├── tree/                      # Daraxtlar
│   │   ├── heap/                      # Kupyura
│   │   └── graph/                     # Graf
│   │
│   └── algorithms/                    # Algoritmlar
│       ├── sorting/                   # Saralash
│       ├── searching/                 # Qidirish
│       ├── recursion/                 # Rekursiya
│       ├── dynamic-programming/       # Dinamik dasturlash
│       ├── greedy/                    # Ochko'zlik
│       └── graph-algorithms/          # Graf algoritmlari
│
├── go/                                # Go dasturlari (tez orada)
│   ├── data-structures/
│   └── algorithms/
│
└── docs/                              # Hujjatlar
    ├── complexity-analysis.md         # Murakkablik tahlili
    ├── big-o-notation.md              # Big O
    └── interview-notes.md             # Suhbat uchun eslatmalar
```

---

## 🚀 Boshlash

### Java dasturlarini ishga tushirish

```bash
# Ma'lumotlar tuzilmalarini sinash
javac java/data-structures/arrays/CustomArray.java
java data.structures.arrays.CustomArray

# Algoritmlarni sinash
javac java/algorithms/sorting/SortingAlgorithms.java
java algorithms.sorting.SortingAlgorithms
```

---

## 📚 Ma'lumotlar Tuzilmalari

### Array (Massiv)
- **Vaqt murakkabligi:** O(1) kirish, O(n) qo'shish/o'chirish
- **Xususiyatlari:** Indeks bilan tez kirish, lekin o'lchami doimiy

### Linked List (Bog'langan Ro'yxat)
- **Vaqt murakkabligi:** O(1) qo'shish/o'chirish (boshida), O(n) kirish
- **Xususiyatlari:** Dinamik o'lcham, tez qo'shish/o'chirish

### Stack (Stek)
- **Vaqt murakkabligi:** O(1) barcha amallar
- **Xususiyatlari:** LIFO (Last In, First Out)

### Queue (Navbat)
- **Vaqt murakkabligi:** O(1) barcha amallar
- **Xususiyatlari:** FIFO (First In, First Out)

### HashMap (Xesh Xaritasi)
- **Vaqt murakkabligi:** O(1) o'rtacha qidirish
- **Xususiyatlari:** Kalit-qiymat juftligi, tez qidirish

### Tree (Daraxt)
- **Vaqt murakkabligi:** O(log n) qidirish (balanced)
- **Xususiyatlari:** Ierarxik tuzilma

### Heap (Kupyura)
- **Vaqt murakkabligi:** O(1) max/min, O(log n) qo'shish/o'chirish
- **Xususiyatlari:** Eng katta/kichik element tezda topiladi

### Graph (Graf)
- **Vaqt murakkabligi:** O(V + E) BFS/DFS
- **Xususiyatlari:** Nuqtalar va bog'lanishlar

---

## 📚 Algoritmlar

### Saralash (Sorting)

| Algoritm | Eng yaxshi | O'rtacha | Eng yomon | Xotira |
|----------|------------|----------|-----------|--------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |

### Qidirish (Searching)

| Algoritm | Vaqt | Shart |
|----------|------|-------|
| Linear Search | O(n) | Hech qanday |
| Binary Search | O(log n) | Saralangan massiv |

### Graf Algoritmlari

| Algoritm | Vaqt | Foydalanish |
|----------|------|-------------|
| BFS | O(V + E) | Eng qisqa yo'l |
| DFS | O(V + E) | Halqa aniqlash |
| Dijkstra | O((V+E)logV) | Eng qisqa yo'l (og'irlikli) |

---

## 🤝 Hissa qo'shish (Contributing)

Loyiha ochiq! Boshqa tillarda ham algoritmlarni qo'shishingiz mumkin.

### Qanday hissa qo'shish kerak:

1. Fork qiling
2. Branch yarating (`git checkout -b feature/amazing-feature`)
3. O'zgarishlaringizni commit qiling (`git commit -m 'Add amazing feature'`)
4. Push qiling (`git push origin feature/amazing-feature`)
5. Pull Request yarating

### Yangi til qo'shish:

1. Til nomi bilan papka yarating (masalan, `python/`)
2. Shu papka ichida data-structures va algorithms papkalari yarating
3. Har bir algoritmda batafsil kommentariyalar qoldiring
4. README ga qo'shing

---

## 📖 O'rganish manbalari

- [GeeksforGeeks](https://www.geeksforgeeks.org/)
- [LeetCode](https://leetcode.com/)
- [HackerRank](https://www.hackerrank.com/)
- [Big O Cheat Sheet](https://www.bigocheatsheet.com/)

---

## 📄 Litsenziya

Bu loyiha MIT litsenziyasi ostida. Batafsil: [LICENSE](LICENSE)

---

## ✨ Xususiyatlar

- ✅ Barcha algoritmlar noldan yozilgan
- ✅ Batafsil kommentariyalar (o'zbek tilida)
- ✅ Amaliy misollar
- ✅ Murakkablik tahlili
- ✅ Suhbat uchun tayyorlangan savollar
- ✅ Open Source - hissa qo'shishga ochiq
