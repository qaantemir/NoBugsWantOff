package clean_code.patterns_java.abstract_fabric_gui;

public class MacOsWindow implements Window {
    @Override
    public void draw() {
        System.out.println("macos window draw");
    }
}
