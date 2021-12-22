package ua.com.alevel.util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWord;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public final class ConsoleHelperUtil {

    private ConsoleHelperUtil() { }

//    public static String createMathSetsTableString(MathSet ... mathSets) {
//        String[][] tableData = createMathSetsTableData(mathSets);
//        return getString(tableData);
//    }
//
//    private static String[][] createMathSetsTableData(MathSet ... mathSets) {
//        String[][] data = new String[mathSets.length][];
//        for (int i = 0; i < data.length; i++) {
//            data[i] = new String[mathSets[i].size() + 1];
//            data[i][0] = String.format("№%d", i + 1);
//            for (int j = 1; j < mathSets[i].size() + 1; j++) {
//                data[i][j] = String.format("%s", mathSets[i].get(j - 1));
//            }
//        }
//        return data;
//    }

    private static String getString(String[][] tableData) {
        AsciiTable at = new AsciiTable();

        for (String[] row : tableData) {
            at.addRule();
            at.addRow(row);
        }
        at.addRule();

        at.getRenderer().setCWC(new CWC_LongestWord());
        at.setTextAlignment(TextAlignment.CENTER);
        at.setPaddingLeftRight(1);

        return at.render();
    }

}
