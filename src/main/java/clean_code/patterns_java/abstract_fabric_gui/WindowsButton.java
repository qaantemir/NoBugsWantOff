package clean_code.patterns_java.abstract_fabric_gui;

public class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("windows button click");
    }
}
