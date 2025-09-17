package clean_code.patterns_java.facade_smarthome;

public class SecuritySystem implements Device {
    @Override
    public void switcher() {
        System.out.println("Security System switched on/off");
    }
}
