package ua.com.alevel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DiAppStarter {

    private final DiContext diContext;

    public DiAppStarter(DiContext diContext) {
        this.diContext = diContext;
    }

    public void startApp() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> starterCls = diContext.getStarter();
        Constructor<?> constructor = starterCls.getDeclaredConstructor();
        Object starterInstance = constructor.newInstance();
        starterCls.getDeclaredMethod("run").invoke(starterInstance);
    }
}
