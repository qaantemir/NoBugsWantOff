package clean_code.patterns_java.singleton_config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;

    private String db_url;
    private String path;
    private String loggerPath;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if(configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLoggerPath() {
        return loggerPath;
    }

    public void setLoggerPath(String loggerPath) {
        this.loggerPath = loggerPath;
    }


}
