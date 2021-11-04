package ua.com.alevel.knightmove;

import com.example.demofx.KnightsMoveStarter;
import ua.com.alevel.interfaces.controllers.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;

public class KnightMoveController implements ConsoleController {
    @Override
    public void start(BufferedReader reader) throws IOException {
        KnightsMoveStarter.main(new String[]{});
    }
}
