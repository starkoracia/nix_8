package ua.com.alevel.utils;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWord;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

public final class ConsoleHelperUtil {
    private ConsoleHelperUtil() {
    }

    public static String createMessagesTableString(SimpleList<Message> messages) {
        String[] columnNames = {"№", "Comment", "Author"};
        String[][] tableData = createMessagesTableData(messages, columnNames);

        return getString(tableData);
    }

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

    private static String[][] createMessagesTableData(SimpleList<Message> messages, String ...columnNames) {
        String[][] data = new String[messages.size() + 1][];
        data[0] = columnNames;
        for (int i = 1; i < data.length; i++) {
            data[i] = new String[]
                    {String.format("%d", i),messages.get(i - 1).getText(), messages.get(i - 1).getAuthor().getName()};
        }
        return data;
    }

    public static String createUsersTableString(SimpleList<User> users) {
        String[] columnNames = {"№", "Email", "Password", "Name"};
        String[][] tableData = createUsersTableData(users, columnNames);

        return getString(tableData);
    }

    private static String[][] createUsersTableData(SimpleList<User> users, String ...columnNames) {
        String[][] data = new String[users.size() + 1][];
        data[0] = columnNames;
        for (int i = 1; i < data.length; i++) {
            data[i] = new String[]
                    {String.format("%d", i),users.get(i - 1).getEmail(), users.get(i - 1).getPassword(), users.get(i - 1).getName()};
        }
        return data;
    }

    public static String tableFromUser(User user) {
        SimpleList<User> users = new SimpleList<>();
        users.add(user);
        return createUsersTableString(users);
    }
}
