package ua.com.alevel.util;

import lombok.Cleanup;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import ua.com.alevel.annotations.Autowired;
import ua.com.alevel.annotations.Service;
import ua.com.alevel.annotations.Starter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class ClassSearcher {
    private final ClassHunter classHunter;
    private static ClassSearcher instance;
    private final String packageName;

    private ClassSearcher(String packageName) {
        classHunter = ComponentSupplier.getInstance().getClassHunter();
        this.packageName = packageName;
    }

    public static ClassSearcher getInstance(String packageName) {
        if (instance == null) {
            instance = new ClassSearcher(packageName);
        }
        return instance;
    }

    public Set<Class<?>> findAllInterfaces() {
        return instance.findServices()
                .stream()
                .flatMap(cls -> Arrays.stream(cls.getInterfaces()))
                .collect(Collectors.toSet());
    }

    public Collection<Class<?>> getSubClasses(Class<?> parentClass) {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"))
                        .by(ClassCriteria.create().allThoseThatMatch(cls ->
                                parentClass.isAssignableFrom(cls) && notEquals(parentClass.getName(),cls.getName())));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        return searchResult.getClasses();
    }

    private boolean notEquals(String s1, String s2) {
        return !s1.equals(s2);
    }

    public Collection<Class<?>> findServices() {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"))
                        .by(ClassCriteria.create().allThoseThatHaveAMatchInHierarchy((cls) -> {
                            return cls.isAnnotationPresent(Service.class);
                        }));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        return searchResult.getClasses();
    }

    public Collection<Class<?>> findClassesWithAutowiredFields() {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"))
                        .by(ClassCriteria.create().allThoseThatHaveAMatchInHierarchy((cls) -> {
                            return Arrays.stream(cls.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(Autowired.class));
                        }));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        return searchResult.getClasses();
    }

    public Class<?> findStarter() {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"))
                        .by(ClassCriteria.create().allThoseThatHaveAMatchInHierarchy((cls) -> cls.isAnnotationPresent(Starter.class)));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        Collection<Class<?>> resultClasses = searchResult.getClasses();
        if (resultClasses.size() != 1) {
            throw new RuntimeException("You must have one @Starter class!");
        }
        return resultClasses.stream().findFirst().get();
    }

    public Collection<Class<?>> findAllFromPackage() {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        return searchResult.getClasses();
    }

    public Collection<Class<?>> findBy(SearchPredicate predicate) {
        SearchConfig searchConfigForResources =
                SearchConfig.forResources(packageName.replace(".", "/"))
                        .by(ClassCriteria.create().allThoseThatHaveAMatchInHierarchy((cls) -> {
                            return predicate.findBy(cls);
                        }));
        @Cleanup ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfigForResources);
        return searchResult.getClasses();
    }
}
