package clean_code.patterns_java.abstract_fabric_gui;

public class MacOsButton implements Button {
    @Override
    public void click() {
        System.out.println("macos button click");
    }
}
