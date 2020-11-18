package org.openjfx.controller;

import org.openjfx.Models.TreeNode;
import java.util.HashMap;
import java.util.Map;

public class Util {
    //*print map for debugging purpose
    public void printMap(HashMap<Character, Integer> map) {
        map.entrySet().forEach(entry -> {
            System.out.println("key : " + entry.getKey() + " value :" + entry.getValue());
        });
    }

    //*get character with the most minimum appearance
    public TreeNode getMinimumAppearanceCharacter(HashMap<Character, Integer> map) {
        int minimum = 9999;
        TreeNode character = null;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (minimum > entry.getValue()) {
                character = new TreeNode(entry.getValue(), entry.getKey(), null, null);
                minimum = entry.getValue();
            }
        }
        return character;
    }


    //* function that will print pre order node tree
    public void printPreOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.isLeaf()) {
            System.out.println(root.getCharacter() + "-" + root.getFreq());
        } else {
            System.out.println(root.getFreq());
        }
        printPreOrderTraversal(root.getLeft());
        printPreOrderTraversal(root.getRight());
    }

    //* function that will print prefix map
    public void printPrefixMap(Map<Character, String> prefixMap) {

        for (Map.Entry<Character, String> entry : prefixMap.entrySet()) {
            System.out.println("key " + entry.getKey() + " result " + entry.getValue());
        }
    }

    //* Function that will return deep copy map
    public HashMap<Character, Integer> deepCopyMap(HashMap<Character, Integer> original) {
        HashMap<Character, Integer> copy = new HashMap<>();
        original.entrySet().forEach(entry -> {
            copy.put(entry.getKey(), entry.getValue());
        });
        return copy;
    }
}