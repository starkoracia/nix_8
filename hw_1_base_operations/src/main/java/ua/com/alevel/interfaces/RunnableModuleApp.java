package ua.com.alevel.interfaces;

import java.io.BufferedReader;
import java.io.IOException;

public interface RunnableModuleApp {
    void start(BufferedReader reader) throws IOException;
}
