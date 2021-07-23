package factories.config.reflections;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ReflectionsManager {
    private final Reflections scanner;

    public ReflectionsManager(String packageToScan) {
        scanner = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageToScan)));
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> cl) {
        return scanner.getSubTypesOf(cl);
    }
}
