package org.openjfx;

import java.io.IOException;


import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.openjfx.Models.TreeNode;
import org.openjfx.controller.Controller;
import org.openjfx.controller.Util;

import java.util.HashMap;
import java.util.Map;


public class PrimaryController {
    @FXML
    private JFXTextField inputText;

    @FXML
    private JFXScrollPane jfxScrollPane;
    @FXML
    private JFXListView<Label> myList;

    @FXML private Label lblOriginalSize, lblCompressionSize, lblRatio;

    Controller controller = new Controller();
    Util util = new Util();


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    @FXML
    private void onButtonPressed() {
        HashMap<Character, Integer> mapChar;
        Map<Character, String> prefixMap = new HashMap<>();
        if (!inputText.getText().trim().isEmpty()) {
            System.out.println(inputText.getText());

            //* fill map from distinct character
            mapChar = controller.initMapCharacter(inputText.getText().trim());
            System.out.println("distinct characters (map) are");
            util.printMap(mapChar);

            //* build trieInfo and get the root
            TreeNode root = controller.constructTreeFromMap(mapChar);
            System.out.println("print preorder tree");
            util.printPreOrderTraversal(root);

            //* fill map with Prefix Free Code for each distinct character
            prefixMap = controller.getMapPrefixFreeCode(prefixMap, root, "");
            System.out.println("prefix free code (map) is");
            util.printPrefixMap(prefixMap);

            //* lets get compression text
            String compressionText = controller.getCompressionText(inputText.getText(), prefixMap);
            System.out.println("compression text is");
            System.out.println(compressionText);

            //* let's get trie info
            String trieInfo = controller.getTrieInfo(prefixMap);

            //* lets get final result, by appending trie info and compression text
            String finalResult = trieInfo + compressionText;
            System.out.println("final result is");
            System.out.println(finalResult);

            //* original size input text length * 1 byte
            System.out.println("original size " + inputText.getText().length() + " byte");
            int compressionSize = controller.getCompressionSizeInByte(finalResult);
            System.out.println("compression size " + compressionSize + " byte");

            //* let's get ratio compression
            int ratio = controller.getPercentageRatioCompression(compressionSize, inputText.getText().length());
            System.out.println("ratio compression is :" + ratio + "%");

            lblCompressionSize.setText(Integer.toString(compressionSize) + " BYTE");
            lblOriginalSize.setText(Integer.toString(inputText.getText().length()) + " BYTE");
            lblRatio.setText(ratio + "%");
        }
    }


}
