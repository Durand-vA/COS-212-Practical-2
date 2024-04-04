
public class BST<T extends Comparable<T>> {

    private static int signum(int n) {
        return Integer.compare(n, 0);
    }

    public BinaryNode<T> root;

    public BST() {
        root = null;
    }

    public BST(BinaryNode<T> node) {
        if (node == null) {
            root = null;
            return;
        }
        root = new BinaryNode<>(node.data);

        copy(root, node);
    }

    private void copy(BinaryNode<T> node, BinaryNode<T> other) {
        if (other == null) {
            return;
        }
        if (other.left != null) {
            node.left = new BinaryNode<>(other.left.data);
        }
        copy(node.left, other.left);
        if (other.right != null) {
            node.right = new BinaryNode<>(other.right.data);
        }
        copy(node.right, other.right);
    }

    private BinaryNode<T> getParent(T data) throws RuntimeException {
        if (!contains(data))
            throw new RuntimeException();
        // Return null if the root is null
        if (root == null) {
            return null;
        }
        // Return null if the node we are looking f0r is the root
        if (root.data.compareTo(data) == 0) {
            return null;
        }
        // Begin recursive search
        return getParent(data, root);
    }

    private BinaryNode<T> getParent(T data, BinaryNode<T> node) {
        if (node == null) {
            return null;
        }

        // Data smaller than this node, look to the left
        if (data.compareTo(node.data) < 0) {
            // Probably unnecessary null check
            if (node.left == null) {
                return null;
            } else {
                // check if left child has correct data
                if (node.left.data.compareTo(data) == 0) {
                    // Return this node when data is found
                    return node;
                }
                // Continue binary search on the left
                return getParent(data, node.left);
            }
        }

        // Data greater than this node, look to the right
        if (data.compareTo(node.data) > 0) {
            // Probably unnecessary null check
            if (node.right == null) {
                return null;
            } else {
                // Check if right child has correct data
                if (node.right.data.compareTo(data) == 0) {
                    // Return this node when data is found
                    return node;
                }
                // Continue binary search on the right
                return getParent(data, node.right);
            }
        }

        return null;
    }

    public void delete(T data) {
        // Find node with data
        BinaryNode<T> node = getNode(data);
        // Null check
        if (node == null) {
            return;
        }

        // Get parent of the node
        BinaryNode<T> parentNode = getParent(data);

        // Booleans to show which child the current node is of its parent node
        boolean rightChild = parentNode != null && data.compareTo(parentNode.data) > 0;
        boolean leftChild = parentNode != null && data.compareTo(parentNode.data) < 0;

        // No children, left and right are null
        if (node.left == null && node.right == null) {
            // Delete leaf node

            // Depending on which child the node is, set parent pointer to null
            if (leftChild) {
                parentNode.left = null;
                return;
            }
            if (rightChild) {
                parentNode.right = null;
                return;
            }
            // Fall through if parent existn't.
            // Node to be deleted will be root, and since no children exist, root will be set to null
            root = null;
            return;
        }
        // At least one of node.left or node.right exists
        if (node.left == null || node.right == null) {
            // Only one of the children exist
            // Delete node with one child
            if (node.left != null) {
                if (leftChild) {
                    parentNode.left = node.left;
                    return;
                }
                if (rightChild) {
                    parentNode.right = node.left;
                    return;
                }
            } else {
                if (leftChild) {
                    parentNode.left = node.right;
                    return;
                }
                if (rightChild) {
                    parentNode.right = node.right;
                    return;
                }
            }
            root = node.left != null ? node.left : node.right;
            return;
        }
        // node.left and node.right exist

        // Save smallest value on the right side of node
        T otherData = findMin(node.right).data;
        // Delete that value
        delete(otherData);
        // Overwrite this node's data
        node.data = otherData;
    }

    public boolean contains(T data) {
        // call getNode and check if it returns a valid pointer
        return getNode(data) != null;
    }

    public void insert(T data) {
        // Check if root is null
        if (root == null) {
            // Initialize root
            root = new BinaryNode<>(data);
            return;
        }
        // Start recursive search f0r space to add data
        insert(data, root);
    }

    private void insert(T data, BinaryNode<T> node) {
        // Null check
        if (node == null) {
            return;
        }

        // Calculate the sign of the comparison of data to be added with old data
        // Will be 0 if equal, -1 if data is smaller than node.data, 1 if data is greater than node.data
        switch (signum(data.compareTo(node.data))) {
            case 0:
                return;

            case -1:
                // if left child is null, create new, else continue search from that child
                if (node.left == null) {
                    node.left = new BinaryNode<>(data);
                } else {
                    insert(data, node.left);
                }
                return;

            case 1:
                // if right child is null, create new, else continue search from that child
                if (node.right == null) {
                    node.right = new BinaryNode<>(data);
                } else {
                    insert(data, node.right);
                }
        }
//        if (data.compareTo(node.data) == 0) {
//            return;
//        }
//
//        if (data.compareTo(node.data) < 0) {
//            if (node.left == null) {
//                node.left = new BinaryNode<>(data);
//            } else {
//                insert(data, node.left);
//            }
//            return;
//        }
//
//        if (data.compareTo(node.data) > 0) {
//            if (node.right == null) {
//                node.right = new BinaryNode<>(data);
//            } else {
//                insert(data, node.right);
//            }
//        }
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BinaryNode<T> node) {
        // Base case, height of tree with no nodes is 0
        if (node == null) {
            return 0;
        }

        // Get max height of trees on the left and right and add one
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public String printSearchPath(T data) {
        return printSearchPath(data, root, new StringBuilder()).toString();
    }

    private StringBuilder printSearchPath(T data, BinaryNode<T> node, StringBuilder sb) {
        // Base case where null node has been reached. Also, null check
        if (node == null) {
            sb.append("Null");
            return sb;
        }

        // Append node data immediately when node is visited
        sb.append(node.data);

        if (data.compareTo(node.data) < 0) {
            // Append " -> " when searching further
            sb.append(" -> ");
            // Continue searching from left child
            return printSearchPath(data, node.left, sb);
        }

        if (data.compareTo(node.data) > 0) {
            // Append " -> " when searching further
            sb.append(" -> ");
            // Continue searching from right child
            return printSearchPath(data, node.right, sb);
        }

        return sb;
    }

    public int getNumLeaves() {
        // Start recursive call
        return getNumLeaves(root);
    }

    private int getNumLeaves(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }

        // If node has no children, it's a leaf. Return 1
        if (node.left == null && node.right == null) {
            return 1;
        }

        // Count all the leaves on the left and the right, and sum them
        return getNumLeaves(node.left) + getNumLeaves(node.right);
    }

    public BST<T> extractBiggestSuperficiallyBalancedSubTree() {
//        int count = countNodes(root);
//        BinaryNode<T>[] balancedNodes = new BinaryNode[count];
//        int[] counts = new int[count];
//        popArr(counts, 0);
//        getBalancedSubtrees(root, balancedNodes, counts, 0);
//
//        int maxIndex = largestSubtree(counts);
//
//        if (maxIndex < 0) {
//            return new BST<>();
//        }

//        return new BST<>(balancedNodes[maxIndex]);

        return new BST<>(extractBiggestSuperficiallyBalancedSubTree(root));
    }

    private BinaryNode<T> extractBiggestSuperficiallyBalancedSubTree(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        if (isSuperficiallyBalanced(node)) {
            return node;
        }

        BinaryNode<T> left = extractBiggestSuperficiallyBalancedSubTree(node.left);
        BinaryNode<T> right = extractBiggestSuperficiallyBalancedSubTree(node.right);

        int countLeft = countNodes(left);
        int countRight = countNodes(right);

        if (countRight > countLeft) {
            return right;
        }
        return left;
    }

    public BinaryNode<T> getNode(T data) {
        // Begin recursive call
        return getNode(data, root);
    }

    private BinaryNode<T> getNode(T data, BinaryNode<T> node) {
        // Null check
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            // If node.left is null, this means that the list not contain the data
            if (node.left == null) {
                return null;
            } else {
                // Continue search from left child
                return getNode(data, node.left);
            }
        }

        if (data.compareTo(node.data) > 0) {
            // If node.right is null, this means that the list not contain the data
            if (node.right == null) {
                return null;
            } else {
                // Continue search from right child
                return getNode(data, node.right);
            }
        }

        // Will fall through to here when data is found. Return that node
        return node;
    }

    public boolean isSuperficiallyBalanced() {
        return isSuperficiallyBalanced(root);
    }

    private int countNodes(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private boolean isSuperficiallyBalanced(BinaryNode<T> node) {
        if (node == null) {
            return true;
        }
        return countNodes(node.left) == countNodes(node.right);
    }
    public BinaryNode<T> findMax() {
        // Begin recursive call
        return findMax(root);
    }

    private BinaryNode<T> findMax(BinaryNode<T> node) {
        // If node is null, root is null and no maximum exists
        if (node == null) {
            return null;
        }

        // Continue search until node.right is null, then return that node
        if (node.right != null) {
            return findMax(node.right);
        }
        return node;
    }

    public BinaryNode<T> findMin() {
        // Begin recursive call
        return findMin(root);
    }

    private BinaryNode<T> findMin(BinaryNode<T> node) {
        // If node is null, root is null and no minimum exists
        if (node == null) {
            return null;
        }

        // Continue search until node.left is null, then return that node
        if (node.left != null) {
            return findMin(node.left);
        }
        return node;
    }

    ///////////////

    private StringBuilder toString(BinaryNode<T> node, StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if (node.right != null) {
            toString(node.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.data.toString()).append("\n");
        if (node.left != null) {
            toString(node.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return root == null ? "Empty tree" : toString(root, new StringBuilder(), true, new StringBuilder()).toString();
    }

}
