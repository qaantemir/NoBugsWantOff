package clean_code.patterns_java.abstract_fabric_gui;

public class MacOsGuiFabric implements GuiAbstractFabric {
    @Override
    public Button getButton() {
        return new MacOsButton();
    }

    @Override
    public Menu getMenu() {
        return new MacOsMenu();
    }

    @Override
    public Window getWindow() {
        return new MacOsWindow();
    }
}
