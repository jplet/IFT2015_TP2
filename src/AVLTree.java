// Java program for insertion in AVL Tree

// Class implementation
//private static class AvlNode<AnyType> {
//        AvlNode( AnyType theElement){
//            this( theElement, null, null );
//        }
//        AvlNode( AnyTypetheElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
//            element = theElement;
//            left = lt;
//            right = rt;
//            height = 0;
//        }
//        AnyTypeelement; // The data in the node
//        AvlNode<AnyType> left; // Left child
//        AvlNode<AnyType> right; // Right child
//        int balance; // indice d’équilibre
//}


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class AVLTree {
    //source : https://www.geeksforgeeks.org/avl-tree-set-1-insertion/
    AVLNode root;

    // A utility function to get the height of the tree
    int height(AVLNode N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    AVLNode rotateWithLeftChild(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    AVLNode rotateWithRightChild(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    public AVLNode insert(AVLNode node, MedDescriptor meds) {

        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new AVLNode(meds));

        if (meds.medicamentID < node.key)
            node.left = insert(node.left, meds);
        else if (meds.medicamentID > node.key)
            node.right = insert(node.right, meds);
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && meds.medicamentID < node.left.key)
            return rotateWithLeftChild(node);

        // Right Right Case
        if (balance < -1 && meds.medicamentID > node.right.key)
            return rotateWithRightChild(node);

        // Left Right Case
        if (balance > 1 && meds.medicamentID > node.left.key) {
            node.left = rotateWithRightChild(node.left);
            return rotateWithLeftChild(node);
        }

        // Right Left Case
        if (balance < -1 && meds.medicamentID < node.right.key) {
            node.right = rotateWithLeftChild(node.right);
            return rotateWithRightChild(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    // A utility function to print preorder traversal
    // of the tree.
    // The function also prints height of every node
    public void preOrder(AVLNode node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date date_ = new Date();
        try {
            date_ = dateFormat.parse("2018-11-30".trim());
        } catch (ParseException pe) {
            System.out.println("2018-11-30"+" is not valid.");
        }

        MedDescriptor meds8 = new MedDescriptor(8, 7, date_);
        MedDescriptor meds7 = new MedDescriptor(7, 7, date_);
        MedDescriptor meds2 = new MedDescriptor(2, 7, date_);
        MedDescriptor meds13 = new MedDescriptor(13, 7, date_);
        MedDescriptor meds20 = new MedDescriptor(20, 7, date_);
        MedDescriptor meds21 = new MedDescriptor(21, 7, date_);

        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, meds8);
        tree.root = tree.insert(tree.root, meds7);
        tree.root = tree.insert(tree.root, meds2);
        tree.root = tree.insert(tree.root, meds13);
        tree.root = tree.insert(tree.root, meds20);
        tree.root = tree.insert(tree.root, meds21);

        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);
    }
}
// This code has been contributed by Mayank Jaiswal