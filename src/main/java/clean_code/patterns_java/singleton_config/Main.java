package clean_code.patterns_java.singleton_config;

public class Main {
    public static void main(String[] args) {
        var configurationManager = ConfigurationManager.getInstance();
        configurationManager.setDb_url("www.leningrad_spb.ru");

        System.out.println(configurationManager.getDb_url());
    }
}
