package clean_code.patterns_java.facade_smarthome;

public class Conditioner implements Device {
    @Override
    public void switcher() {
        System.out.println("Conditioner switched on/off");
    }
}
