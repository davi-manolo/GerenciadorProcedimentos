package utils;

public abstract class SessionMode {
    
    private static boolean value;
 
    public static void demo() {
        value = true;
    }
    
    public static void full() {
        value = false;
    }

    public static boolean getValue() {
        return value;
    }
    
}
