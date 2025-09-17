package clean_code.patterns_java.abstract_fabric_gui;

public class Main {
    public static void main(String[] args) {
        GuiAbstractFabric macOsGuiFabric = new MacOsGuiFabric();
        GuiAbstractFabric windowsFabric = new WindowsGuiFabric();

        macOsGuiFabric.getButton().click();
        macOsGuiFabric.getMenu().set();
        macOsGuiFabric.getWindow().draw();

        windowsFabric.getButton().click();
        windowsFabric.getMenu().set();
        windowsFabric.getWindow().draw();
    }
}
