package ua.com.alevel;

import ua.com.alevel.annotations.Autowired;
import ua.com.alevel.factory.InstanceFactory;
import ua.com.alevel.store.InstanceStore;
import ua.com.alevel.util.ClassSearcher;

import java.lang.reflect.Field;

public class DiContext {
    private ClassSearcher classSearcher;
    private InstanceFactory instanceFactory;
    private InstanceStore instanceStore;

    public DiContext(String packageName) {
        classSearcher = ClassSearcher.getInstance(packageName);
        instanceFactory = InstanceFactory.getInstanceFactory(classSearcher);
        instanceStore = InstanceStore.getStore();
        injectionToAutowiredFields();
    }

    private void injectionToAutowiredFields() {
        instanceStore.getAppScope().forEach((iface, impl) -> {
            Field[] fields = impl.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Object injectObj = instanceStore.getAppScope().get(field.getType());
                    try {
                        field.set(impl, injectObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public Class<?> getStarter() {
        return classSearcher.findStarter();
    }
}
