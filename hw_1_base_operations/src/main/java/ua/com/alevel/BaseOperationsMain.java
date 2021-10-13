package ua.com.alevel;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Cleanup;

import java.io.*;

import static ua.com.alevel.HomeworkStarter.*;


public class BaseOperationsMain {
    public static void main(String[] args) throws IOException {

        HomeworkStarter.run();


//        hwNumberTwoStart();
//        hwNumberThreeStart();

//        DefaultTerminalFactory defaultTerminalFactory =
//                new DefaultTerminalFactory();
//
//        Terminal terminal = null;

//        try {
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(terminal != null) {
//                try {
//                    terminal.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
