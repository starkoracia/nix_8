package ua.com.alevel.store;

import java.util.HashMap;

public final class InstanceStore {
    private final HashMap<Class<?>, Object> appScope;
    private static InstanceStore instanceStore;

    private InstanceStore() {
        this.appScope = new HashMap<>();
    }

    public static InstanceStore getStore() {
        if(instanceStore == null) {
            instanceStore = new InstanceStore();
        }
        return instanceStore;
    }

    public HashMap<Class<?>, Object> getAppScope() {
        return appScope;
    }
}
