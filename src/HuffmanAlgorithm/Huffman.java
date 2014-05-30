/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HuffmanAlgorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author molayab
 */
public class Huffman {
    protected Node root;
    
    private int[] freqs;
    private Map<Character, String> hashes;
    
    public Huffman(String str) {
        freqs = new int[256];
        
        for (char character : str.toCharArray()) {
            freqs[character]++;
        }
        
        this.root = build();
        
        hashes = new HashMap();
    }
    
    private Node build() {
        PriorityQueue<Node> queue = new PriorityQueue();
        
        for (int i = 0; i < freqs.length; ++i) {
            if (freqs[i] > 0) {
                queue.offer(new Node((char)i, freqs[i]));
            }
        }
        
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node rigth = queue.poll();
            
            Node top = new Node('\0', 
                    left.getFrequency() + rigth.getFrequency(), left, rigth);
            
            queue.offer(top);
        }
        
        return queue.poll();
    }
    
    public void compress() {
        StringBuffer buffer = new StringBuffer();
        
        if (this.root != null) {
            huffman(this.root, buffer);
        } 
    }
    
    public static String decode(String code, String alf) {
        Huffman tree = new Huffman(alf);
        
        tree.compress();
        
        StringBuffer output = new StringBuffer();
        StringBuffer input = new StringBuffer(code);
        
        Node node = tree.getTree();
        int index = 0;
        
        while (input.length() > 0 && index < input.length()) {
            char pointer = input.charAt(index);
            
            switch (pointer) {
                case '0':
                    node = node.getLeft();
                    break;
                case '1':
                    node = node.getRigth();
                    break;
                default:
                    break;
            }
            
            if (node.isLeaf()) {
                output.append(node.getValue());
                
                System.out.println(node.getFrequency());
                
                input.delete(0, index);
                
                index = 0;
                
                node = tree.getTree();
            }
            
            ++index;
        }
        
        return output.toString();
    }
    
    public Map<Character, String> getHashes() {
        return this.hashes;
    }
    
    public String getCode() {
        StringBuffer str = new StringBuffer();
        
        Map<Character, String> hash = new HashMap(hashes);
        
        Iterator it = hash.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            str.append(pairs.getValue());
            it.remove(); 
        }
        
        return str.toString();
    }
    
    private void huffman(Node node, StringBuffer buff) {
        if (!node.isLeaf()) {
            buff.append('0');
            huffman(node.getLeft(), buff);
            buff.deleteCharAt(buff.length() - 1);
        
            buff.append('1');
            huffman(node.getRigth(), buff);
            buff.deleteCharAt(buff.length() - 1);
        } else {
            hashes.put(new Character(node.getValue()), buff.toString());
        }
    }
    
    public Node getTree() {
        return root;
    }
}
