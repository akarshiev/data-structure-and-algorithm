package data.structures.tree;

/**
 * BinarySearchTree - Ikkinchi darajali qidiruv daraxti (BST).
 * 
 * NIMA UCHUN TREE KERAK?
 * Tree tuzilmalari ma'lumotlarni ierarxik (darajali) shaklda saqlaydi.
 * BST har bir tugundan kattaroq elementlar o'nga, kichikroqlari chapga joylashadi.
 * 
 * QANDAY ISHLAYDI?
 * 1. Har bir tugun (node) 2 ta bola (child) ga ega bo'lishi mumkin
 * 2. Chap bola = ota tugundan kichikroq
 * 3. O'ng bola = ota tugundan kattaroq
 * 4. Bu xususiyat qidiruvni juda tezlashtiradi!
 * 
 * MISOL:
 *        50
 *       /  \
 *     30    70
 *    /  \   /  \
 *  20  40  60  80
 * 
 * Bu daraxtda 50 ga qidirsak:
 * - 50 > 30 -> chapga boramiz
 * - 50 < 70 -> chapga boramiz
 * - Topdik! O(n) emas, O(log n)!
 * 
 * MURAKKABLIK:
 * - Qidirish: O(log n) o'rtacha, O(n) eng yomon holat
 * - Qo'shish: O(log n) o'rtacha, O(n) eng yomon holat
 * - O'chirish: O(log n) o'rtacha, O(n) eng yomon holat
 * 
 * ENG YOMON HOLAT QACHON BO'LADI?
 * Agar elementlar tartibsiz qo'shilsa (masalan, 1, 2, 3, 4, 5),
 * daraxt "linked list" ga aylanadi va O(n) bo'ladi.
 * 
 * @author DSA Project
 */
public class BinarySearchTree {

    // ==================== NODE (TUGUN) ====================
    
    /**
     * TreeNode - Daraxtning asosiy bloki (tuguni).
     * Har bir tugun ma'lumot va ikki bolaga ega.
     */
    private static class TreeNode {
        int data;           // Ma'lumot (son)
        TreeNode left;      // Chap bola (kichikroq)
        TreeNode right;     // O'ng bola (kattaroq)
        
        TreeNode(int data) {
            this.data = data;
            this.left = null;   // Dastlab bolalar yo'q
            this.right = null;
        }
    }

    // ==================== O'ZGARUVCHILAR ====================
    
    /**
     * Daraxtning ildizi (root) - eng yuqori tugun.
     * Bu "darvoza" - daraxtga kirish shu yerdan.
     */
    private TreeNode root;

    // ==================== YARATISH ====================
    
    /**
     * Bo'sh daraxt yaratish.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    // ==================== ASOSIY AMALLAR ====================
    
    /**
     * Element qo'shish (insert).
     * 
     * QANDAY ISHLAYDI?
     * 1. Agar daraxt bo'sh bo'lsa, root ga qo'shamiz
     * 2. Aks holda, to'g'ri joyni qidiramiz:
     *    - Kichikroq -> chapga
     *    - Kattaroq -> o'nga
     * 3. Bo'sh joy topilganda, yangi tugun qo'shamiz
     * 
     * MISOL (50, 30, 70, 20, 40 qo'shish):
     * Qo'shish 50:    50
     * Qo'shish 30:    50
     *                /
     *              30
     * Qo'shish 70:    50
     *                / \
     *              30  70
     * 
     * TEZLIK: O(log n) o'rtacha, O(n) eng yomon holat
     * 
     * @param data - qo'shiladigan element
     */
    public void insert(int data) {
        // Recursion yordamida qo'shamiz
        root = insertRecursive(root, data);
    }

    /**
     * Elementni recursion yordamida qo'shish.
     * 
     * NIMA UCHUN RECURSION?
     * Recursion daraxt tuzilmalari bilan ishlashning eng qulay usuli.
     * Har bir chaqiruvda bir qadam pastga tushamiz.
     * 
     * @param current - joriy tugun
     * @param data - qo'shiladigan element
     * @return yangilangan tugun
     */
    private TreeNode insertRecursive(TreeNode current, int data) {
        // Agar joriy tugun bo'sh bo'lsa, yangi tugun yaratamiz
        if (current == null) {
            return new TreeNode(data);
        }
        
        // Qaysi tomonga borishni hal qilamiz
        if (data < current.data) {
            // Kichikroq -> chapga
            current.left = insertRecursive(current.left, data);
        } else if (data > current.data) {
            // Kattaroq -> o'nga
            current.right = insertRecursive(current.right, data);
        }
        // Agar data == current.data bo'lsa, takroriy element - qo'shmaymiz
        
        return current; // Tugunni qaytaramiz
    }

    /**
     * Element qidirish (search).
     * 
     * QANDAY ISHLAYDI?
     * 1. Root dan boshlaymiz
     * 2. Har bir qadamda:
     *    - Topdik -> qaytaramiz
     *    - Kichikroq -> chapga
     *    - Kattaroq -> o'nga
     * 3. null ga yetdik -> topilmadi
     * 
     * TEZLIK: O(log n) o'rtacha, O(n) eng yomon holat
     * 
     * @param data - qidirilayotgan element
     * @return true - topildi, false - topilmadi
     */
    public boolean search(int data) {
        return searchRecursive(root, data);
    }

    /**
     * Elementni recursion yordamida qidirish.
     * 
     * @param current - joriy tugun
     * @param data - qidirilayotgan element
     * @return true - topildi, false - topilmadi
     */
    private boolean searchRecursive(TreeNode current, int data) {
        // Bo'sh tugun - element topilmadi
        if (current == null) {
            return false;
        }
        
        // Topdik!
        if (data == current.data) {
            return true;
        }
        
        // Qaysi tomonga qidiramiz
        if (data < current.data) {
            return searchRecursive(current.left, data); // Chapga
        } else {
            return searchRecursive(current.right, data); // O'nga
        }
    }

    /**
     * Elementni o'chirish (delete).
     * 
     * QANDAY ISHLAYDI?
     * 3 ta holat mavjud:
     * 
     * 1. YAPRAK (leaf) - bolasi yo'q:
     *    Oddiygina o'chiramiz
     * 
     * 2. BIR TA BOLASI BOR:
     *    Bolani o'rniga qo'yamiz
     * 
     * 3. IKKI TA BOLASI BOR:
     *    a) In-order successor (eng kichik kattaroq) ni topamiz
     *    b) Uni hozirgi tugun o'rniga qo'yamiz
     *    c) Successorni o'chiramiz
     * 
     * TEZLIK: O(log n) o'rtacha, O(n) eng yomon holat
     * 
     * @param data - o'chiriladigan element
     */
    public void delete(int data) {
        root = deleteRecursive(root, data);
    }

    /**
     * Elementni recursion yordamida o'chirish.
     * 
     * @param current - joriy tugun
     * @param data - o'chiriladigan element
     * @return yangilangan tugun
     */
    private TreeNode deleteRecursive(TreeNode current, int data) {
        // Bo'sh tugun - element topilmadi
        if (current == null) {
            return null;
        }
        
        // O'chiriladigan tugunni topdik
        if (data == current.data) {
            // 1-HOLAT: Yaprak tugun
            if (current.left == null && current.right == null) {
                return null; // O'chiramiz
            }
            
            // 2-HOLAT: Bitta bola bor
            if (current.left == null) {
                return current.right; // O'ng bolani qo'yamiz
            }
            if (current.right == null) {
                return current.left; // Chap bolani qo'yamiz
            }
            
            // 3-HOLAT: Ikki ta bola bor
            // In-order successor (eng kichik kattaroq) ni topamiz
            TreeNode successor = findMin(current.right);
            // Successorni hozirgi tugun o'rniga qo'yamiz
            current.data = successor.data;
            // Successorni o'chiramiz
            current.right = deleteRecursive(current.right, successor.data);
            return current;
        }
        
        // Qaysi tomonga borishni hal qilamiz
        if (data < current.data) {
            current.left = deleteRecursive(current.left, data);
        } else {
            current.right = deleteRecursive(current.right, data);
        }
        
        return current;
    }

    /**
     * Eng kichik elementni topish.
     * 
     * QANDAY ISHLAYDI?
     * Chap tomonga ketaveramiz - eng kichik element eng chapda.
     * 
     * TEZLIK: O(log n) o'rtacha
     * 
     * @param node - boshlang'ich tugun
     * @return eng kichik tugun
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left; // Chapga ketaveramiz
        }
        return node;
    }

    // ==================== TRAVERSAL (OB'YOD) ====================
    
    /**
     * In-order traversal (Chap -> Root -> O'ng).
     * 
     * NATIJA: Elementlar tartiblangan holda chiqadi!
     * Bu BST ning eng muhim xususiyati.
     * 
     * MISOL:
     *        50
     *       /  \
     *     30    70
     *    /  \   /  \
     *  20  40  60  80
     * 
     * In-order: 20, 30, 40, 50, 60, 70, 80 (tartiblangan!)
     * 
     * TEZLIK: O(n) - barcha tugunlarni ziyorat qilish kerak
     */
    public void inOrder() {
        System.out.print("In-order: ");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(TreeNode node) {
        if (node != null) {
            inOrderRecursive(node.left);   // Chap
            System.out.print(node.data + " "); // Root
            inOrderRecursive(node.right);  // O'ng
        }
    }

    /**
     * Pre-order traversal (Root -> Chap -> O'ng).
     * 
     * FOYDALANISH: Daraxtni nusxalash yoki saqlash uchun.
     * 
     * TEZLIK: O(n)
     */
    public void preOrder() {
        System.out.print("Pre-order: ");
        preOrderRecursive(root);
        System.out.println();
    }

    private void preOrderRecursive(TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " "); // Root
            preOrderRecursive(node.left);   // Chap
            preOrderRecursive(node.right);  // O'ng
        }
    }

    /**
     * Post-order traversal (Chap -> O'ng -> Root).
     * 
     * FOYDALANISH: Daraxtni o'chirish uchun.
     * 
     * TEZLIK: O(n)
     */
    public void postOrder() {
        System.out.print("Post-order: ");
        postOrderRecursive(root);
        System.out.println();
    }

    private void postOrderRecursive(TreeNode node) {
        if (node != null) {
            postOrderRecursive(node.left);   // Chap
            postOrderRecursive(node.right);  // O'ng
            System.out.print(node.data + " "); // Root
        }
    }

    // ==================== YORDAMCHI FUNKSIYALAR ====================
    
    /**
     * Daraxt balandligini hisoblash.
     * 
     * TEZLIK: O(n)
     * 
     * @return daraxt balandligi
     */
    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(TreeNode node) {
        if (node == null) return -1; // Bo'sh daraxt balandligi -1
        
        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);
        
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Daraxt bo'shligini tekshirish.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Daraxtni matn ko'rinishida qaytarish.
     */
    @Override
    public String toString() {
        if (isEmpty()) return "BST bo'sh";
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb, "", true);
        return sb.toString();
    }

    private void toStringHelper(TreeNode node, StringBuilder sb, String prefix, boolean isLast) {
        if (node != null) {
            sb.append(prefix).append(isLast ? "└── " : "├── ").append(node.data).append("\n");
            toStringHelper(node.left, sb, prefix + (isLast ? "    " : "│   "), node.right == null);
            toStringHelper(node.right, sb, prefix + (isLast ? "    " : "│   "), true);
        }
    }

    // ==================== ASOSIY FUNKSIYA (TEST) ====================
    
    public static void main(String[] args) {
        System.out.println("=== BinarySearchTree Sinov ===\n");
        
        BinarySearchTree bst = new BinarySearchTree();
        
        // Elementlar qo'shamiz
        System.out.println("--- Elementlar qo'shish ---");
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
        
        // Daraxtni ko'rsatamiz
        System.out.println("\nDaraxt:");
        System.out.println(bst);
        
        // Traversal'lar
        System.out.println("\n--- Traversal'lar ---");
        bst.inOrder();    // 20 30 40 50 60 70 80
        bst.preOrder();   // 50 30 20 40 70 60 80
        bst.postOrder();  // 20 40 30 60 80 70 50
        
        // Element qidiramiz
        System.out.println("\n--- Element qidirish ---");
        System.out.println("40 topildimi: " + bst.search(40));  // true
        System.out.println("90 topildimi: " + bst.search(90));  // false
        
        // Balandlik
        System.out.println("\n--- Daraxt ma'lumotlari ---");
        System.out.println("Balandlik: " + bst.height());
        
        // Element o'chiramiz
        System.out.println("\n--- Element o'chirish ---");
        bst.delete(20);  // Yaprak
        System.out.println("20 o'chirildi:");
        bst.inOrder();
        
        bst.delete(30);  // Bitta bola
        System.out.println("30 o'chirildi:");
        bst.inOrder();
        
        bst.delete(50);  // Ikki bola
        System.out.println("50 o'chirildi:");
        bst.inOrder();
    }
}