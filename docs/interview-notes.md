# Interview Notes - Ma'lumotlar Tuzilmalari va Algoritmlar

## Suhbatga tayyorgarlik bo'yicha qo'llanma

---

## 1. Ma'lumotlar Tuzilmalari

### Array (Massiv)
- **Xususiyatlari:** Indeks bilan tez kirish O(1), lekin qo'shish/o'chirish sekin O(n)
- **Qachon ishlatiladi:** Saralangan ma'lumotlar, indeks bilan kirish kerak bo'lganda
- **Savol:** "Massivda element qanday qilib qo'shiladi?" - Yangi massiv yaratiladi va nusxalanadi

### Linked List
- **Xususiyatlari:** O'rtasiga qo'shish/o'chirish tez O(1), lekin kirish sekin O(n)
- **Qachon ishlatiladi:** Tez-tez qo'shish/o'chirish kerak bo'lganda
- **Savol:** "Linked List massivdan qanday farq qiladi?" - Xotirada ketma-ket emas

### Stack
- **Xususiyatlari:** LIFO (Last In, First Out)
- **Qachon ishlatiladi:** Rekursiya, undo funksiyasi, brauzer tarixi
- **Savol:** "Stack qanday qilib rekursiyani amalga oshiradi?" - Function call stack

### Queue
- **Xususiyatlari:** FIFO (First In, First Out)
- **Qachon ishlatiladi:** BFS, print queue, task scheduling
- **Savol:** "Circular Queue nima uchun kerak?" - Xotira samarali ishlatish

### HashMap
- **Xususiyatlari:** Kalit-qiymat juftligi, O(1) qidirish
- **Qachon ishlatiladi:** Tez qidirish, frequency counter
- **Savol:** "Collision qanday hal qilinadi?" - Chaining yoki Open Addressing

### Tree
- **Xususiyatlari:** Ierarxik tuzilma, O(log n) qidirish
- **Qachon ishlatiladi:** Saralangan ma'lumotlar, database indexing
- **Savol:** "BST va AVL farqi nima?" - AVL balanslangan

### Heap
- **Xususiyatlari:** Eng katta/kichik element tezda topiladi O(1)
- **Qachon ishlatiladi:** Priority Queue, Heap Sort
- **Savol:** "Heap massivda qanday saqlanadi?" - Indekslar orqali

### Graph
- **Xususiyatlari:** Nuqtalar va bog'lanishlar
- **Qachon ishlatiladi:** Social networks, maps, networks
- **Savol:** "BFS va DFS farqi nima?" - Kenglikka vs Chuqurlikka

---

## 2. Algoritmlar

### Saralash (Sorting)

| Algoritm | Vaqt | Xotira | Barqaror | Qachon ishlatiladi |
|----------|------|--------|----------|-------------------|
| Bubble Sort | O(n²) | O(1) | Ha | O'rganish uchun |
| Selection Sort | O(n²) | O(1) | Yo'q | Kichik massivlar |
| Insertion Sort | O(n²) | O(1) | Ha | Kichik/yarim saralangan |
| Merge Sort | O(n log n) | O(n) | Ha | Katta massivlar |
| Quick Sort | O(n log n) | O(log n) | Yo'q | Umumiy maqsad |
| Heap Sort | O(n log n) | O(1) | Yo'q | Xotira cheklangan |

### Qidirish (Searching)

| Algoritm | Vaqt | Shart |
|----------|------|-------|
| Linear Search | O(n) | Hech qanday |
| Binary Search | O(log n) | Saralangan |

### Graf Algoritmlari

| Algoritm | Vaqt | Foydalanish |
|----------|------|-------------|
| BFS | O(V + E) | Eng qisqa yo'l (og'irliksiz) |
| DFS | O(V + E) | Halqa aniqlash, topologik saralash |
| Dijkstra | O((V+E)logV) | Eng qisqa yo'l (og'irlikli) |
| Bellman-Ford | O(VE) | Manfiy og'irliklar |

---

## 3. Ko'p beriladigan savollar

### Savol: "Array va Linked List o'rtasidagi farq nima?"

**Javob:**
- **Array:** Indeks bilan tez kirish O(1), lekin o'rtasiga qo'shish sekin O(n)
- **Linked List:** O'rtasiga qo'shish tez O(1), lekin kirish sekin O(n)
- **Xotira:** Array ketma-ket, Linked List tarqalgan

### Savol: "HashMap qanday ishlaydi?"

**Javob:**
1. Kalitning hash kodini hisoblaymiz
2. Hash kodini bucket indeksiga aylantiramiz
3. Bucket da qidiramiz
4. Collision bo'lsa, chaining ishlatamiz

### Savol: "Rekursiya nima? Qachon ishlatiladi?"

**Javob:**
- Rekursiya - funksiya o'zini o'zi chaqirishi
- Base case bo'lishi shart (bo'lmasa StackOverflow)
- Daraxt tuzilmalari, Divide and Conquer da ishlatiladi

### Savol: "Dynamic Programming nima?"

**Javob:**
- Katta muammoni kichik qismlarga bo'lish
- Takroriy hisoblangan qiymatlarni saqlash
- Memoization (Top-Down) yoki Tabulation (Bottom-Up)

### Savol: "Greedy algoritmlar qachon ishlaydi?"

**Javob:**
1. Greedy Choice Property - lokal optimal tanlov global optimalga olib keladi
2. Optimal Substructure - katta muammo kichik qismlardan tashkil topgan

---

## 4. Maslahatlar

1. **Oldin tushun, keyin yoz** - Muammoni to'liq tushunib oling
2. **Oddiydan boshla** - Avval oddiy yechim, keyin optimallashtiring
3. **Test qil** - Kichik misollar bilan sinang
4. **Murakkablikni ayting** - Vaqt va xotira murakkabligini tushuntiring
5. **Alternative yechimlar** - Boshqa usullarni ham gapiring
