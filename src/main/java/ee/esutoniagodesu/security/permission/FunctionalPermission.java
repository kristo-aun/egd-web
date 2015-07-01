package ee.esutoniagodesu.security.permission;

@FunctionalInterface
public interface FunctionalPermission {
    boolean hasPermission(Object object);
}
