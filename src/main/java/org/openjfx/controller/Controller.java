package org.openjfx.controller;

import org.openjfx.Models.TreeNode;

import java.util.*;

public class Controller {
    Util util = new Util();

    //*function that will put distinct character into map <Character, Integer>
    public HashMap<Character, Integer> initMapCharacter(String text) {
        HashMap<Character, Integer> map = new HashMap<>();

        for (char character : text.toCharArray()) {
            if (!map.containsKey(character)) {
                map.put(character, 1);
            } else {
                map.put(character, (map.get(character) + 1));
            }
        }

        return map;
    }

    public TreeNode constructTreeFromMap(HashMap<Character, Integer> map) {
        Queue<TreeNode> priorityQueue = new LinkedList<>();
        int repeatFor = 0;

        //* map size is an odd number
        if (map.size() % 2 > 0) {
            repeatFor = map.size() / 2 + 1;
        } else {
            repeatFor = map.size() / 2;
        }

        for (int i = 0; i < repeatFor; i++) {
            TreeNode modelCharacter1;
            TreeNode modelCharacter2 = null;
            //*pick character 2 times
            modelCharacter1 = util.getMinimumAppearanceCharacter(map);
            //* character that is picked is removed from the map
            map.remove(modelCharacter1.getCharacter());


            //* when repeat for is an odd number, in the latest loop, there will be 1 character left
            if (map.size() > 0) {
                modelCharacter2 = util.getMinimumAppearanceCharacter(map);
                //*set as picked
                map.remove(modelCharacter2.getCharacter());
            }
            int total = 0;
            total += (modelCharacter2 != null ? (modelCharacter2.getFreq() + modelCharacter1.getFreq()) : modelCharacter1.getFreq());

            TreeNode treeNode = new TreeNode(total, '\0', modelCharacter1, modelCharacter2);
            priorityQueue.add(treeNode);

        }

        return buildTreeFromQueue(priorityQueue);
    }

    public TreeNode buildTreeFromQueue(Queue<TreeNode> queues) {
        TreeNode n;
        TreeNode m = null;
        TreeNode root;
        while (queues.size() > 1) {
            n = queues.poll();
            if (!queues.isEmpty()) {
                m = queues.poll();
            }
            if (m != null) {
                root = new TreeNode(n.getFreq() + m.getFreq(), '\0', n, m);
            } else {
                root = new TreeNode(n.getFreq(), '\0', n, m);
            }
            queues.add(root);
        }
        root = queues.poll();
        return root;
    }

    public Map<Character, String> getMapPrefixFreeCode(Map<Character, String> prefixMap, TreeNode root, String result) {
        if (root == null) {
            return prefixMap;
        }
        if (root.getLeft() != null) {
            //*append 0
            prefixMap = getMapPrefixFreeCode(prefixMap, root.getLeft(), (result + "0"));
        }
        if (root.getRight() != null) {
            //*append 1
            prefixMap = getMapPrefixFreeCode(prefixMap, root.getRight(), (result + "1"));

        }
        if (root.isLeaf()) {
            prefixMap.put(root.getCharacter(), result);
        }

        return prefixMap;
    }

    public String getCompressionText(String text, Map<Character, String> prefixMap) {
        //* assumption : 1 character is 1 byte
        String result = "";
        for (char character : text.toCharArray()) {
            result += prefixMap.get(character);
        }
        return result;
    }

    public String getTrieInfo(Map<Character, String> prefixMap) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, String> entry : prefixMap.entrySet()) {
            //*get prefix free code
            String prefixFreeCode = entry.getValue();

            //* get ascii from character and convert it to binary string string
            String binaryAscii = Integer.toBinaryString(((int) entry.getKey()));

            //*prefix free code + ascii in binary string
            result.append(prefixFreeCode).append(binaryAscii);

        }

        return result.toString();
    }

    public int getCompressionSizeInByte(String text) {
        //* assumption that each character in text is 1 bit
        //* divided by 8 to convert it to byte
        return (int) Math.round(text.length() / 8.0);
    }

    public int getPercentageRatioCompression(int compressionSize, int textSize) {
        return (int) Math.round((100 - (compressionSize * 100.0 / textSize)));
    }

}