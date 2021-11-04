package ua.com.alevel;

import ua.com.alevel.annotations.Autowired;
import ua.com.alevel.util.ClassSearcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;

public class StarkoDIApplication {


    public static void runApplication(Class<?> appMainClass) {
        DiContext diContext = new DiContext(appMainClass.getPackageName());
        DiAppStarter appStarter = new DiAppStarter(diContext);
        try {
            appStarter.startApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
