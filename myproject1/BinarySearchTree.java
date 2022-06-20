package myproject;
import java.util.Comparator;
import java.util.*;

public class BinarySearchTree<T> {
    
    //----------nested Node class
    protected static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;
        Node<T> parent;


        public Node(T elem, Node<T> p, Node<T> l, Node<T> r) {
            this.element = elem;
            parent = p;
            left = l;
            right = r;
        }

        public Node(T elem) {
            this(elem, null, null, null);
        }

        public String toString() {
            return element.toString();
        }
    }
    //--------end of nested Node class----------
    
    Comparator<T> comp;
    int size;
    Node<T> root;

    /**Constructs an empty Binary Search Tree with a given comparator*/
    public BinarySearchTree(Comparator<T> c) {
        this.comp = c;
        size = 0;
        root = null;
    }


    /**Constructs an empty Binary Search Tree with a default comparator*/
    public BinarySearchTree() {this(new DefaultComparator());}

    public Node<T> treeSearch(Node<T> x, T key) {
        if (key == null) throw new IllegalArgumentException("Error: null passed instead of a valid key");


        if (x == null || comp.compare(x.element, key) == 0) return x;

        else if (comp.compare(key, x.element) < 0) return treeSearch(x.left,key);

        else return treeSearch(x.right, key);
    }

    public Node<T> iterativeTreeSearch(Node<T> x, T key) {
        Node<T> z = x;

        while (z != null && comp.compare(key, z.element) != 0) {
                if (comp.compare(key, z.element) < 0) z = z.left;
                else
                        z = z.right;
        }
        return z;
    }

    public Node<T> treeMinimum(Node<T> x) {
        if (x == null) throw new IllegalArgumentException("Error: null instead of a valid Node object");

        while (x.left != null) x = x.left;
        return x;
    }

    public Node<T> treeMaximun(Node<T> x) {
        if (x == null) throw new IllegalArgumentException("Error: null instead of a valid Node object");

        while (x.right != null) x = x.right;
        return x;
    }

    public Node<T> treeSuccessor(Node<T> x) {
        if (x == null) throw new IllegalArgumentException("Error: null instead of a valid Node object");

        if (x.right != null) return treeMinimum(x.right);

        Node<T> z = x.parent;

        while (z != null && x == z.right) {
                x = z;
                z = z.parent;
        }
        return z;
    }

    public Node<T> treePredecessor(Node<T> x) {
        if (x == null) throw new IllegalArgumentException("Error: null instead of a valid Node object");

        if (x.left != null) return treeMaximun(x.left);

        Node<T> z = x.parent;

        while (z != null && x == z.left) {
                x = z;
                z = z.parent;
        }
        return z;
    }
    
    public void treeInsert(Node<T> newest) {
        if (newest == null) throw new IllegalArgumentException("Error: null instead of a valid object");
        
        T z = newest.element;
        Node<T> y = null;
        Node<T> x = this.root;

        while (x != null) {
            y = x;
            if (comp.compare(z, x.element) < 0) x = x.left;
            else
                x = x.right;
        }
        
        if (y == null) {
            this.root = newest;
        } else {
            if (comp.compare(z, y.element) < 0) y.left = newest;
            else
                y.right = newest;
        }
        newest.parent = y;

        this.size++;
    }
    
    
    public Node<T> treeInsert(T z) {
        if (z == null) throw new IllegalArgumentException("Error: null instead of a valid object");

        Node<T> newest = new Node<>(z);
        
        this.treeInsert(newest);
        
        return newest;
        //BinaryTreePrinter.printNode(this.root);
    }
    

    public void treeDelete(T z) {
        if (z == null) throw new IllegalArgumentException("Error: null instead of a valid object");

        Node<T> temp = treeSearch(this.root, z);

        if (temp == null) return;

        if (temp.left == null && temp.right == null) {
            Node<T> y = temp.parent;
            temp.parent = null;
            if (temp == y.left) y.left = null;
            else
                y.right = null;

        } else {
            Node<T> x;

            if (temp.left != null) x = treeMaximun(temp.left);
            else
                x = treeMinimum(temp.right);

            temp.element = x.element;

            Node<T> y = x.parent;

            if (y.left == x) y.left = null;
            else
                y.right = null;
                x.parent = null;
        }
    }



    public String toString() {
        List<String> allines = BinaryTreePrinter.treeString(this.root);
        
        System.out.println("---------printing the whole tree--------");
        StringBuilder answer = new StringBuilder();
        for (String s : allines) {
            answer.append(s + "\n");
        }

        return answer.toString();
    }

}

class BinaryTreePrinter {
    static String horizontalGap = new String(new char[4]).replace("\0", " ");
    static String verticalGap = new String(new char[4]).replace("\0", " ");

    public static <T> ArrayList<String> treeString(BinarySearchTree.Node<T> x) {
        if (x.left == null && x.right == null) {
            ArrayList<String> answer =  new ArrayList<>();
            answer.add(x.toString());
            return answer;
        }

        ArrayList<String> leftSubtree = new ArrayList<>();
        leftSubtree.add("");
        ArrayList<String> rightSubtree = new ArrayList<>();
        rightSubtree.add("");


        if (x.left != null) {
            leftSubtree = treeString(x.left);

//            System.out.println("----------printing left subtree---------");
//            for (String s : leftSubtree) {
//                System.out.println(s);
//            }
            //System.out.println("leftSubtree = " + leftSubtree);
        }

        if (x.right != null) {
            rightSubtree = treeString(x.right);

//            System.out.println("----------printing right subtree---------");
//            for (String s : rightSubtree) {
//                System.out.println(s);
//            }
            //System.out.println("rightSubtree = " + rightSubtree);
        }


    int leftSubtreeWidth = 0;
    int rightSubtreeWidth = 0;

    leftSubtreeWidth = leftSubtree.get(0).length();
    rightSubtreeWidth = rightSubtree.get(0).length();

    //System.out.println("leftSubtreeWidth = " + leftSubtreeWidth);
    //System.out.println("rightSubtreeWidth = " + rightSubtreeWidth);

    String firstLine = strRepeat(" ", leftSubtreeWidth/2) + "/" + strRepeat("-", leftSubtreeWidth/2 + horizontalGap.length() - 1) + x + 
                                             strRepeat("-", rightSubtreeWidth/2 + horizontalGap.length() - 1) + "\\" + strRepeat(" ", rightSubtreeWidth/2);

    

    ArrayList<String> answer = new ArrayList<>();
    answer.add(firstLine);
    

    //System.out.println("firstLine = /" + firstLine + "/");
    

    for (int i = 0; i < Math.max(leftSubtree.size(), rightSubtree.size()); i++) {
        StringBuilder line = new StringBuilder();

        if (i < leftSubtree.size()) line.append(leftSubtree.get(i));
        else
            line.append(strRepeat(" ", Math.max(leftSubtreeWidth, rightSubtreeWidth)));
        

        line.append(horizontalGap +  strRepeat(" ", x.toString().length()) + horizontalGap);

        if (i < rightSubtree.size()) line.append(rightSubtree.get(i));
        else
            line.append(strRepeat(" ", Math.max(leftSubtreeWidth, rightSubtreeWidth)));
        
        //line.append("\n");
        answer.add(line.toString());
        //System.out.println("line = " + line.toString() + "hooll");
    }
    return answer;
}

    private static String strRepeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }

}

