package clean_code.patterns_java.singleton_logger;

public class Main {
    public static void main(String[] args) {
        var logger = Logger.getInstance();

        logger.logEvent("Запуск");
        logger.logError("ВЫлет");
        logger.logWarn("Упячка");
    }
}
