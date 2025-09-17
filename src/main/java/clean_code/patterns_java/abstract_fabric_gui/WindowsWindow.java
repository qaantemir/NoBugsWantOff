package clean_code.patterns_java.abstract_fabric_gui;

public class WindowsWindow implements Window {
    @Override
    public void draw() {
        System.out.println("windows window draw");
    }
}
