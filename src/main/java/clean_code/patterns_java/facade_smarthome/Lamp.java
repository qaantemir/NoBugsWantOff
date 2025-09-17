package clean_code.patterns_java.facade_smarthome;

public class Lamp implements Device {
    @Override
    public void switcher() {
        System.out.println("lamp switched on/off");
    }
}
