package ua.com.alevel.factory;

import lombok.Getter;
import ua.com.alevel.store.InstanceStore;
import ua.com.alevel.util.ClassSearcher;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InstanceFactory {
    private final ClassSearcher classSearcher;
    private static InstanceFactory instanceFactory;
    @Getter private HashMap<Class<?>, Class<?>> diMap;
    @Getter private HashMap<Class<?>, Object> appScope;

    private InstanceFactory(ClassSearcher classSearcher) {
        this.classSearcher = classSearcher;
        initializeDiMap();
        initAppScope();
    }

    public static InstanceFactory getInstanceFactory(ClassSearcher classSearcher) {
        if (instanceFactory == null) {
            instanceFactory = new InstanceFactory(classSearcher);
        }
        return instanceFactory;
    }

    private void initAppScope() {
        appScope = InstanceStore.getStore().getAppScope();
        diMap.forEach((iface, implClass) -> {
            if(iface.isInterface()) {
                appScope.put(iface, createInstanceFromIface(iface));
            }
        });
    }

    public  <IFACE> IFACE createInstanceFromIface(Class<IFACE> iface) {
        Class<?> implClass = getImplementationClass(iface);
        try {
            Constructor<?> constructor = implClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (IFACE) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initializeDiMap() {
        diMap = new HashMap<>();
        Collection<Class<?>> services = classSearcher.findServices();
        for (Class<?> implClass : services) {
            Class<?>[] interfaces = implClass.getInterfaces();
            if (interfaces.length == 0) {
                diMap.put(implClass, implClass);
            } else {
                for (Class<?> iface : interfaces) {
                    diMap.put(iface, implClass);
                }
            }
        }
    }

    public Class<?> getImplementationClass(Class<?> iface) {
        Set<Class<?>> implClasses = new HashSet<>(classSearcher.getSubClasses(iface));
        String errorMessage = "";
        if (implClasses.size() == 0) {
            errorMessage = "No implementation found for interface" + iface.getName();
        } else {
            Class<?> implClass = implClasses.stream().findFirst().get();
            return implClass;
        }
        throw new RuntimeErrorException(new Error(errorMessage));
    }

}
