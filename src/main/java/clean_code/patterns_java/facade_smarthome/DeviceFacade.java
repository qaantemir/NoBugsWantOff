package clean_code.patterns_java.facade_smarthome;

public class DeviceFacade {
    private Lamp lamp;
    private Conditioner conditioner;
    private SecuritySystem securitySystem;

    public DeviceFacade(Lamp lamp, Conditioner conditioner, SecuritySystem securitySystem) {
        this.lamp = new Lamp();
        this.conditioner = new Conditioner();
        this.securitySystem = new SecuritySystem();
    }

    public void lampSwitcher() {
        lamp.switcher();
    }

    public void conditionerSwitcher() {
        conditioner.switcher();
    }

    public void securitySystemSwitcher() {
        securitySystem.switcher();
    }
}
