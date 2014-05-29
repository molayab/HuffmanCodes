/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HuffmanAlgorithm;

/**
 *
 * @author molayab
 */
public class Node implements Comparable<Node>{
    protected Node left;
    protected Node rigth;
    
    private char value;
    private int frequency;
    
    public Node(char value, int frequency, Node left, Node rigth) {
        this.left = left;
        this.rigth = rigth;
        this.frequency = frequency;
        this.value = value;
    }
    
    public Node(char value, int frequency) {
        this(value, frequency, null, null);
    }
    
    public boolean isLeaf() {
        return (left == null && rigth == null);
    }
    
    public Node getLeft() {
        return this.left;
    }
    
    public Node getRigth() {
        return this.rigth;
    }
    
    public int getFrequency() {
        return this.frequency;
    }
    
    public char getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Node o) {
        return this.frequency - o.getFrequency();
    }
}
