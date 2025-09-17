package clean_code.patterns_java.singleton_logger;

public class Logger {
    private static Logger logger;

    private Logger() {}

    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public void logEvent(String message) {
        System.out.println("Произошло событие: " + message);
    }

    public void logError(String message) {
        System.out.println("Произошла ошибка: " + message);
    }

    public void logWarn(String message) {
        System.out.println("Предупреждение: " + message);
    }
}
