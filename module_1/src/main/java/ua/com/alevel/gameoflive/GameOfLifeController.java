package ua.com.alevel.gameoflive;

import com.example.fxapps.gameoflife.GameOfLifeStarter;
import ua.com.alevel.interfaces.controllers.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;

public class GameOfLifeController implements ConsoleController {
    @Override
    public void start(BufferedReader reader) throws IOException {
        GameOfLifeStarter.main(new String[]{});
    }
}
