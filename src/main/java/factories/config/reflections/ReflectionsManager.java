package factories.config.reflections;

import exceptions.factory.NoSuchPackageException;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ReflectionsManager {
    private final Reflections scanner;

    public ReflectionsManager(String packageToScan) throws NoSuchPackageException {
        try {
            scanner = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(packageToScan)));
        } catch (ReflectionsException e) {
            throw new NoSuchPackageException("No package "+packageToScan+" found");
        }
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> cl) {
        return scanner.getSubTypesOf(cl);
    }
}
