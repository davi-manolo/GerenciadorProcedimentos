package manager;

public abstract class SessionTestMode {
    
    private static boolean value;
 
    public static void on() {
        value = true;
    }
    
    public static void off() {
        value = false;
    }

    public static boolean isValue() {
        return value;
    }
    
}
