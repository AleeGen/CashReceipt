package util.generation;

public class NumberGenerator {

    private static long number = 0;

    public static Long generate() {
        return number++;
    }
}
