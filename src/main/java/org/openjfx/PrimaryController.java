package org.openjfx;

import java.io.IOException;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.openjfx.Models.TreeNode;
import org.openjfx.controller.Controller;
import org.openjfx.controller.Util;
import java.util.HashMap;
import java.util.Map;


public class PrimaryController {
    @FXML
    private JFXTextField inputText;

    @FXML
    private Label lblOriginalSize, lblCompressionSize, lblRatio, lblResultFor;

    @FXML
    private ScrollPane paneResult;

    @FXML
    private TableView freqTable;

    @FXML
    private TableColumn charColumn, freqColumn;

    Controller controller = new Controller();
    Util util = new Util();


    public static final String Column1MapKey = "A";
    public static final String Column2MapKey = "B";


    @FXML
    private void onButtonPressed() {


        HashMap<Character, Integer> charFreqMap;
        Map<Character, String> prefixMap = new HashMap<>();
        if (!inputText.getText().trim().isEmpty()) {
            System.out.println(inputText.getText());

            //* fill map from distinct character
            charFreqMap = controller.initMapCharacter(inputText.getText().trim());
            System.out.println("distinct characters (map) are");
            util.printMap(charFreqMap);
      
            //* build trieInfo and get the root
            TreeNode root = controller.constructTreeFromMap(charFreqMap);
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

            lblCompressionSize.setText(compressionSize + " BYTE");
            lblOriginalSize.setText(inputText.getText().length() + " BYTE");
            lblRatio.setText(ratio + "%");
            lblResultFor.setText("Result for " + inputText.getText() + " :");


            Text text = new Text(finalResult);
            text.setWrappingWidth(paneResult.getWidth() - 10);
            paneResult.setContent(text);
            paneResult.setPadding(new Insets(4.0, 4.0, 4.0, 4.0));
            paneResult.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


            //*Construct Table
            charColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
            charColumn.setMinWidth(130);
            freqColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
            freqColumn.setMinWidth(130);

            freqTable.setItems(generateDataInMap(charFreqMap));

            freqTable.setEditable(true);
            freqTable.getSelectionModel().setCellSelectionEnabled(true);
            freqTable.getColumns().setAll(charColumn, freqColumn);
            Callback<TableColumn<Map, String>, TableCell<Map, String>>
                    cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
                        @Override
                        public String toString(Object t) {
                            return t.toString();
                        }
                        @Override
                        public Object fromString(String string) {
                            return string;
                        }
                    });
            charColumn.setCellFactory(cellFactoryForMap);
            freqColumn.setCellFactory(cellFactoryForMap);

        }

    }

    //* function that will return ObservableList  from map for Table
    private ObservableList<Map> generateDataInMap(Map<Character, Integer> mapCharFreq) {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        mapCharFreq.entrySet().forEach(entry -> {
            Map<String, String> dataRow = new HashMap<>();
            String value1 =  entry.getKey().toString();
            String value2 =  entry.getValue().toString();

            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2);

            allData.add(dataRow);

        });
        return allData;
    }


}
