package mate.academy.internetshop.dao;

public class IdGenerator {
    private static Long id = 0L;

    public static Long getId() {
        return id++;
    }
}
