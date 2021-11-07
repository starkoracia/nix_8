package ua.com.alevel.knightmove;

import com.example.fxapps.knightmove.KnightsMoveStarter;
import ua.com.alevel.interfaces.controllers.ConsoleController;

import java.io.BufferedReader;

public class KnightMoveController implements ConsoleController {
    @Override
    public void start(BufferedReader reader) {
        KnightsMoveStarter.main(new String[]{});
    }
}
