package utils;

public class Log {
    public static void d(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void start() {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void end() {
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void start(String msg) {
        System.out.println("╔════════════════════════════════════"+msg+"═══════════════════════════════════════════════════");
    }

    public static void end(String msg) {
        System.out.println("╚════════════════════════════════════"+msg+"═══════════════════════════════════════════════════");
    }
}
