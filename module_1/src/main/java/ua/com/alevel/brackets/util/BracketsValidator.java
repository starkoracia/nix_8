package ua.com.alevel.brackets.util;

import java.util.EmptyStackException;
import java.util.Stack;

public class BracketsValidator {

    public static boolean isBracketsValid(String inputString) {
        String[] openBrackets = {"(", "{", "["};
        String[] closeBrackets = {")", "}", "]"};
        String[] symbols = inputString.split("");
        Stack<String> bracketsStack = new Stack<>();

        for (String symbol : symbols) {

            if(symbol.equals(openBrackets[0]) || symbol.equals(openBrackets[1]) || symbol.equals(openBrackets[2])) {
                bracketsStack.push(symbol);
            }
            for (int i = 0; i < closeBrackets.length; i++) {
                if(symbol.equals(closeBrackets[i])) {
                    String lastOpenBracket = null;
                    try {
                        lastOpenBracket = bracketsStack.pop();
                    } catch (EmptyStackException e) {
                        return false;
                    }
                    if(!lastOpenBracket.equals(openBrackets[i])) {
                        return false;
                    }
                }
            }
        }
        return bracketsStack.empty();
    }

}
