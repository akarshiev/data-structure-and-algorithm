package datastructures.tree;

/**
 * BinarySearchTree - A generic integer binary search tree (BST).
 *
 * <p><b>BST Property:</b> For every node N,
 * all values in N's left subtree are &lt; N.data, and all values in
 * the right subtree are &gt; N.data.  This property makes search,
 * insert, and delete run in O(log n) on a balanced tree.
 *
 * <pre>
 *        50
 *       /  \
 *     30    70
 *    /  \  /  \
 *  20  40 60  80
 *
 * In-order traversal → 20 30 40 50 60 70 80  (always sorted!)
 * </pre>
 *
 * <p><b>Time Complexity (balanced tree):</b>
 * <ul>
 *   <li>insert / search / delete : O(log n) average, O(n) worst case</li>
 *   <li>in-order / pre-order / post-order : O(n)</li>
 * </ul>
 *
 * <p>Worst case O(n) occurs when elements are inserted in sorted order,
 * turning the tree into a linked list.  A self-balancing tree (AVL, Red-Black)
 * avoids this at the cost of additional complexity.
 */
public class BinarySearchTree {

    // ── Inner Node ────────────────────────────────────────────────────────────

    private static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        TreeNode(int data) {
            this.data = data;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private TreeNode root;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Creates an empty BST. */
    public BinarySearchTree() {}

    // ── Core operations ───────────────────────────────────────────────────────

    /**
     * Inserts {@code data} into this tree.  Duplicate values are ignored.
     *
     * @param data the value to insert
     */
    public void insert(int data) {
        root = insertRecursive(root, data);
    }

    private TreeNode insertRecursive(TreeNode current, int data) {
        if (current == null) return new TreeNode(data);
        if (data < current.data)      current.left  = insertRecursive(current.left,  data);
        else if (data > current.data) current.right = insertRecursive(current.right, data);
        // duplicate — do nothing
        return current;
    }

    /**
     * Returns {@code true} if {@code data} exists in this tree.
     *
     * @param data the value to search for
     * @return {@code true} if found
     */
    public boolean search(int data) {
        return searchRecursive(root, data);
    }

    private boolean searchRecursive(TreeNode current, int data) {
        if (current == null)      return false;
        if (data == current.data) return true;
        return data < current.data
                ? searchRecursive(current.left, data)
                : searchRecursive(current.right, data);
    }

    /**
     * Deletes {@code data} from this tree.  Three cases are handled:
     * <ol>
     *   <li>Leaf node — simply removed.</li>
     *   <li>One child — replaced by its child.</li>
     *   <li>Two children — replaced by its in-order successor (minimum of right subtree),
     *       then the successor is deleted from the right subtree.</li>
     * </ol>
     *
     * @param data the value to delete
     */
    public void delete(int data) {
        root = deleteRecursive(root, data);
    }

    private TreeNode deleteRecursive(TreeNode current, int data) {
        if (current == null) return null;

        if (data < current.data) {
            current.left = deleteRecursive(current.left, data);
        } else if (data > current.data) {
            current.right = deleteRecursive(current.right, data);
        } else {
            // Found the node to delete
            if (current.left == null)  return current.right;
            if (current.right == null) return current.left;

            // Two children: replace with in-order successor
            TreeNode successor = findMin(current.right);
            current.data  = successor.data;
            current.right = deleteRecursive(current.right, successor.data);
        }
        return current;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // ── Traversals ────────────────────────────────────────────────────────────

    /**
     * Prints elements in ascending order (Left → Root → Right).
     * This is the defining property of a BST.
     */
    public void inOrder() {
        System.out.print("In-order:  ");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(TreeNode node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(node.data + " ");
            inOrderRecursive(node.right);
        }
    }

    /** Prints elements Root → Left → Right (useful for tree cloning). */
    public void preOrder() {
        System.out.print("Pre-order: ");
        preOrderRecursive(root);
        System.out.println();
    }

    private void preOrderRecursive(TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    /** Prints elements Left → Right → Root (useful for safe deletion). */
    public void postOrder() {
        System.out.print("Post-order:");
        postOrderRecursive(root);
        System.out.println();
    }

    private void postOrderRecursive(TreeNode node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /**
     * Returns the height of this tree.
     * An empty tree has height -1; a single-node tree has height 0.
     */
    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(TreeNode node) {
        if (node == null) return -1;
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }

    /** Returns {@code true} if this tree contains no elements. */
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "(empty BST)";
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb, "", true);
        return sb.toString();
    }

    private void toStringHelper(TreeNode node, StringBuilder sb, String prefix, boolean isLast) {
        if (node == null) return;
        sb.append(prefix).append(isLast ? "└── " : "├── ").append(node.data).append('\n');
        toStringHelper(node.left,  sb, prefix + (isLast ? "    " : "│   "), node.right == null);
        toStringHelper(node.right, sb, prefix + (isLast ? "    " : "│   "), true);
    }
}
