package clean_code.patterns_java.facade_smarthome;

public class Main {
    public static void main(String[] args) {
        DeviceFacade facade = new DeviceFacade(
                new Lamp(),
                new Conditioner(),
                new SecuritySystem()
        );

        facade.lampSwitcher();
        facade.conditionerSwitcher();
        facade.securitySystemSwitcher();
    }
}
