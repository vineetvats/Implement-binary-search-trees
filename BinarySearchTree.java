/** @author Manindra Kumar Anantaneni (mxa180038)
 * Vineet Vats (vxv180008)
 * Akshay Kanduri (axk175430)
 *Binary Search Tree
 **/

package mxa180038;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {//Here we are extending the comparable interface.
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    static class Entry<T> {
        T element;
        Entry<T> left, right;// keeping track of left and right child.

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size, index=0;
    Stack<Entry<T>> s = new Stack<>();

    public BinarySearchTree() {//constructor
        root = null;
        size = 0;
    }

    public Entry<T> find(Entry<T> t, T x){// find function used to find an element within subtree rooted at t.
        if (t == null || t.element == x) return t;
        while(true){
            if(x.compareTo(t.element) < 0){
                if (t.left == null) break;
                else {
                    s.push(t);
                    t = t.left;
                }
            }
            else if(x.compareTo(t.element) == 0) break;
            else if (t.right == null) break;
            else {
                s.push(t);
                t = t.right;
            }
        }
        return t;
    }

    public Entry<T> find(T x){// find function for the root only.
        //s = new Stack<>();
        s.push(null);
        return find(root,x);
    }


    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        if (t == null || t.element != x) return true;
        else
        return false;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {//
        return (T) find(x);
    }

    /**  Add x to tree.
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if(size == 0){
            root = new Entry<>(x,null,null);
            size++;
            return true;
        }
        else{
            Entry<T> t = find(x);
            if(t.element == x) {
                t.element = x;
                return false;
            }
            else if(x.compareTo(t.element)<0) t.left = new Entry<>(x,null,null);
            else t.right = new Entry<>(x,null,null);

        }
        size++;
        return true;
    }

    /** function: Remove x from tree.
     * It Return x if found, otherwise it return null
     */
    public T remove(T x) {
        if(root == null) return null;
        Entry<T> t = find(x);
        if (t.element != x) return null;
        T result = t.element;
        if(t.left == null || t.right == null) bypass(t);
        else{
            s.push(t);
            Entry<T> minRight = find(t.right,x);
            t.element = minRight.element;
            bypass(minRight);
        }
        size--;
        return result;
    }

    public void bypass(Entry<T> t){// We call this function inside remove function so that we can remove a node and bypass that element.
        Entry<T> parent = s.peek();
        Entry<T> child = t.left == null? t.right:t.left;
        if(parent == null) root = child;
        else
            if(parent.left == t) parent.left = child;
            else parent.right = child;
    }

    public T min() {// This function will return the minimum of all the elements.
        if(size == 0) return null;
        Entry<T> t = root;
        while (t.left != null){
            t = t.left;
        }
        return t.element;
    }

    public T max() {//This function will return the maximum of all the elements.
        if (size == 0) return null;
        Entry<T> t = root;
        while (t.right != null){
            t = t.right;
        }
        return t.element;
    }

    //this function  Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        /* write code to place elements in array here */
        //int i = 0;
        inOrder(root,arr);
        return arr;
    }

    public void inOrder(Entry<T> r, Comparable[] arr){// function for inorder traversal and which is called in toarray function.
        if(r != null) {
            inOrder(r.left, arr);
            arr[index++] = r.element;
            inOrder(r.right, arr);
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");//if we give a positive number which is greater than 0 then add function is called here.
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");//if we give a positive number which is smaller than 0 then remove function is called here.
                t.remove(-x);
                t.printTree();
            } else {// If we type 0 then the array is displayed in the console
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println(" \n Minimum: " + t.min() + "\n Maximum: "+ t.max() + "\n");//Minimum and maximum values are displayed.
                return;
            }
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10

*/