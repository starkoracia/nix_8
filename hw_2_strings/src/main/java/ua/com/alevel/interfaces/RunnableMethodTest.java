package ua.com.alevel.interfaces;

import java.io.BufferedReader;
import java.io.IOException;

public interface RunnableMethodTest {
    void start(BufferedReader reader, boolean isWordsReverse) throws IOException;
}
