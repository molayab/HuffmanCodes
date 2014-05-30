/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HuffmanAlgorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 *
 * @author molayab
 */
public class Huffman {
    protected Node root;
    
    private int[] freqs;
    private Map<Character, String> hashes;
    private String str;
    
    public Huffman(String str) {
        this.str = str;
        
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
    
    public String decode(String code) {
        StringBuffer output = new StringBuffer();
        StringBuffer chain = new StringBuffer();
        
        for (int i = 0; i < code.length(); ++i) {
            chain.append(code.charAt(i));
            
            Map<Character, String> h = new HashMap(this.getHashes());
            
            for (Entry<Character, String> map : h.entrySet()) {
                String c = (String)map.getValue();
                
                if (c.equals(chain.toString())) {
                    output.append((Character) map.getKey());
                    
                    chain.delete(0, chain.length());
                    break;
                }
            }
        }
        
        
        
        return output.toString();
    }
    
    public Map<Character, String> getHashes() {
        return new HashMap(this.hashes);
    }
    
    public String getCode() {
        StringBuffer output = new StringBuffer();
        StringBuffer chain = new StringBuffer();
        
        for (int i = 0; i < this.str.length(); ++i) {
            chain.append(str.charAt(i));
            
            Map<Character, String> h = new HashMap(this.getHashes());
            
            for (Entry<Character, String> map : h.entrySet()) {
                Character c = (Character)map.getKey();
                
                if (c.charValue() == str.charAt(i)) {
                    output.append((String) map.getValue());
                    
                    chain.delete(0, chain.length());
                    break;
                }
            }
        }
        
        return output.toString();
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
