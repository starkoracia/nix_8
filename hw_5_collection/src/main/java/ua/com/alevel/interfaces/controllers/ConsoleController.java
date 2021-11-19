package ua.com.alevel.interfaces.controllers;

import java.io.BufferedReader;
import java.io.IOException;

public interface ConsoleController {
    void start(BufferedReader reader) throws IOException;
}
